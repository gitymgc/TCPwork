package client;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
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

		//ソケット接続
		String server = args[0];
		int servPort = (args.length == 2)? Integer.parseInt(args[1]) :10514;
		Socket sock = new Socket(server, servPort);
		System.out.println("Connected to server ...sending echo string");

		InputStream in = sock.getInputStream();
		OutputStream out = sock.getOutputStream();

		String srcDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/src";
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();
		for(File srcFile : srcFiles){
			
			BufferedImage srcImg = ImageIO.read(srcFile);
			WritableRaster srcRas = srcImg.getRaster();
			DataBuffer srcBuf = srcRas.getDataBuffer();
			int w = srcImg.getWidth();
			int h = srcImg.getHeight();
			
			byte srcBytes[] = new byte[w*h];
			for(int i = 0; i < h*w; i++){
				srcBytes[i] = (byte) srcBuf.getElem(i);
			}
			ImageIO.write(srcImg, "bmp", out);
			
			int totalBytesRcvd = 0;
			int bytesRcvd;
			
			while(totalBytesRcvd < srcBuf.getSize()){
				if((bytesRcvd = in.read(srcBytes,totalBytesRcvd,srcBuf.getSize() - totalBytesRcvd)) == -1);
			}
			











			sock.close();
		}
	}
}
