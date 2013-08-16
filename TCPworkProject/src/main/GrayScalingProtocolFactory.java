package main;

import java.net.Socket;

public class GrayScalingProtocolFactory implements ProtocolFactory{
	public Runnable createProtocol(Socket clntSock, Logger logger){
		return new GrayScalingProtocol(clntSock, logger);
	}

}
