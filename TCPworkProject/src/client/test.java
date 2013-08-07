package client;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class test {

	public static void main(String[] args) throws IOException{

		String srcDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/src";
		String dstDirPath = "C:/Users/Yamaguchi/git/TCPwork/TCPworkProject/resources/debug/dst/";
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();
		System.out.println(srcFiles.length);
		for(File srcFile : srcFiles){

			BufferedImage srcImg = ImageIO.read(srcFile);
			WritableRaster srcRas = srcImg.getRaster();
			DataBuffer srcBuf = srcRas.getDataBuffer();
			
			String path = srcFile.getName();
			String[] name = path.split("\\.");
			
			String dstFilePath = dstDirPath + name[0] + ".bmp";
			File dstFile = new File(dstFilePath);
			
			ImageIO.write(srcImg, "bmp", dstFile);

		}
	}

}


