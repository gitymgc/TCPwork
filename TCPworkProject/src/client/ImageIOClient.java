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

		String srcDirPath = "./resources/debug/src/";
		String dstDirPath = "./resources/debug/dst/";
		
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();

		for(File srcFile : srcFiles){
			System.out.println(srcFile.getName());
			//ソケット接続
			String server = args[0];
			int servPort = (args.length == 2)? Integer.parseInt(args[1]) :10514;
			Socket sock = new Socket(server, servPort);
			System.out.println("Connected to server ...sending echo string");

			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			
			BufferedImage srcImg = ImageIO.read(srcFile);
			String name = srcFile.getName();
			String sepName[] = name.split("\\.");
			ImageIO.write(srcImg, sepName[1],os);
			
			System.out.println("処理開始");
			
			//受信
			BufferedImage dstImg = ImageIO.read(is);
			String dstFilePath = dstDirPath + srcFile.getName()+ ".bmp";
			File dstFile = new File(dstFilePath);
			ImageIO.write(dstImg, "bmp", dstFile);
			sock.close();
		}
	}
}
