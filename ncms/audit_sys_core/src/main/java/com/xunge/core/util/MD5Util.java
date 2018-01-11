package com.xunge.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author SongJL
 * @Description 加密处理
 */
public class MD5Util {
	
	public static String encode(String str) throws Exception{
		return EncoderByMd5(str);
	}

	/**利用MD5进行加密
	 　　* @param str  待加密的字符串
	　　* @return  加密后的字符串
	　　* @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
	　　 * @throws UnsupportedEncodingException  
	　　*/
	private static String EncoderByMd5(String plainText) throws Exception{
		try {  
             MessageDigest md = MessageDigest.getInstance("MD5");  
             md.update(plainText.getBytes());  
             byte b[] = md.digest();  
   
             int i;  
   
             StringBuffer buf = new StringBuffer("");  
             for (int offset = 0; offset < b.length; offset++) {  
                 i = b[offset];  
                 if (i < 0)  
                     i += 256;  
                 if (i < 16)  
                     buf.append("0");  
                 buf.append(Integer.toHexString(i));  
             }  
             //32位加密  
             return buf.toString();  
             // 16位的加密  
             //return buf.toString().substring(8, 24);  
         } catch (NoSuchAlgorithmException e) {  
             e.printStackTrace();  
             return null;  
         }
	}
}
