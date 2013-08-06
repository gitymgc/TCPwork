package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {

	private static final int BUFSIZE = 32;

	public static void main(String[] args) throws IOException{

		new TCPEchoServer().exec(args);

	}

	public void exec(String args[]) throws IOException{

		if(args.length != 1){
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}

		int servPort = Integer.parseInt(args[0]);
		ServerSocket servSock = new ServerSocket(servPort);

		int recvMsgSize ;
		byte[] byteBuffer = new byte[BUFSIZE];

		for(; ;){
			System.out.println("start");
			Socket clntSock = servSock.accept();

			InetAddress clntAddress = clntSock.getInetAddress();
			String IP = clntAddress.getHostAddress();
			System.out.println("Handing client at "+IP+ " on port"+ clntSock.getPort());

			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();

			while((recvMsgSize = in.read(byteBuffer)) != -1){
				out.write(byteBuffer,0,recvMsgSize);
			}
			clntSock.close();
		}
	}
}
