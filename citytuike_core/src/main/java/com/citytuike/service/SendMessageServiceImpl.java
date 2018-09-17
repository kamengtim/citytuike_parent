package com.citytuike.service;

import com.citytuike.eucp.inter.http.v1.dto.request.MoRequest;
import com.citytuike.eucp.inter.http.v1.dto.request.ReportRequest;
import com.citytuike.eucp.inter.http.v1.dto.request.SmsSingleRequest;
import com.citytuike.eucp.inter.http.v1.dto.response.MoResponse;
import com.citytuike.eucp.inter.http.v1.dto.response.ReportResponse;
import com.citytuike.eucp.inter.http.v1.dto.response.SmsResponse;
import com.citytuike.exception.SendMessageException;
import com.citytuike.mapper.*;
import com.citytuike.mobileutil.AES;
import com.citytuike.mobileutil.GZIPUtils;
import com.citytuike.mobileutil.JsonHelper;
import com.citytuike.mobileutil.http.*;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpSmsLog;
import com.citytuike.model.TpUserAddress;
import com.citytuike.model.TpUsers;
import com.citytuike.util.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        tpSmsLog.setMobile(mobile);
        tpSmsLog.setSession_id("a4nlq1f02mmatnrlrbr8vhjpt5");
        tpSmsLog.setAdd_time((int) (new Date().getTime()/1000));
        tpSmsLog.setCode(randCode);
        tpSmsLog.setStatus(1);
        tpSmsLog.setMsg(content);
        tpSmsLog.setScene(Integer.parseInt(scene));
        tpSmsLog.setError_msg("");
        logMapper.saveMsg(tpSmsLog);
        }catch (Exception e){
         tpSmsLog.setStatus(0);
         logMapper.updateByStatus(tpSmsLog);
         throw new SendMessageException("发送超时");
        }
    }

    private void sendReadMsg(String content, String mobile) {
        // appId
        String appId = "EUCP-EMY-SMS1-03GEW";// 请联系销售，或者在页面中 获取
        // 密钥
        String secretKey = "4EAEC2AEAF5D1223";// 请联系销售，或者在页面中 获取
        // 接口地址
        String host = "http://shmtn.b2m.cn:80";// 请联系销售获取
        // 加密算法
        String algorithm = "AES/ECB/PKCS5Padding";
        // 编码
        String encode = "UTF-8";
        // 是否压缩
        boolean isGizp = true;
        // 获取状态报告
        getReport(appId, secretKey, host, algorithm, isGizp, encode);
        // 获取上行
       // getMo(appId, secretKey, host, algorithm, isGizp, encode);
        // 发送单条短信
        try{
        setSingleSms(appId, secretKey, host, algorithm, content, null, null, mobile, isGizp, encode);//短信内容请以商务约定的为准，如果已经在通道端绑定了签名，则无需在这里添加签名
        }catch (Exception e){
         throw new SendMessageException("发送超时");
        }
    }
    /**
     * 获取状态报告
     *
     * @param isGzip
     *            是否压缩
     */
    private static void getReport(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
        System.out.println("=============begin getReport==================");
        ReportRequest pamars = new ReportRequest();
        ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/getReport", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            ReportResponse[] response = JsonHelper.fromJson(ReportResponse[].class, result.getResult());
            if (response != null) {
                for (ReportResponse d : response) {
                    System.out.println("result data : " + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + "," + d.getDesc() + ","
                            + d.getSubmitTime() + "," + d.getReceiveTime());
                }
            }
        }
        System.out.println("=============end getReport==================");
    }

    /**
     * 获取上行
     *
     * @param isGzip
     *            是否压缩
     */
    private static void getMo(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
        System.out.println("=============begin getMo==================");
        MoRequest pamars = new MoRequest();
        ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/getMo", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            MoResponse[] response = JsonHelper.fromJson(MoResponse[].class, result.getResult());
            if (response != null) {
                for (MoResponse d : response) {
                    System.out.println("result data:" + d.getContent() + "," + d.getExtendedCode() + "," + d.getMobile() + "," + d.getMoTime());
                }
            }
        }
        System.out.println("=============end getMo==================");
    }

    /**
     * 发送单条短信
     *
     * @param isGzip
     *            是否压缩
     */
    private static void setSingleSms(String appId, String secretKey, String host, String algorithm, String content, String customSmsId, String extendCode, String mobile, boolean isGzip, String encode) {
        System.out.println("=============begin setSingleSms==================");
        SmsSingleRequest pamars = new SmsSingleRequest();
        pamars.setContent(content);
        pamars.setCustomSmsId(customSmsId);
        pamars.setExtendedCode(extendCode);
        pamars.setMobile(mobile);
        ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendSingleSMS", isGzip, encode);
        System.out.println("result code :" + result.getCode());
        if ("SUCCESS".equals(result.getCode())) {
            SmsResponse response = JsonHelper.fromJson(SmsResponse.class, result.getResult());
            if (response != null) {
                System.out.println("data : " + response.getMobile() + "," + response.getSmsId() + "," + response.getCustomSmsId());
            }
        }
        System.out.println("=============end setSingleSms==================");
    }
    /**
     * 公共请求方法
     */
    public static ResultModel request(String appId, String secretKey, String algorithm, Object content, String url, final boolean isGzip, String encode) {
        Map<String, String> headers = new HashMap<String, String>();
        HttpRequest<byte[]> request = null;
        try {
            headers.put("appId", appId);
            headers.put("encode", encode);
            String requestJson = JsonHelper.toJsonString(content);
            System.out.println("result json: " + requestJson);
            byte[] bytes = requestJson.getBytes(encode);
            System.out.println("request data size : " + bytes.length);
            if (isGzip) {
                headers.put("gzip", "on");
                bytes = GZIPUtils.compress(bytes);
                System.out.println("request data size [com]: " + bytes.length);
            }
            byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes(), algorithm);
            System.out.println("request data size [en] : " + parambytes.length);
            HttpRequestParams<byte[]> params = new HttpRequestParams<byte[]>();
            params.setCharSet("UTF-8");
            params.setMethod("POST");
            params.setHeaders(headers);
            params.setParams(parambytes);
            params.setUrl(url);
            if (url.startsWith("https://")) {
                request = new HttpsRequestBytes(params, null);
            } else {
                request = new HttpRequestBytes(params);
            }
        } catch (Exception e) {
            System.out.println("加密异常");
            e.printStackTrace();
        }
        HttpClient client = new HttpClient();
        String code = null;
        String result = null;
        try {
            HttpResponseBytes res = client.service(request, new HttpResponseBytesPraser());
            if (res == null) {
                System.out.println("请求接口异常");
                return new ResultModel(code, result);
            }
            if (res.getResultCode().equals(HttpResultCode.SUCCESS)) {
                if (res.getHttpCode() == 200) {
                    code = res.getHeaders().get("result");
					if (code.equals("SUCCESS")) {
						byte[] data = res.getResult();
						System.out.println("response data size [en and com] : " + data.length);
						data = AES.decrypt(data, secretKey.getBytes(), algorithm);
						if (isGzip) {
							data = GZIPUtils.decompress(data);
						}
						System.out.println("response data size : " + data.length);
						result = new String(data, encode);
						System.out.println("response json: " + result);
					}
                } else {
                    System.out.println("请求接口异常,请求码:" + res.getHttpCode());
                }
            } else {
                System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
            }
        } catch (Exception e) {
            System.out.println("解析失败");
            e.printStackTrace();
        }
        ResultModel re = new ResultModel(code, result);
        return re;
    }
}
