package com.citytuike.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util<main> {
	
	 public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";  
	 public static final String BIGCHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
    public static String getBigString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(BIGCHAR.charAt(random.nextInt(BIGCHAR.length())));
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
    /**
     * 时间戳
     * @return
     */
    public static String CreateDate(){
        String dataStr=null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        dataStr = sdf.format(new Date());
        return dataStr;
    }
    /**
     * 失效时间 当前时间+10min
     * @return
     */
    public static String expriredDate(String dataStr){
        String aDataStr=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            Date date = sdf.parse(dataStr);
            date.setTime(date.getTime()+10*60*1000);
            aDataStr=sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return aDataStr;
    }
}
