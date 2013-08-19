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



public class GrayScalingProtocol implements Runnable{
	static public final int BUFSIZE = 256;

	private Socket clntSock;
	private Logger logger;

	public GrayScalingProtocol(Socket clntSock , Logger logger){
		this.clntSock = clntSock;
		this.logger = logger;
	}
	public void run(){
		ArrayList entry = new ArrayList();
		entry.add("Client address and port = " + clntSock.getInetAddress().getHostAddress() + ":" + clntSock.getPort());
		entry.add("Thread = " + Thread.currentThread().getName());

		String dstDirPath = "./resources/debug/dst/";

		try{
			InputStream is = clntSock.getInputStream();
			OutputStream os = clntSock.getOutputStream();

			BufferedImage srcImg = ImageIO.read(is);
			if(srcImg == null) return;
			WritableRaster srcRas = srcImg.getRaster();
			DataBuffer srcBuf = srcRas.getDataBuffer();
			int w = srcImg.getWidth();
			int h = srcImg.getHeight();

			int dst2d[][] = new int[h][w];
			try {
				ImageDecoder.exec(srcImg, dst2d);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			BufferedImage dstImg = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
			WritableRaster dstRas = dstImg.getRaster();
			DataBuffer dstBuf = dstRas.getDataBuffer();

			for(int y = 0; y < h; y++){
				for(int x = 0; x < w; x++){
					dstBuf.setElem(y*w+x,dst2d[y][x]);
				}
			}
			
//			// 送信 : データサイズ
//			int dataSize = w*h;
//			for(int i = dataSize; 0 < i; i >>= 8){
//				os.write(i&0xff);
//			}
//			os.write(-1);
//			
//			// 送信 : データ（輝度値)
//			for(int y = 0; y < h; y++){
//				for(int x = 0; x < w; x++){
//					os.write(dst2d[y][x]);
//				}
//			}
			
			
			ImageIO.write(dstImg, "bmp", os);
			
//			System.out.println(is.read());
//			ImageIO.write(dstImg, "bmp", new File("D:/tmp/tmp.bmp"));
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
