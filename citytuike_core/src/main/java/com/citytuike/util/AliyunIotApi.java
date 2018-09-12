package com.citytuike.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20170420.PubRequest;
import com.aliyuncs.iot.model.v20170420.PubResponse;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailRequest;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailResponse;
import org.apache.commons.codec.binary.Base64;

public class AliyunIotApi {
    private DefaultAcsClient client;
    public void _construct(){
        client = ClientUtil.createClient();
    }

    public PubResponse put(String product_key, String device_name,String paper_token) {
        PubRequest request = new PubRequest();
        String topic = "/"+ product_key +"/" + device_name +"/"+ "get";
        request.setProductKey(product_key);
        request.setMessageContent(Base64.encodeBase64String(("{'command': '1','message': 'hello','token': '"+paper_token+"'}").getBytes()));
        request.setTopicFullName(topic);
        request.setQos(0); //目前支持QoS0和QoS1

        PubResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
    /*
    * 查看设备状态
    * */
    public QueryDeviceDetailResponse queryDeviceDetailRequest(String product_key, String device_name) {
        QueryDeviceDetailRequest request = new QueryDeviceDetailRequest();
        request.setProductKey(product_key);
        QueryDeviceDetailResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}
