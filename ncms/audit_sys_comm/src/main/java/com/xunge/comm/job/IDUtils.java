package com.xunge.comm.job;

import java.util.UUID;

public class IDUtils {

	private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9" };
	private static String[] alphabet = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	
	
	public static String getID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	
	public static String get8UUID() {

		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			if (i == 0) {
				shortBuffer.append(alphabet[x % 0x1a]);
			} else {
				shortBuffer.append(chars[x % 0x24]);
			}
		}
		return shortBuffer.toString();
	}
}
