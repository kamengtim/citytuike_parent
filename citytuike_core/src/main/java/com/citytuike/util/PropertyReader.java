package com.citytuike.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
public class PropertyReader {
    private static Properties props;
    private static String dbtype = "";
    private static String fileName = "db.properties";

    /**
     * 此方法只支持读取src目录property文件
     * @param
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String get(String proName){
        if(fileName != null && !"".equals(fileName)){
            props = new Properties();
            try {
                props.load(PropertyReader.class.getClassLoader().getResourceAsStream(fileName));
                dbtype = new String(props.getProperty(proName).getBytes("ISO-8859-1"),"utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dbtype;
    }

}
