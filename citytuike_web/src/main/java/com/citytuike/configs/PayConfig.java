package com.citytuike.configs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.citytuike.constant.Constant;
import com.citytuike.util.Config;
import com.yeepay.g3.sdk.yop.encrypt.CertTypeEnum;
import com.yeepay.g3.sdk.yop.encrypt.DigestAlgEnum;
import com.yeepay.g3.sdk.yop.encrypt.DigitalSignatureDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;
import com.yeepay.g3.sdk.yop.utils.InternalConfig;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PayConfig {
    public static String format(String text){
        return text==null?"":text.trim();
    }
    //将获取到的response解密完成的json转换成map
    public static Map<String, String> parseResponse(String response){

        Map<String,String> jsonMap  = new HashMap<>();
        jsonMap	= JSON.parseObject(response,
                new TypeReference<TreeMap<String,String>>() {});

        return jsonMap;
    }
    //标准收银台——拼接支付链接
    public static String getUrl(Map<String,String> paramValues) throws UnsupportedEncodingException {
        StringBuffer url = new StringBuffer();
        url.append(Config.getInstance().getValue("CASHIER"));
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(paramValues);
        for (int i = 0; i < Constant.CASHIER.length; i++) {
            String name = Constant.CASHIER[i];
            String value = paramValues.get(name);
            if(i != 0){
                stringBuilder.append("&");
            }
            stringBuilder.append(name+"=").append(value);
        }
        System.out.println("stringbuilder:"+stringBuilder);
        String sign = getSign(stringBuilder.toString());
        url.append("?sign="+sign+"&"+stringBuilder);
        return url.toString();
    }
    //获取sign
    public static String getSign(String stringBuilder){
        String appKey = "OPR:"+Config.getInstance().getValue("merchantNo");

        PrivateKey isvPrivateKey = getSecretKey();

        DigitalSignatureDTO digitalSignatureDTO = new DigitalSignatureDTO();
        digitalSignatureDTO.setAppKey(appKey);
        digitalSignatureDTO.setCertType(CertTypeEnum.RSA2048);
        digitalSignatureDTO.setDigestAlg(DigestAlgEnum.SHA256);
        digitalSignatureDTO.setPlainText(stringBuilder.toString());
        String sign = DigitalEnvelopeUtils.sign0(digitalSignatureDTO,isvPrivateKey);
        return sign;
    }
    //获取密钥P12
    public static PrivateKey getSecretKey(){
        PrivateKey isvPrivateKey = InternalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
        return isvPrivateKey;
    }
}
