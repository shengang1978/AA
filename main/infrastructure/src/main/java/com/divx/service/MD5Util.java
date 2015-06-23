package com.divx.service;


import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class MD5Util {

	public static String getMd5ByFile(String path){ 
		String md5String = "";
		try{
			char hexDigits[] =
				{ 
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
				'c', 'd', 'e', 'f'
				};
			  File file = new File(path);
	          FileInputStream in = new FileInputStream(file);  
	     
	          MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
	          MessageDigest md5 = MessageDigest.getInstance("MD5");  
	          md5.update(byteBuffer);  
	          byte tmp[] = md5.digest(); 
	          char str[] = new char[16 * 2];  
	          int k = 0; 
	          for (int i = 0; i < 16; i++)
	          { 
	        	  byte byte0 = tmp[i];
	        	  str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
	        	  str[k++] = hexDigits[byte0 & 0xf]; 
	          }
	          in.close();
	          md5String = new String(str); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return md5String;
    }  
   

}
