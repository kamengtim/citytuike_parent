package com.citytuike.util;

import java.util.Random;

public class Util {
	
	 public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";  
	
	/** 
     * 返回一个定长的随机字符串(只包含大小写字母、数字) 
     *  
     * @param length 
     *            随机字符串长度 
     * @return 随机字符串 
     */  
    public static String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }  
    public static String getJson(String obj){
        obj = obj.replace("\\", "");
        char[] array = obj.toCharArray();
        char[] charArray = new char[array.length - 2];

        for (int i = 1; i < array.length - 1; i++)
        {
            charArray[i - 1] = array[i];

        }
        return new String(charArray);
    }

}
