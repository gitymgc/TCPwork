package client;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class test {

	public static void main(String[] args) throws IOException{

		
		int dst2d[][] = new int[256][256];
		dst2d[0][0] = 255;
		dst2d[1][1] = 255;
		
		
		BufferedImage dstImg = new BufferedImage(256, 256, BufferedImage.TYPE_BYTE_GRAY);
		DataBuffer dstBuf = dstImg.getRaster().getDataBuffer();
		
		for(int y = 0; y < 4; y++)
			for(int x = 0; x < 4; x++)
				dstBuf.setElem(y*4+x, dst2d[y][x]);
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dstImg, "jpg", baos);
		
//		ImageIO.write(dstImg, "bmp", new File("D:/tmp/001.bmp"));
		
		
		byte b[] = baos.toByteArray();

System.out.println(b.length);
	}

}


