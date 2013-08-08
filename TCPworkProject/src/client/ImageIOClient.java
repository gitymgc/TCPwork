package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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

			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			//			BufferedOutputStream out = 
			//					new BufferedOutputStream(
			//					sock.getOutputStream()
			//					);

			FileInputStream fis = new FileInputStream(srcFile);
			int count = 0;

			//送信
			int data = 0;
			// バッファのサイズ
			int bufSize = 4096;
			int recvMsgSize;
			byte buf[] = new byte[bufSize];
			while( (recvMsgSize = fis.read(buf) ) != -1){
				os.write(buf);
			}
			os.write(1);

			//			int res = 0;
			//			res = is.read();
			//			if(res == 1){
			//				System.out.println("BufferedImage.TYPE_CUSTOM は処理出来ません。");
			//				sock.close();
			//			} else {

			System.out.println("処理開始");
			BufferedImage dstImg = ImageIO.read(is);
			String dstFilePath = dstDirPath + srcFile.getName()+ ".bmp";
			File dstFile = new File(dstFilePath);
			if(dstImg != null){
				ImageIO.write(dstImg, "bmp", dstFile);
			}else{
				System.out.println("BufferedImage.TYPE_CUSTOM は処理出来ません。");
				fis.close();
				sock.close();
				continue;
			}
			//			}
			fis.close();
			sock.close();
		}
	}
}
