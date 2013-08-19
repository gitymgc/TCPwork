package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class ImageIOClient {
	public static void main(String[] args) throws Exception{
		while(true)
		new ImageIOClient().exec(args);
	}

	public void exec(String[] args) throws Exception{

		if((args.length < 1)){
			throw new IllegalArgumentException("Parameter(s) : <Server> [<Port>]");
		}

		String srcDirPath = "./debug/src/";
		String dstDirPath = "./debug/dst/";
		
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();

		for(File srcFile : srcFiles){
			System.out.println(srcFile.getName());
			//ソケット接続
			String server = args[0];
			int servPort = (args.length == 2)? Integer.parseInt(args[1]) :7;
			Socket sock = new Socket(server, servPort);
			System.out.println("Connected to server ...sending echo string");

			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			
			BufferedImage srcImg = ImageIO.read(srcFile);
			System.out.println(srcImg.getType());
			if((srcImg.getType() == BufferedImage.TYPE_4BYTE_ABGR) || (srcImg.getType() == BufferedImage.TYPE_USHORT_GRAY) || (srcImg.getType() == BufferedImage.TYPE_CUSTOM)){
				sock.close();
				continue;
			}
			String name = srcFile.getName();
			String sepName[] = name.split("\\.");
			ImageIO.write(srcImg, "bmp",os);
			
			System.out.println("処理開始");
			
			//受信
			ImageInputStream iis =ImageIO.createImageInputStream(is);
			BufferedImage dstImg = ImageIO.read(iis);
			String dstFilePath = dstDirPath + srcFile.getName()+ ".bmp";
			File dstFile = new File(dstFilePath);
			ImageIO.write(dstImg, "bmp", dstFile);
			sock.close();
		}
	}
}
