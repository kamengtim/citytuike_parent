package com.citytuike.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
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
    public static String getDateAndNumber(int length){
        String num = CreateDate();
        System.out.println("hhh:" + num);
        if (length > num.length()){
            num = num + getBigString(length-num.length());
        }
        return num;
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
    public static String getNowDate(){
        String dataStr=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
     /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
     public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
     }
      /**
      * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
      *
      * @param dateDate
      * @return
      */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    //0点
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }
    //23点
    public static Date getnowEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
    public static String getNowToString(){
        //使用Calendar
//        Calendar now = Calendar.getInstance();
       /* System.out.println("年：" + now.get(Calendar.YEAR));
        System.out.println("月：" + (now.get(Calendar.MONTH) + 1));
        System.out.println("日：" + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时：" + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分：" + now.get(Calendar.MINUTE));
        System.out.println("秒：" + now.get(Calendar.SECOND));*/
//使用Date
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(d));
        return sdf.format(d);
    }

    public static void  main(String[] args) {
        //使用Calendar
        Calendar now = Calendar.getInstance();
        System.out.println("年：" + now.get(Calendar.YEAR));
        System.out.println("月：" + (now.get(Calendar.MONTH) + 1));
        System.out.println("日：" + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时：" + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分：" + now.get(Calendar.MINUTE));
        System.out.println("秒：" + now.get(Calendar.SECOND));
        //方法 一

//方法 二
        Calendar.getInstance().getTimeInMillis();
//方法 三
        new Date().getTime();
//使用Date
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + System.currentTimeMillis());
        System.out.println("当前时间：" + Calendar.getInstance().getTimeInMillis());
        System.out.println("当前时间：" + new Date());
        System.out.println("当前时间：" + getDateAndNumber(16));
    }

}
