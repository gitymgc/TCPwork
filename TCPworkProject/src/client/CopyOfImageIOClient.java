package client;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class CopyOfImageIOClient {
	public static void main(String[] args) throws Exception{
		while(true)
		new CopyOfImageIOClient().exec(args);
		
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
//			System.out.println(srcFile.getName());
			//ソケット接続
			String server = args[0];
			int servPort = (args.length == 2)? Integer.parseInt(args[1]) :7;
			Socket sock = new Socket(server, servPort);
//			System.out.println("Connected to server ...sending echo string");

			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();

			BufferedImage srcImg = ImageIO.read(srcFile);
//			System.out.println(srcImg.getType());
			if((srcImg.getType() == BufferedImage.TYPE_4BYTE_ABGR) || (srcImg.getType() == BufferedImage.TYPE_USHORT_GRAY) || (srcImg.getType() == BufferedImage.TYPE_CUSTOM)){
				sock.close();
				continue;
			}

			String name = srcFile.getName();
			String sepName[] = name.split("\\.");
//			System.out.println(sepName[1]);
			ImageIO.write(srcImg, "bmp",os);
			
			/*
			 * サーバ処理中。。
			 */
			
			// 受信 : データサイズ
			int dataSize = 0;
			for(int i = 0, buf = 0; (buf = is.read()) != 255; i += 8){
				dataSize |= buf << i;
			}
			
			// 受信 : データ輝度値
			byte data[] = new byte[dataSize];
			int recvMsgSize;
			int total = 0;
			byte buf[] = new byte[256];
			while((recvMsgSize = is.read(buf)) != -1){
				System.arraycopy(buf, 0, data, total, recvMsgSize);
				total += recvMsgSize;
			}
			
//			System.out.println("処理開始");

			//受信
			BufferedImage dstImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			DataBuffer dstBuf = dstImg.getRaster().getDataBuffer();
			for(int y = 0; y < srcImg.getHeight(); y++)
				for(int x = 0; x < srcImg.getWidth(); x++)
					dstBuf.setElem(y*srcImg.getWidth()+x, data[y*srcImg.getWidth()+x]&0xff);
			
			String dstFilePath = dstDirPath + srcFile.getName()+ ".bmp";
			File dstFile = new File(dstFilePath);
			ImageIO.write(dstImg, "bmp", dstFile);
//			
			
//			String dstFilePath = dstDirPath + srcFile.getName()+ ".bmp";
//			File dstFile = new File(dstFilePath);
//			FileOutputStream fos = new FileOutputStream(dstFile);
//			
//			int recvMsgSize;
//			byte buf[] = new byte[256];
//			while((recvMsgSize = is.read(buf)) != -1){
//				fos.write(buf, 0, recvMsgSize);
//			}
//			System.out.println(dstFile.length());
//			if(dstFile.length() != 85758){
//				System.exit(1);
//			}
//			os.write(1);
//			fos.close();
			sock.close();
			System.out.println("owari");
		}
	}
}
