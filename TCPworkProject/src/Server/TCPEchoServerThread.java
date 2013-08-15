package Server;

import java.io.IOException;
import java.net.ServerSocket;

import main.Logger;

public class TCPEchoServerThread {
	
	public static void main(String[] args) throws IOException{
		
		if(args.length != 1){
			throw new IllegalArgumentException("Parameter(S): <Port>");
		}
		
		int echoServPort = Integer.parseInt(args[0]);
		ServerSocket servSock = new ServerSocket(echoServPort);
		Logger logger = new ConsoleLogger();
		
	}

}
