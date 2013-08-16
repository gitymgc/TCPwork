package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerDispatcher implements Dispatcher{
	public void startDispatching(ServerSocket servSock , Logger logger, ProtocolFactory protoFactory){
		for(;;){
			try{
				Socket clntSock = servSock.accept();
				Runnable protocol = protoFactory.createProtocol(clntSock,logger);
				Thread thread = new Thread(protocol);
				thread.start();
				logger.writeEntry("Created and stated Thread = "+ thread.getName());
			}catch(IOException e){
				logger.writeEntry("Exception = " + e.getMessage());		
				}
		}
	}
}
