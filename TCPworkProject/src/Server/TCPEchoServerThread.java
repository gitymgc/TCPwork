package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.ConsoleLogger;
import main.EchoProtocol;
import main.Logger;

public class TCPEchoServerThread {

	public static void main(String[] args) throws IOException{

		if(args.length != 1){
			throw new IllegalArgumentException("Parameter(S): <Port>");
		}

		int echoServPort = Integer.parseInt(args[0]);
		ServerSocket servSock = new ServerSocket(echoServPort);
		Logger logger = new ConsoleLogger();

		for(;;){
			try{
				Socket clntSock = servSock.accept();
				EchoProtocol protocol = new EchoProtocol(clntSock, logger);
				Thread thread = new Thread(protocol);
				thread.start();
				logger.writeEntry("Created and started Thread = " + thread.getName());
			}catch(IOException e){
				logger.writeEntry("Exception = " + e.getMessage());
			}
		}
	}
}
