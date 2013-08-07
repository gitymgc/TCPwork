package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPEchoClient {
	public static void main(String[] args){

		new TCPEchoClient().exec(args);
	}

	public void exec(String[] args){

		if((args.length < 2) || (args.length > 3)){
			throw new IllegalArgumentException("Parameter(s) : <Server> <Word> [<Port>]");
		}

		String server = args[0];
		byte[] byteBuffer = args[1].getBytes();
		int servPort = (args.length == 3)? Integer.parseInt(args[2]) :7;
		Socket sock = null;

		try {
			sock = new Socket(server, servPort);
		} catch (UnknownHostException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("Connected to server ...sending echo string");

		InputStream in = null;
		try {
			in = sock.getInputStream();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		OutputStream out = null;
		try {
			out = sock.getOutputStream();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		try {
			out.write(byteBuffer);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		int totalBytesRcvd = 0;
		int bytesRcvd = 0;
		while(totalBytesRcvd < byteBuffer.length){
			try {
				bytesRcvd = in.read(byteBuffer,totalBytesRcvd,byteBuffer.length - totalBytesRcvd);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			if(bytesRcvd == -1){
				try {
					throw new SocketException("Connection closed prematurely");
				} catch (SocketException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
			totalBytesRcvd += bytesRcvd;
		}
		
		System.out.println("Received : "+ new String(byteBuffer));
		try {
			sock.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
