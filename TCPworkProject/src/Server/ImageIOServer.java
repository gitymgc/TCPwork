package Server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ImageIOServer {

	private static final int BUFSIZE = 32;

	public static void main(String[] args) throws Exception{

		new ImageIOServer().exec();

	}

	public void exec() throws Exception{
		String dstDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/dst/";
		int servPort = 10514;
		ServerSocket servSock = new ServerSocket(servPort);

		int cnt  = 0;
		for(; ;){
			System.out.println("start");
			Socket clntSock = servSock.accept();

			InetAddress clntAddress = clntSock.getInetAddress();
			String IP = clntAddress.getHostAddress();
			//			System.out.println("Handing client at "+IP+ " on port"+ clntSock.getPort());

			InputStream is = clntSock.getInputStream();
			OutputStream os = clntSock.getOutputStream();
			
			File srcFile = new File("D:/tmp/tmp.bmp");
			FileOutputStream fos = new FileOutputStream(srcFile);
			int count = 0;

			//受信
			// バッファのサイズ
			int bufSize = 256;
			int recvMsgSize;
			byte buf[] = new byte[bufSize];
			while( (recvMsgSize = is.read(buf) ) != 1){
				fos.write(buf);
			}

			BufferedImage srcImg = ImageIO.read(srcFile);

			//TYPE_CUSTOMははじく
			if(srcImg.getType() == BufferedImage.TYPE_CUSTOM){
//				os.write(1);
				fos.close();
				clntSock.close();
				continue;
			}

			int h = srcImg.getHeight();
			int w = srcImg.getWidth();

			int src2d[][] = new int[h][w];

			ImageDecoder.exec(srcImg,src2d,srcFile);

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
			//ファイルごと
			ImageIO.write(dstImg, "bmp", os);
//			ImageIO.write(dstImg, "bmp", new File("D:/tmp/"+cnt+".png"));
			cnt++;

			fos.close();
			clntSock.close();
		}
	}
}