package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException{

		String srcDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/src";
		String dstDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/dst/";
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();
		System.out.println(srcFiles.length);
		//		for(File srcFile : srcFiles){
		File srcFile = srcFiles[0];
		FileInputStream fis = new FileInputStream(srcFile);
		int data = 0;
		System.out.println(fis.read());
//		while( (data = fis.read() ) != -1){
//			System.out.println(data);
//			//				out.write(data);
//		}

		//			BufferedImage srcImg = ImageIO.read(srcFile);
		//			WritableRaster srcRas = srcImg.getRaster();
		//			DataBuffer srcBuf = srcRas.getDataBuffer();
		//			
		//			String path = srcFile.getName();
		//			String[] name = path.split("\\.");

		//			String dstFilePath = dstDirPath + name[0] + ".bmp";
		//			File dstFile = new File(dstFilePath);
		//			
		//			ImageIO.write(srcImg, "bmp", dstFile);

		//		}
	}

}


