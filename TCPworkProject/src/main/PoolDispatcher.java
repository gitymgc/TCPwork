package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PoolDispatcher implements Dispatcher{

	static final String NUMTHREADS = "8";  //スレッドプールのデフォルトサイズ
	static final String THREADPROP = "Threads";  //スレッドプロパティの名前

	private int numThreads;  //プールに含まれるスレッドの数
	public PoolDispatcher(){
		//Systemプロパティからスレッドの数を取得するか、デフォルト値を使用する。
		numThreads = Integer.parseInt(System.getProperty(THREADPROP,NUMTHREADS));
	}

	public void startDispatching(final ServerSocket servSock, final Logger logger, final ProtocolFactory protoFactory){
		//それぞれが反復サーバとして動作する、n-1個のスレッドを生成する
		for(int i = 0; i < (numThreads - 1); i++){
			Thread thread = new Thread(){
				public void run(){
					dispatchLoop(servSock, logger, protoFactory);
				}
			};
			thread.start();
			logger.writeEntry("Created and started Thread = " + thread.getName());
		}
		logger.writeEntry("Iterative server starting in main thread " + Thread.currentThread().getName());
		//メインスレッドをn番目の反復サーバとして使用する
		dispatchLoop(servSock, logger, protoFactory);
	}
	
	private void dispatchLoop(ServerSocket servSock, Logger logger, ProtocolFactory protoFactry){
		for(;;){
			try{
				Socket clntSock = servSock.accept();
				Runnable protocol = protoFactry.createProtocol(clntSock, logger);
				protocol.run();
			}catch(IOException e){
				logger.writeEntry("Exception = " + e.getMessage());
			}
		}
	}
}
