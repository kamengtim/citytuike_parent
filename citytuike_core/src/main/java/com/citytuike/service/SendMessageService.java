package com.citytuike.service;

public interface SendMessageService {
    /*
    * 短信发送
    **/
    void sendVerifyCode(String type, String scene, String mobile, String send, String verify_code, String unique_id);
}
