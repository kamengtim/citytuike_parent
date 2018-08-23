package com.citytuike.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class SHA1 {
	
	/*public static String encrypt(String input){
		final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        return "";
	    }
	    byte[] buf = md.digest(input.getBytes());
	    char[] chars = new char[2 * buf.length];
	    for (int i = 0; i < buf.length; ++i) {
	        chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
	        chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
	    }
	    return new String(chars);
	}*/
	
	public static String encrypt(String str){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(str.getBytes("UTF-8"));
			byte[] digest = md.digest();
	
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String encrypt(String[] array){
		StringBuffer sb = new StringBuffer();
		Arrays.sort(array);
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
		}
		String str = sb.toString();
		
		return encrypt(str);
	}

}
