package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
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

		int recvMsgSize = 0;
		byte[] byteBuffer = new byte[BUFSIZE];
		Socket clntSock	= null;
		for(; ;){
			try {
				clntSock = servSock.accept();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			InetAddress clntAddress = clntSock.getInetAddress();
			String IP = clntAddress.getHostAddress();
			System.out.println("Handing client at "+IP+ " on port"+ clntSock.getPort());

			InputStream in = null;
			try {
				in = clntSock.getInputStream();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			OutputStream out = null;
			try {
				out  = clntSock.getOutputStream();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			try {
				recvMsgSize = in.read(byteBuffer);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			while(recvMsgSize != -1){
				try {
					out.write(byteBuffer,0,recvMsgSize);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}

			try {
				clntSock.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
