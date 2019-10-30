
package com.newlife.s4.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.imageio.ImageIO;


/**
 * @author zengxiangxin
 *
 */
public class FileUtil {
	/**
	 * InputStream 装换成 String
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * Stream 转换 Bytes
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static final byte[] inputStream2byte(InputStream inStream)  
			throws IOException {  
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
		byte[] buff = new byte[100];  
		int rc = 0;  
		while ((rc = inStream.read(buff, 0, 100)) > 0) {  
			swapStream.write(buff, 0, rc);  
		}  
		byte[] in2b = swapStream.toByteArray();  
		return in2b;  
	}  

	/**
	 * Bytes 转换 Stream
	 * 
	 * @param bytes
	 * @return
	 */
	public static final InputStream byte2inputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes); 
	}

	public static final void inputStream2File(InputStream is, String outputPath) {
		try {
			File file = new File(outputPath);    
			OutputStream os = new FileOutputStream(file);  

			byte[] byteStr = new byte[1024];    
			int len = 0;    
			while ((len = is.read(byteStr)) > 0) {    
				os.write(byteStr,0,len);    
			}    
			is.close();    

			os.flush();    
			os.close();    
		} catch (FileNotFoundException e) {    
			// TODO Auto-generated catch block    
			e.printStackTrace();    
		} catch (IOException e) {    
			// TODO Auto-generated catch block    
			e.printStackTrace();    
		}  
	}

	

	private static Rectangle calcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;

		if(des_width > 1000 && des_height > 1000) {
			des_width = new BigDecimal(des_width).multiply(new BigDecimal(0.5)).intValue();
			des_height = new BigDecimal(des_height).multiply(new BigDecimal(0.5)).intValue();
		}else if(des_width > 2000 || des_height > 2000) {
			des_width = new BigDecimal(des_width).multiply(new BigDecimal(0.4)).intValue();
			des_height = new BigDecimal(des_height).multiply(new BigDecimal(0.4)).intValue();
		}else if(des_width > 3000 || des_height > 3000) {
			des_width = new BigDecimal(des_width).multiply(new BigDecimal(0.3)).intValue();
			des_height = new BigDecimal(des_height).multiply(new BigDecimal(0.3)).intValue();
		}else{
			des_width = new BigDecimal(des_width).multiply(new BigDecimal(0.2)).intValue();
			des_height = new BigDecimal(des_height).multiply(new BigDecimal(0.2)).intValue();
		}

		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}
}
