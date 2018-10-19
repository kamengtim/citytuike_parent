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
	 public static final String NUMCHAR = "0123456789";
    private static double EARTH_RADIUS = 6378.137;// 单位千米
	/** 
     * 返回一个定长的随机字符串(只包含数字)
     *  
     * @param length 
     *            随机字符串长度 
     * @return 随机字符串 
     */  
    public static String numberString(int length) {
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(NUMCHAR.charAt(random.nextInt(NUMCHAR.length())));
        }  
        return sb.toString();  
    }  
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
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate0(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate1(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    public static String stampToDate2(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    public static String stampToDate3(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
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
    /**
     * 角度弧度计算公式 rad:(). <br/>
     *
     * 360度=2π π=Math.PI
     *
     * x度 = x*π/360 弧度
     *
     * @author chiwei
     * @return
     * @since JDK 1.6
     */
    private static double getRadian(double degree) {
        return degree * Math.PI / 180.0;
    }
    /**
     * 依据经纬度计算两点之间的距离 GetDistance:(). <br/>
     *
     *
     * @author chiwei
     * @param lat1
     *            1点的纬度
     * @param lng1
     *            1点的经度
     * @param lat2
     *            2点的纬度
     * @param lng2
     *            2点的经度
     * @return 距离 单位 米
     * @since JDK 1.6
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = getRadian(lat1);
        double radLat2 = getRadian(lat2);
        double a = radLat1 - radLat2;// 两点纬度差
        double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s * 1000;
    }
    public static void  main(String[] args) {
        //使用Calendar
//        Calendar now = Calendar.getInstance();
//        System.out.println("年：" + now.get(Calendar.YEAR));
//        System.out.println("月：" + (now.get(Calendar.MONTH) + 1));
//        System.out.println("日：" + now.get(Calendar.DAY_OF_MONTH));
//        System.out.println("时：" + now.get(Calendar.HOUR_OF_DAY));
//        System.out.println("分：" + now.get(Calendar.MINUTE));
//        System.out.println("秒：" + now.get(Calendar.SECOND));
//        //方法 一
//
////方法 二
//        Calendar.getInstance().getTimeInMillis();
////方法 三
//        new Date().getTime();
////使用Date
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("当前时间：" + System.currentTimeMillis());
//        System.out.println("当前时间：" + Calendar.getInstance().getTimeInMillis());
//        System.out.println("当前时间：" + new Date());
//        System.out.println("当前时间：" + getDateAndNumber(16));
        String date = Calendar.getInstance().getTimeInMillis() + "";
        String sss = stampToDate1(date);
        System.out.println(sss);
    }
    public static String transferLongToDate(String dateFormat, Long millSec){
        String result = null;
        Date date = new Date(millSec*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        result =  sdf.format(date);
        return result;

    }
}
