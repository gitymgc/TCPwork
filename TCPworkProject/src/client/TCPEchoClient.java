package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TCPEchoClient {
	public static void main(String[] args)throws Exception{

		new TCPEchoClient().exec(args);
	}

	public void exec(String[] args)throws Exception{

		if((args.length < 2) || (args.length > 3)){
			throw new IllegalArgumentException("Parameter(s) : <Server> <Word> [<Port>]");
		}

		String server = args[0];
		byte[] byteBuffer = args[1].getBytes();
		int servPort = (args.length == 3)? Integer.parseInt(args[2]) :7;
		Socket sock = new Socket(server, servPort);
		System.out.println("Connected to server ...sending echo string");

		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();

		out.write(byteBuffer);

		int totalBytesRcvd = 0;
		int bytesRcvd = 0;
		while(totalBytesRcvd < byteBuffer.length){

			bytesRcvd = in.read(byteBuffer,totalBytesRcvd,byteBuffer.length - totalBytesRcvd);
			if(bytesRcvd == -1){
				try {
					throw new SocketException("Connection closed prematurely");
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
			totalBytesRcvd += bytesRcvd;
		}
		System.out.println("Received : "+ new String(byteBuffer));
		sock.close();
	}
}
