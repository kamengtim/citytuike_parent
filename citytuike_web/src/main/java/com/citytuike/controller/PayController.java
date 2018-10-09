package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.citytuike.configs.PayConfig;
import com.citytuike.constant.Constant;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpPlugin;
import com.citytuike.model.TpUsers;
import com.citytuike.service.ITpAccountLogService;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.AlipayConfig;
import com.citytuike.util.Config;
import com.citytuike.util.PayUtils;
import com.citytuike.util.Util;
import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.encrypt.CertTypeEnum;
import com.yeepay.g3.sdk.yop.encrypt.DigitalEnvelopeDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;
import com.yeepay.g3.sdk.yop.utils.InternalConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/Payment")
public class PayController extends BaseController{
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpOrderService tpOrderService;
    @Autowired
    private ITpAccountLogService iTpAccountLogService;


    //商品支付回调 投放广告支付回调  优惠券支付回调
    @RequestMapping(value="/pay_list",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "支付方式", notes = "支付方式")
    public @ResponseBody String payList(Model model,@RequestParam(required=false) String type){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");

        List<TpPlugin> tpPluginList = tpOrderService.findAllPlugin();
        JSONArray jsonArray = new JSONArray();
        for (TpPlugin tpplugin: tpPluginList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", tpplugin.getName());
            jsonObject.put("code", tpplugin.getCode());
            jsonObject.put("icon", tpplugin.getIcon());
            jsonArray.add(jsonObject);
        }
        data.put("list", jsonArray);
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    @RequestMapping(value = "/yop_payback", method = RequestMethod.GET)
    public @ResponseBody String yopPayback(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, AlipayApiException {
//        String response = new String(request.getParameter("response").getBytes("ISO-8859-1"),"UTF-8");
        String response="qNjNqLTYaSK98W9F04PghhPishrb_Z6u4g6oADsKxc_pwmajFQJZQnhVH5PeKrjf7SyvYr0VmZ2qZ66ev2PZohf0rSbkrTdV8yaN4MSvt4jhv2qBAaB39N9tDzr2avJ4uLZQ_QNvGG2cp4HJAp9h6Xa5LNkSqJrnxqzHQWzWDDIylNKirNeXp99qfip5yQYRaHE9B8CiJ8FaoY44d8J9udzdJPyABjpYbbEtGV6ad7xcmdE6EsXq6pC5JEbl8DDa1RJuwVA_ZFWABW7wJRgqwmJg_eQgiJ83C8HMg8nL0TVk7wp8hPUXLseOWbq021a7TDddn9mC8_i7l85BIvGVxA$6NFh3P1wfzZhMMwce5FxengavYXb_woW7KGriA2slwq5DNKu7ydSjQ1YuBiicFyxVHdtclXNptwJPCT8lGWKSxVRa9GWyljqGeG7nHNLaqTv65h5WGzidSlB1Z6HUN655Mf8ZtoVqs7p6kMotEbjDcBNT7KQBYJlrPJS70v2A1fco-9jzl4Ptaf0JFGCxwQv9erUM72KAbsZBWAKxGE9mjhlOB6H5z3Sb2qbVN9Sf-OYI9TZd_wQrnYH16OrYqFIIbzEHFmZLfYV5bwXu682W8Ewk4RJ0MSAL8-I-cdMMP7gUFweBpK2EOZTRyvkUBb5-i-qt9stRxlThfzTIgLeHOyI17WE_bAq7_84wl5iAbBlUxUdT87ArKFwyBVXzygwQUDvNlafzo6uYYQQd-keExyiMOj3YDTfTvvOPjjWxLbv65h5WGzidSlB1Z6HUN65tl6_6BIOnFDRpuCdhV4aDQtDszcvOWKpTar8RTCIduq0h2Ukk2B6UpSCb_2lPPAcTe8JM0LDr5yz0aM8zPBJdbHo_AoY1X4_aJ2CojGOMXUvMIbj6dViu9dQoBQHzv6krCL40XxBs3NB8qsuAfDj3lrpMDCnr6pRZpE20mfNw67c_Vh2K9miFTtwC0Cfp7SOlc2LPOiA-mGx-wdmwUM4cgl7GIiyX1nafzqF4gSvNn1phKf0IcnXtXfRTEFeHIAgllNxaPb-YfsQ_slWLmMbnJxDQFnRkFNiITC4P1ZyPZKdjEGyfHOHiRpgV8nywudTfCB_hDh0PVRyijcXXfnWncEvBz8k80XM4JBWIvO-DqyUVCzDoyv7-yCdb3a2B-ehTHNel7QGNu-U6iIrTiUlk236CYqxCKCdH79g0cPiHez1kL3rgeWds0nlK_XYyD29Sa0l3WEBcrdK38Szh_pEV1K0AYgh_zOTv3J3VV4ssNG736AebkCSK3-HJAr88O_i952OFXsMpUsLuNoI8QQMjxQeiWNkEAg2IEBIx-Bs1TJbApZwD8X_LZh1SYXxOLjqJppz3CZ7ZPSkOAQuyciB4CBxn-q5OYE4fcD8fbuYzEs$AES$SHA256";
//        String response="iZLsNqljI0S4Edin9NctaLxZZpF7coaX5GOB-jpYynO8Wyv2ELzZHMOStZm0vIZ-DQnX8m2E6LNv3AN2Z_k2-MGgFKAvFq2_BIM-JF3vSiaUkvW0YVyvJUSNBkv9v_JsFYDVsP5h4zbtZQbbB6Uavj53_LWWYYGriWl2qkyHQbxEqszDPgayGo45oeCoxVLZW8QWyClzKRIAuhAFp2IP7NtWhl7lFln_CO_g6KK6_QbUqYACUtxhfSznbXYfLPGtgc5uQIfUtSpJqWOx_zUd3-qEuFl27Z4vjSmoN93YlNDeDx6gMKJlKaPKuJp7aGX9ptj3w8PPN11TWEIpbd0t5A$sKGZIndTkBKi9kp8MAo-BVxqEk0UN_dlmmr5I3diLRmfqBs4E3LurNHBzhW1iZ9LO5sm5oP87r6-s7sYzZdmPtDayoLF7KkZaDlYyxuTRnFYUXo28dDR293fFuaTPSFfqH3omysxy2ncZyQPo2yky50RcMDxhLr2nwX714gnmk-cJBQ8MA6P87PfNgLoF5rPopFaj-PTXJlM6_coX2yo_6JDhG8TrB0Ig3ntLzA9kKOR_1vbCPizv0FtVaNKyfrxlUwabD-eIlxBXhmnt10NTuTcM18OFvUFTHxxBrR0dMEUJww0MvudKJEjVZwwh2Hmv4NOVAPk4c5zeVhiHTQHWG4JQmCxvpG-mltxbv-66dVbNi45julQN7AcrbCJwKChj6amyXVQUNkGkmQr53iCDFBG4GtxneKOQ9mIap27YtPihemFcuBF6vfii4m9bBpXvorIqGie4kDT_8U_IHAwByUHyTBZBYx6gmbDHPhqD4_pkSRKzs6lAN1z1A1fY-WCbEwCNMEPxr_r7-5yumrlXSrG4uiR_1wBt8vBTlv2ludl1rRKWVFxN5jaQQp0MahJIq1AssuORDF3o1fx0gwwf-gjkxkUfz39VNWW2vXPG4dEBFJgj8zgyUHrOxNsfTMT_6Mak77Qgej2d8fnh5DqNNSMrt8L4LRLC4gV98GiRjf8BfJXDgHhY3Wsskxr3EBLE1sKoV7bST5H9Fl0t3l0OKkHbHQyuVh7iYawyZwvpEXnHesbZr_ZaBieQfjUKHcvA0L0B_exFcWPDm8oQt01WZ9zP8a86Lc82l-Cll1gk0gwjtDw3cQZ5T20BdDm3TLfCRqWA9oEBprBpaHij6HJo0XB5Zw_V1YmDP1PSm9tQamXiwrNoP7FoI7JzOzVkq8YRXTaFlFDJBk8n8pRrlNVggui5mScWVPsmg-l2MZgR93IaVrETKzZTWfFlIy2Uebhs9WOfDgU7WgL9jUaZ28P2VzXs6r9EQgkK_D8iLKFVEE8vnMfLkm-41AWLFHMzl24XUkKKAzcwZaasJYeG_Ob-aGcnAOQSulMLPT0gymFvOOG6keM7zxxzie86usakW4Gpqziyv64VZ7HugfEvPE5C09KW7Ln4Tgbtagh1_x67iEEJKCCPuZhuUvkdMQ9OpYAhmCBkGaWCUFsNIqKO9_59NpYKny8xHklk1XXCWNsQCeE6VXXyWsrZywR4TgalLR6$AES$SHA256";
        try {
            //开始解密
            Map<String,String> jsonMap  = new HashMap<>();
            DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
            dto.setCipherText(response);
            //InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
            PrivateKey privateKey = InternalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
            System.out.println("privateKey: "+privateKey);
            PublicKey publicKey = InternalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
            System.out.println("publicKey: "+publicKey);

            dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
            System.out.println("解密结果:"+dto.getPlainText());
            jsonMap = PayConfig.parseResponse(dto.getPlainText());
            System.out.println(jsonMap);
            if (null != jsonMap){
                String parentMerchantNo = jsonMap.get("parentMerchantNo");
                String merchantNo = jsonMap.get("merchantNo");
                String merchantNoOld = PayConfig.format(Config.getInstance().getValue("merchantNo"));
                String parentMerchantNoOld = PayConfig.format(Config.getInstance().getValue("parentMerchantNo"));
                if (!parentMerchantNo.equals(parentMerchantNoOld) || !merchantNo.equals(merchantNoOld)){
                    System.out.println("商户号不对!!!");
                    return "FAIL";
                }
                String orderId = jsonMap.get("orderId");
                String uniqueOrderNo = jsonMap.get("uniqueOrderNo");
                String payAmount = jsonMap.get("payAmount");
                int result = PayUtils.getPayUtils().updateOrder(orderId, payAmount, uniqueOrderNo, 2);
                if (result == 0){
                    return "FAIL";
                }
                return "SUCCESS";
            }
            return "FAIL";
        } catch (Exception e) {
            throw new RuntimeException("回调解密失败！");
        }

    }
    @RequestMapping(value = "/alipay_payback", method = RequestMethod.GET)
    public @ResponseBody String payBack(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
        String seller_id = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"),"UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        if(verify_result){//验证成功
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            if (!seller_id.equals(AlipayConfig.UID)){
                System.out.println("商户号不一致!!!");
                return "fail";
            }
            int result = PayUtils.getPayUtils().updateOrder(out_trade_no, total_fee, trade_no, 1);
            if (result == 0){
                return "fail";
            }else if (result == 1){
                return "success";
            }
            return "fail";
       }else{//验证失败
            return "fail";
        }
    }

    @RequestMapping(value="/getCode",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "支付签名", notes = "支付签名")
    public @ResponseBody String yopTaketoken(HttpServletResponse resp, HttpServletRequest request,
                                             @RequestParam(required=true) String order_sn,
                                             @RequestParam(required=false) String openid,
                                             @RequestParam(required=true) String pay_code) throws IOException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpOrder tpOrder = tpOrderService.findOrderByOrderSn(order_sn);
        if (null != tpOrder){
            if (pay_code.equals("yop")){
                String merchantNo = PayConfig.format(Config.getInstance().getValue("merchantNo"));
                String parentMerchantNo=PayConfig.format(Config.getInstance().getValue("parentMerchantNo"));
                String privateKey=Config.getInstance().getValue("privatekey");
                Map<String, String> result = new HashMap<String, String>();
                //银联在线支付
                Map<String, String> params = new HashMap<>();
                params.put("orderId", tpOrder.getOrder_sn());
                params.put("orderAmount", tpOrder.getTotal_amount()+"");
                params.put("timeoutExpress", "");
                params.put("requestDate", "");
                params.put("redirectUrl", "");
                params.put("notifyUrl", Constant.YOP_PAY_NOTIFYURL);
                String name = "卡盟订单";
                params.put("goodsParamExt", "{\"goodsName\":\""+name+"\",\"goodsDesc\":\"\"}");
                params.put("paymentParamExt", "");
                params.put("industryParamExt", "");
                params.put("memo", "");
                params.put("riskParamExt", "");
                params.put("csUrl", "");
                params.put("fundProcessType", "");
                params.put("divideDetail", "");
                params.put("divideNotifyUrl", "");
                params.put("parentMerchantNo", parentMerchantNo);
                params.put("merchantNo", merchantNo);

                YopRequest yoprequest = new YopRequest("OPR:"+merchantNo, privateKey, Config.getInstance().getValue("baseURL"));
                for (int i = 0; i < Constant.TRADEORDER.length; i ++) {
                    String key = Constant.TRADEORDER[i];
                    yoprequest.addParam(key, params.get(key));
                }
                System.out.println(yoprequest.getParams());
                String uri = Config.getInstance().getValue("tradeOrderURI");
                YopResponse response = YopClient3.postRsa(uri, yoprequest);

                System.out.println(response.toString());
                if("FAILURE".equals(response.getState())){
                    if(response.getError() != null)
                        result.put("code",response.getError().getCode());
                        result.put("message",response.getError().getMessage());
                    return jsonObj.toString();
                }
                if (response.getStringResult() != null) {
                    result = PayConfig.parseResponse(response.getStringResult());
                    Map<String,String> params1 = new HashMap<String,String>();
                    params1.put("parentMerchantNo", parentMerchantNo);
                    params1.put("merchantNo", merchantNo);
                    params1.put("token", result.get("token"));
                    params1.put("timestamp", Util.CreateDate());
                    params1.put("directPayType", "");
                    params1.put("cardType", "");
                    params1.put("userNo", tpUsers.getUser_id() + "");
                    params1.put("userType", "USER_ID");
                    String ext = "{\"appId\":\""+""+"\",\"openId\":\""+""+"\",\"clientId\":\""+""+"\"}";
                    params1.put("ext", "");
                    String url = PayConfig.getUrl(params1);
                    System.out.println(url);
                    jsonObj.put("status", "1");
                    jsonObj.put("msg", "ok");
                    data.put("url",url);
                    data.put("type","jump");
                    jsonObj.put("result", data);
                    return jsonObj.toString();
                }
            }else if (pay_code.equals("alipayMobile")){
                //手机网站支付宝
                // 商户订单号，商户网站订单系统中唯一订单号，必填
                String out_trade_no = tpOrder.getOrder_sn();
                // 订单名称，必填
                String subject = "卡盟订单";
                System.out.println(subject);
                // 付款金额，必填
                String total_amount= tpOrder.getTotal_amount() + "";
                // 商品描述，可空
                String body = "卡盟订单商品";
                // 超时时间 可空
                String timeout_express="2m";
                // 销售产品码 必填
                String product_code="QUICK_WAP_WAY";
                /**********************/
                // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
                //调用RSA签名方式
                AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
                AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

                // 封装请求支付信息
                AlipayTradeWapPayModel alipayModel=new AlipayTradeWapPayModel();
                alipayModel.setOutTradeNo(out_trade_no);
                alipayModel.setSubject(subject);
                alipayModel.setTotalAmount(total_amount);
                alipayModel.setBody(body);
                alipayModel.setTimeoutExpress(timeout_express);
                alipayModel.setProductCode(product_code);
                alipay_request.setBizModel(alipayModel);
                // 设置异步通知地址
                alipay_request.setNotifyUrl(AlipayConfig.notify_url);
                // 设置同步地址
                alipay_request.setReturnUrl(AlipayConfig.return_url);

                // form表单生产
                String form = "";
                // 调用SDK生成表单
                try {
                    form = client.pageExecute(alipay_request).getBody();
                    data.put("url",form);
                    data.put("type","jump");
                    jsonObj.put("result", data);
                    return jsonObj.toString();
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObj.toString();
    }
}
