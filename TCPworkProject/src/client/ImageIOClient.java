package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

public class ImageIOClient {
	public static void main(String[] args) throws Exception{

		new ImageIOClient().exec(args);
	}

	public void exec(String[] args) throws Exception{

		String srcFilePath = "D:/tmp/001.bmp";
		File srcFile = new File(srcFilePath);
		BufferedImage srcImg = ImageIO.read(srcFile);
		
		if((args.length < 2)){
			throw new IllegalArgumentException("Parameter(s) : <Server> [<Port>]");
		}

		String server = args[0];
		int servPort = (args.length == 2)? Integer.parseInt(args[1]) :10514;
		Socket sock = null;

		try {
			sock = new Socket(server, servPort);
		} catch (UnknownHostException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("Connected to server ...sending echo string");

		InputStream in = null;
		try {
			in = sock.getInputStream();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		OutputStream out = null;
		try {
			out = sock.getOutputStream();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		
		ImageIO.write(srcImg, "bmp", out);
		
		
		
		
		try {
			sock.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
