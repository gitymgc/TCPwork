package main;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Server.ImageDecoder;

public class GrayScalingProtocol implements Runnable{
	static public final int BUFSIZE = 32;
	
	private Socket clntSock;
	private Logger logger;
	
	public GrayScalingProtocol(Socket clntSock , Logger logger){
		this.clntSock = clntSock;
	}
	
	public void run(){
		ArrayList entry = new ArrayList();
		entry.add("Client address and port = " + clntSock.getInetAddress().getHostAddress() + ":" + clntSock.getPort());
		entry.add("Thread = " + Thread.currentThread().getName());
		
		try{
			InputStream is = clntSock.getInputStream();
			OutputStream os = clntSock.getOutputStream();
			
			BufferedImage srcImg = ImageIO.read(is);
			WritableRaster srcRas = srcImg.getRaster();
			DataBuffer srcBuf = srcRas.getDataBuffer();
			int w = srcImg.getWidth();
			int h = srcImg.getHeight();
			
			int src2d[][] = new int[h][w];
			for(int y = 0; y < h; y++){
				for(int x = 0; x < w; x++){
					src2d[y][x] = srcBuf.getElem(y*w+x);
				}
			}
			
			try {
				ImageDecoder.exec(srcImg, src2d);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ImageIO.write(srcImg, "bmp", os);
			entry.add("OK");
		}catch(IOException e){
			entry.add("Exception = " + e.getMessage());
		}
		try{
			clntSock.close();
		}catch(IOException e){
			entry.add("Exception = " + e.getMessage());
		}
		logger.writeEntry(entry);
	}
}
