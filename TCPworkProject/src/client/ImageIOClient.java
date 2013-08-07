package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ImageIOClient {
	public static void main(String[] args) throws Exception{
		new ImageIOClient().exec(args);
	}

	public void exec(String[] args) throws Exception{

		if((args.length < 1)){
			throw new IllegalArgumentException("Parameter(s) : <Server> [<Port>]");
		}

		//		//ソケット接続
		//		String server = args[0];
		//		int servPort = (args.length == 2)? Integer.parseInt(args[1]) :10514;
		//		Socket sock = new Socket(server, servPort);
		//		System.out.println("Connected to server ...sending echo string");
		//
		//		InputStream in = sock.getInputStream();
		//		OutputStream out = sock.getOutputStream();

		String srcDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/src/";
		String dstDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/dst/";
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();

		for(File srcFile : srcFiles){
			System.out.println(srcFile.getName());
			//ソケット接続
			String server = args[0];
			int servPort = (args.length == 2)? Integer.parseInt(args[1]) :10514;
			Socket sock = new Socket(server, servPort);
			System.out.println("Connected to server ...sending echo string");

			InputStream in = sock.getInputStream();
			OutputStream out = sock.getOutputStream();

			BufferedImage srcImg = ImageIO.read(srcFile);
			//			WritableRaster srcRas = srcImg.getRaster();
			//			DataBuffer srcBuf = srcRas.getDataBuffer();
			//			int w = srcImg.getWidth();
			//			int h = srcImg.getHeight();
			//
			//			byte srcBytes[] = new byte[w*h];
			//			for(int i = 0; i < h*w; i++){
			//				srcBytes[i] = (byte) srcBuf.getElem(i);
			//			}
			ImageIO.write(srcImg, "bmp", out);

			//			int totalBytesRcvd = 0;
			//			int bytesRcvd;
			//
			//			while(totalBytesRcvd < srcBytes.length){
			//				if((bytesRcvd = in.read(srcBytes,totalBytesRcvd,srcBytes.length - totalBytesRcvd)) == -1){
			//					throw new SocketException("Connection closed prematurely");
			//				}
			//				totalBytesRcvd += bytesRcvd;
			//			}
			//			BufferedImage dstImg = ImageIO.read(in);
			//			String dstFilePath = dstDirPath +srcFile.getName()+"dst.bmp";
			//			File dstFile = new File(dstFilePath);
			//			ImageIO.write(dstImg,"bmp",dstFile );
			if(srcImg == null){
				sock.close();
			}else{
				BufferedImage dstImg = ImageIO.read(in);
				String path = srcFile.getName();
				String[] name = path.split("\\.");
				
				String dstFilePath = dstDirPath +path+".bmp";
				File dstFile = new File(dstFilePath);
				ImageIO.write(dstImg,"bmp",dstFile );
			}
			
			sock.close();
		}
	}
}
