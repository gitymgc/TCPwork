package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

			InputStream in = sock.getInputStream();
			OutputStream out = sock.getOutputStream();
			//			BufferedOutputStream out = 
			//					new BufferedOutputStream(
			//					sock.getOutputStream()
			//					);

			FileInputStream fis = new FileInputStream(srcFile);
			int count = 0;
	
//			//送信
//			int data = 0;
//			while( (data = fis.read() ) != -1){
//				//				Thread.sleep(1000);
//				out.write(data);
//				System.out.println(count++);
//
//
//			}
//			out.flush();
//			System.out.println("while 出た");

			int recvMsgSize;
			int bufSize = 256;
			byte buf[] = new byte[bufSize];
			
			while( (fis.read(buf) != -1)){
				out.write(buf, 0, bufSize);
			}
			System.out.println("while 出た");

			
			int res = -1;
//			while(res == -1){
//				System.out.println("kita");
//				res = in.read();
//			}
			res = in.read();
			System.out.println(res);
			
			//			System.out.println("kita");
			//			System.out.println(res);
			//			if(res == 1){
			//				System.out.println("BufferedImage.TYPE_CUSTOM は処理出来ません。");
			//				sock.close();
			//			} else {
			//				System.out.println("処理開始");
			//			}

			// グレイスケール画像を取得
			fis.close();
			sock.close();
		}
	}
}
