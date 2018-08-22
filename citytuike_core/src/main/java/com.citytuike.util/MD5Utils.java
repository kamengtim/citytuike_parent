package com.citytuike.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	 /**
     * 使用md5的算法进行加密
     */
    public static String md5(String password) {
    	 try {
             // 得到一个信息摘要器
             MessageDigest digest = MessageDigest.getInstance("md5");
             byte[] result = digest.digest(password.getBytes());
             StringBuffer buffer = new StringBuffer();
             // 把每一个byte 做一个与运算 0xff;
             for (byte b : result) {
                 // 与运算
                 int number = b & 0xff;// 加盐
                 String str = Integer.toHexString(number);
                 if (str.length() == 1) {
                     buffer.append("0");
                 }
                 buffer.append(str);
             }

             // 标准的md5加密后的结果
             return buffer.toString();
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
             return "";
         }

    }
    public static void main(String[] args) {
		System.out.println("加密后:" + Util.generateString(16));
		System.out.println("加密后:" + System.currentTimeMillis()+Util.generateString(16));
		System.out.println("加密后:" + MD5Utils.md5(System.currentTimeMillis()+Util.generateString(16)));
	}
}
