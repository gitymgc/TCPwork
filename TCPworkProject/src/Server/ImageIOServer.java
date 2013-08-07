package Server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ImageIOServer {

	private static final int BUFSIZE = 32;

	public static void main(String[] args) throws IOException{

		new ImageIOServer().exec();

	}

	public void exec() throws IOException{
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

			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();


			BufferedImage srcImg = ImageIO.read(in);
			String dstFilePath = dstDirPath + cnt+".bmp";
//			cnt++;
			File dstFile = new File(dstFilePath);
			
			if(srcImg == null){
				System.out.println(cnt);
				cnt++;
				clntSock.close();
				
			}else{
				
//				System.out.println("ok");
				cnt++;
				srcImg.getType();
				ImageIO.write(srcImg, "bmp",out);
			}
			clntSock.close();
		}
	}
}
