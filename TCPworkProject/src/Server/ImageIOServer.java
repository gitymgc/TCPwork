package Server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class ImageIOServer {

	private static final int BUFSIZE = 32;

	public static void main(String[] args) throws Exception{

		new ImageIOServer().exec();

	}

	public void exec() throws Exception{

		int servPort = 10514;
		ServerSocket servSock = new ServerSocket(servPort);
		int cnt = 0;
		for(; ;){
			System.out.println("start");
			Socket clntSock = servSock.accept();

			InetAddress clntAddress = clntSock.getInetAddress();
			String IP = clntAddress.getHostAddress();

			InputStream is = clntSock.getInputStream();
			OutputStream os = clntSock.getOutputStream();

			//			File srcFile = new File("/home/yamaguchi/tmp/tmp.bmp");
			File srcFile = new File("D:/tmp/tmp.bmp");
			FileOutputStream fos = new FileOutputStream(srcFile);

			//受信
			// バッファのサイズ
			int bufSize = 256;
			int recvMsgSize = 0;
			byte buf[] = new byte[bufSize];
			while( (recvMsgSize = is.read(buf) ) != 1){
				fos.write(buf);
			}

			BufferedImage srcImg = null;
			try{
				srcImg = ImageIO.read(srcFile);

			}catch(IIOException e){
				//TYPE_CUSTOMははじく
				os.write(1);
//				fos.flush();
				fos.close();
				clntSock.close();
				continue;
			}
			os.write(0);

			int h = srcImg.getHeight();
			int w = srcImg.getWidth();
			System.out.println(" w = "+w+" h = "+h);
			int src2d[][] = new int[h][w];

			ImageDecoder.exec(srcImg,src2d);

			BufferedImage dstImg = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
			WritableRaster dstRas = dstImg.getRaster();
			DataBuffer dstBuf = dstRas.getDataBuffer();

			int dst2d[][] = new int[h][w];
			for(int y = 0; y < h; y ++){
				for(int x = 0; x < w; x++){
					dst2d[y][x] = src2d[y][x];
					dstBuf.setElem(y*w+x, dst2d[y][x]);
				}
			}

			//送信
			//ImageIO.write(dstImg, "bmp", os);
			//			File dstFile = new File("/home/yamaguchi/tmp/tmp.bmp");
			String dstFilePath = "D:/tmp/"+cnt+".bmp";
			//			String dstFilePath = "/home/yamaguchi/tmp/" + cnt + "bmp";
			File dstFile = new File(dstFilePath);
			ImageIO.write(dstImg, "bmp", dstFile);

			//			File dstFile = new File(dstFilePath);
			FileInputStream fis = new FileInputStream(dstFile);
			byte dstFileBuf[] =  new byte[256];
			
			while((recvMsgSize = fis.read(dstFileBuf)) != -1){
				os.write(dstFileBuf);
			}
			os.write(1);

			//			ImageIO.write(dstImg,"bmp",dstFile);
			cnt++;

//			fos.flush();
			fos.close();
			fis.close();
			clntSock.close();
		}
	}
}
