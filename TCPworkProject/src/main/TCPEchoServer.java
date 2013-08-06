package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {

	private static final int BUFSIZE = 32;

	public static void main(String[] args){

		new TCPEchoServer().exec(args);

	}

	public void exec(String args[]){
		if(args.length != 1){
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}

		int servPort = Integer.parseInt(args[0]);
		ServerSocket servSock = null;
		try {
			servSock = new ServerSocket(servPort);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		int recvMsgSize;
		byte[] byteBuffer = new byte[BUFSIZE];
		Socket clntSock	= null;
		for(; ;){
			try {
				clntSock = servSock.accept();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		}
		



	}

}
