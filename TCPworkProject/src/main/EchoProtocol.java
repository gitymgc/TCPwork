package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;



public class EchoProtocol implements Runnable{
	static public final int BUFSIZE = 32;

	private Socket clntSock;
	private Logger logger;

	public EchoProtocol(Socket clntSock, Logger logger){
		this.clntSock = clntSock;
		this.logger = logger;
	}

	public void run(){
		ArrayList entry = new ArrayList();
		entry.add("Client address and port = " + clntSock.getInetAddress().getHostAddress() + ":" + clntSock.getPort());
		entry.add("Thread = "+ Thread.currentThread().getName());

		try{
			//ソケットから入力および出力I/Oストリームを取得する。
			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();

			int recvMsgSize;
			int totalBytesEchoed = 0;
			byte[] echoBuffer = new byte[BUFSIZE];
			while((recvMsgSize = in.read(echoBuffer)) != -1){
				out.write(echoBuffer,0,recvMsgSize);
				totalBytesEchoed += recvMsgSize;
			}
			entry.add("Client finished; echoed " + totalBytesEchoed + "bytes.");
		}catch(IOException e){
			entry.add("Exception = " + e.getMessage());
		}

		try{
			clntSock.close();
		}catch(IOException e){
			entry.add("Exception = " + e.getMessage());
		}
		logger.writeEntry(entry);
	}
}
