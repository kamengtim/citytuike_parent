package com.citytuike.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.input.XmlStreamReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;


public class XmlUtil {
	 /* 
     * xml转map 
     */  
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{  
        HashMap<String, String> map = new HashMap<String,String>();  
        SAXReader reader = new SAXReader();  
  
        InputStream ins = request.getInputStream();  
        Document doc = reader.read(ins);  
  
        Element root = doc.getRootElement();  
        @SuppressWarnings("unchecked")  
        List<Element> list = (List<Element>)root.elements();  
  
        for(Element e:list){  
            map.put(e.getName(), e.getText());  
        }  
        ins.close();  
        return map;  
    }  
    /* 
     * 文本消息对象转xml 
     */  
    public static String textMsgToxml(TextsMessage textMessage){  
        XStream xstream = new XStream();  
        xstream.alias("xml", textMessage.getClass());  
        return xstream.toXML(textMessage);  
    }  
    /**
	 * InputStream流转换成String字符串
	 * @param inStream InputStream流
	 * @param encoding 编码格式
	 * @return String字符串
	 */
	public static String inputStream2String(InputStream inStream, String encoding){
	 String result = null;
     int _buffer_size = 1024;
	 try {
	 if(inStream != null){
	  ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	  byte[] tempBytes = new byte[_buffer_size ];
	  int count = -1;
	  while((count = inStream.read(tempBytes, 0, _buffer_size)) != -1){
	    outStream.write(tempBytes, 0, count);
	  }
	  tempBytes = null;
	  outStream.flush();
	  result = new String(outStream.toByteArray(), encoding);
	 }
	 } catch (Exception e) {
	 result = null;
	 }
	 return result;
	}
	@SuppressWarnings("unchecked")
	public static <T> Object stringToObject(String value, Class<T> c) {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream is;
			try {
//				String getString = value.replace((char) 0x00a0, ' ');// 转换特殊字符
				is = new ByteArrayInputStream(value.getBytes("UTF-8"));
				XmlStreamReader reader = null;
				reader = new XmlStreamReader(is);
				return (T) unmarshaller.unmarshal(reader);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
//				throw new BusinessException(
//						ErrorConstants.ERROR_UNSUPPORTED_ENCODING_EXCEPTION);
			} catch (IOException e) {
				e.printStackTrace();
//				throw new BusinessException(ErrorConstants.ERROR_IO_EXCEPTION);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
//			throw new BusinessException(ErrorConstants.ERROR_JAXB_EXCEPTION);
		} catch (Exception e) {
			e.printStackTrace();
//			throw new BusinessException(ErrorConstants.ERROR_UNKNOWN_EXCEPTION);
		}
		return null;
	}
}
