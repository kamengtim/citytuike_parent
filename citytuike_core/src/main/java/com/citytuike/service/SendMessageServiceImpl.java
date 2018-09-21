package com.citytuike.service;


import cn.emay.sdk.client.SmsSDKClient;
import cn.emay.sdk.core.dto.sms.request.RetrieveReportRequest;
import cn.emay.sdk.core.dto.sms.request.SmsBatchOnlyRequest;
import cn.emay.sdk.core.dto.sms.response.RetrieveReportResponse;
import cn.emay.sdk.util.exception.SDKParamsException;
import com.citytuike.exception.SendMessageException;
import com.citytuike.mapper.*;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpSmsLog;
import com.citytuike.model.TpUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class SendMessageServiceImpl implements SendMessageService {
    @Autowired
    private TpSmsTemplateMapper tpSmsTemplateMapper;
    @Autowired
    private TpSmsLogMapper logMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Autowired
    private TpOrderMapper tpOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    public void sendVerifyCode(String type, String scene, String mobile, String send, String verify_code, String unique_id) {
        String content= "";
        //生成验证码
        String randCode = String.valueOf((Math.random() * 9 + 1) * 1000000).toString().substring(0, 6);
        String msg = tpSmsTemplateMapper.selectScene(scene);
        if(scene.equals("6")){
        content = msg.replace("${code}",randCode);
        sendReadMsg(content,mobile);
        }else if(scene.equals("1")){
        content = msg.replace("${code}",randCode);
        }else if(scene.equals("5")){
        TpUsers tpUsers =  tpUsersMapper.getUserNameByMobile(mobile);
        TpOrder tpOrder = tpOrderMapper.getConsigneeByMobile(tpUsers.getUser_id());
        content = msg.replace("${consignee}",tpOrder.getConsignee());
        content = content.replace("${user_name}",tpUsers.getNickname());
        content = content.replace("${order_sn}",tpOrder.getOrder_sn());
        sendReadMsg(content,mobile);
        }else if(scene.equals("2")){
        content = msg.replace("${code}",randCode);
        sendReadMsg(content,mobile);
        }
        TpSmsLog tpSmsLog = new TpSmsLog();
        try{
        //打印状态码
        tpSmsLog.setMobile(mobile);
        tpSmsLog.setSession_id("a4nlq1f02mmatnrlrbr8vhjpt5");
        tpSmsLog.setAdd_time((int) (new Date().getTime()/1000));
        tpSmsLog.setCode(randCode);
        tpSmsLog.setStatus(1);
        tpSmsLog.setMsg(content);
        tpSmsLog.setScene(Integer.parseInt(scene));
        tpSmsLog.setError_msg("");
        logMapper.saveMsg(tpSmsLog);
        redisTemplate.opsForValue().set(mobile,randCode);
        redisTemplate.expire(mobile,180, TimeUnit.SECONDS);
        }catch (Exception e){
         tpSmsLog.setStatus(0);
         logMapper.updateByStatus(tpSmsLog);
         throw new SendMessageException("发送超时");
        }
    }
    /*发送单条短信
    * */
    private void sendReadMsg(String OldContent, String OldMobile) {
        SmsSDKClient client = null;
        try {
            retrieveReport();
            client = new SmsSDKClient("http://shmtn.b2m.cn", 80, "EUCP-EMY-SMS1-03GEW", "4EAEC2AEAF5D1223");
        } catch (SDKParamsException e) {
            e.printStackTrace();
        }
        String mobile = OldMobile;
        String content = OldContent;
        String customSmsId = "1";
        String extendedCode = "01";
        cn.emay.sdk.core.dto.sms.request.SmsSingleRequest request = new cn.emay.sdk.core.dto.sms.request.SmsSingleRequest(mobile, content, customSmsId, extendedCode, "");
        cn.emay.sdk.core.dto.sms.common.ResultModel<cn.emay.sdk.core.dto.sms.response.SmsResponse> result = client.sendSingleSms(request);
        if (result.getCode().equals("SUCCESS")) {
            System.out.println("请求成功");
            cn.emay.sdk.core.dto.sms.response.SmsResponse response = result.getResult();
            System.out.println("sendSingleSms:" + response.toString());
        } else {
            System.out.println("请求失败");
        }
    }
    /*批量发送短信
    * */
    public static void sendBatchOnlySms() throws SDKParamsException {
        SmsSDKClient client = new SmsSDKClient("http://shmtn.b2m.cn", 80, "EUCP-EMY-SMS1-03GEW", "4EAEC2AEAF5D1223");
        String[] mobiles = { "13800000000", "13800000001" };
        String content = "短信内容";
        String extendedCode = "01";
        SmsBatchOnlyRequest request = new SmsBatchOnlyRequest(mobiles, content, "", extendedCode);
        cn.emay.sdk.core.dto.sms.common.ResultModel<cn.emay.sdk.core.dto.sms.response.SmsResponse[]> result = client.sendBatchOnlySms(request);
        if (result.getCode().equals("SUCCESS")) {
            System.out.println("请求成功");
            cn.emay.sdk.core.dto.sms.response.SmsResponse[] responses = result.getResult();
            for (cn.emay.sdk.core.dto.sms.response.SmsResponse response : responses) {
                System.out.println("sendBatchOnlySms:" + response.toString());
            }
        } else {
            System.out.println("请求失败");
        }
    }
    /*
    *状态码
    * */
    public static void retrieveReport() throws SDKParamsException {
        SmsSDKClient client = new SmsSDKClient("http://shmtn.b2m.cn", 80, "EUCP-EMY-SMS1-03GEW", "4EAEC2AEAF5D1223");
        String startTime = "20180120110000";
        String endTime = "20180120110500";
        String smsid = "15167713536420020356";
        RetrieveReportRequest reportRequest = new RetrieveReportRequest();
        reportRequest.setSmsId(smsid);
        reportRequest.setStartTime(startTime);
        reportRequest.setEndTime(endTime);
        cn.emay.sdk.core.dto.sms.common.ResultModel<RetrieveReportResponse> result = client.retrieveReport(reportRequest);
        if (result.getCode().equals("SUCCESS")) {
            System.out.println("请求成功");
            RetrieveReportResponse response = result.getResult();
            System.out.println("retrieveReport:" + response.getCode());
        } else {
            System.out.println("请求失败");
        }

    }
}
