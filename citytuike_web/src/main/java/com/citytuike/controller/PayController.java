package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.citytuike.configs.PayConfig;
import com.citytuike.constant.Constant;
import com.citytuike.model.*;
import com.citytuike.service.ITpAccountLogService;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.AlipayConfig;
import com.citytuike.util.Config;
import com.citytuike.util.Util;
import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
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
import java.util.*;

@Controller
@RequestMapping("api/Payment")
public class PayController {
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpOrderService tpOrderService;
    @Autowired
    private ITpAccountLogService iTpAccountLogService;




    @RequestMapping(value = "/gotopay", method = RequestMethod.GET)
    public void ddd(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        // 订单名称，必填
        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        System.out.println(subject);
        // 付款金额，必填
        String total_amount=new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        // 商品描述，可空
        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
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
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(value = "/pay_back", method = RequestMethod.GET)
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
            //////////////////////////////////////////////////////////////////////////////////////////

            //请在这里加上商户的业务逻辑程序代码
            TpOrder tpOrder = tpOrderService.findOrderByOrderSn(out_trade_no);
            if (null != tpOrder){
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                if (!seller_id.equals(AlipayConfig.UID)){
                    System.out.println("商户号不一致!!!");
                    return "fail";
                }
                if (!(tpOrder.getTotal_amount()+"").equals(total_fee)){
                    System.out.println("订单金额不一致!!!");
                    return "fail";
                }
                TpUsers tpUsers = tpUsersService.findOneByUserId(tpOrder.getUser_id());
                if (null != tpUsers){
                    //更新订单状态
                    TpOrder tpOrder1 = new TpOrder();
                    tpOrder1.setOrder_id(tpOrder.getOrder_id());
                    tpOrder1.setPay_code("alipayMobile");
                    tpOrder1.setPay_name("手机网站支付宝");
                    tpOrder1.setPay_time((int)new Date().getTime());
                    tpOrder1.setTransaction_id(trade_no);
                    tpOrder1.setPay_status(1);
                    int orderResult =tpOrderService.updateOrderByAlipay(tpOrder);
                    if(orderResult > 0){
                        //订单变化记录
                        TpOrderAction tpOrderAction = tpOrderService.getOrderAction(tpOrder, 1);
                        int actionResult1 =tpOrderService.insertOrderAction(tpOrderAction);
                        if (actionResult1 > 0){
                            int goodsnum = 0;
                            //升级和返佣金
                            List<TpOrderGoods> tpOrderGoodsList = tpOrderService.findAllGoodsByOrderId(tpOrder.getOrder_id());
                            List<Integer> goodsIds = new ArrayList<Integer>();
                            for (TpOrderGoods tpOrderGoods: tpOrderGoodsList) {
                                if (tpOrderGoods.getGoods_id() == 144){
                                    //统计机器数量,用作判断上面三级是否升级
                                    goodsnum += tpOrderGoods.getGoods_num();
                                }
                                if (!goodsIds.contains(tpOrderGoods.getGoods_id())){
                                    goodsIds.add(tpOrderGoods.getGoods_id());
                                }
                            }
                            Integer level = 0;
                            //存在机器时
                            if (goodsnum > 0){
                                //当前用户升级
                                if (tpUsers.getLevel() == 0){
                                    level = 20;
                                    int userResult = tpUsersService.updateUserLevel(tpOrder.getUser_id(), level);
                                    if (userResult > 0){
                                        //升级记录
                                        int uplogResult = insertUplogo(tpOrder, level);
                                        if (uplogResult <= 0){
                                            return "fail";
                                        }
                                    }
                                }
                                //上级是否升级
                                if(null != tpUsers.getParent_id() && "".equals(tpUsers.getParent_id())){
                                    TpUsers tpUsers1 = tpUsersService.findOneByUserId(tpUsers.getParent_id());
                                    if (null != tpUsers1){
                                        List<TpUsers> listUsers =  tpUsersService.findAllByUserParentId(tpUsers.getParent_id());
                                        int goodsNum = 0;
                                        for (TpUsers tpuser: listUsers ) {
                                            List<TpOrder> orderList = tpOrderService.findAllOrderByUserId(tpuser.getUser_id());
                                            for (TpOrder tporder: orderList) {
                                                List<TpOrderGoods> ordergoodsList  =  tpOrderService.findAllGoodsByOrderId(tporder.getOrder_id());
                                                for (TpOrderGoods tpOrderGoods: ordergoodsList) {
                                                    if (144 == tpOrderGoods.getOrder_id()){
                                                        goodsNum += tpOrderGoods.getGoods_num();
                                                    }
                                                }
                                            }
                                        }
                                        if ((goodsnum >= 45 && goodsnum < 150) && (listUsers.size() >= 15 && listUsers.size() < 50)){
                                            //总监
                                            level = 30;
                                            int userResult1 = tpUsersService.updateUserLevel(tpUsers1.getUser_id(), level);
                                            if (userResult1 > 0){
                                                //升级记录
                                                int uplogResult = insertUplogo(tpOrder, level);
                                                if (uplogResult <= 0){
                                                    return "fail";
                                                }
                                            }
                                        }else if(goodsnum >= 150 && listUsers.size() >= 50){
                                            //合伙人
                                            level = 40;
                                            int userResult1 = tpUsersService.updateUserLevel(tpUsers1.getUser_id(), level);
                                            if (userResult1 > 0){
                                                int uplogResult = insertUplogo(tpOrder, level);
                                                if (uplogResult <= 0){
                                                    return "fail";
                                                }
                                            }
                                        }
                                        //返佣金
                                        TpUsers tpUsers2 = tpUsersService.findOneByUserId(tpUsers1.getParent_id());
                                        if (null != tpUsers2){
                                            //感恩金
                                            double  frozenMoney = goodsnum*20 + tpUsers2.getFrozen_money();
                                            int moneyResult = tpUsersService.updateUserFrozenMoney(tpUsers2.getUser_id(), frozenMoney);
                                            if (moneyResult > 0){
                                                //金额变化记录
                                                TpAccountLog tpAccountLog = new TpAccountLog();
                                                tpAccountLog.setUser_id(tpUsers2.getUser_id());
                                                tpAccountLog.setChange_time(Integer.parseInt(Util.CreateDate()));
                                                tpAccountLog.setFrozen_money(frozenMoney);
                                                String desc = "您的好友 " + tpUsers.getMobile() + " 购买智媒体广告机（订单：" + tpOrder.getOrder_sn() + " ），您获得 20 元奖励";
                                                tpAccountLog.setDesc(desc);
                                                tpAccountLog.setOrder_id(tpOrder.getOrder_id());
                                                tpAccountLog.setOrder_sn(tpOrder.getOrder_sn());
                                                tpAccountLog.setUser_money(tpUsers2.getUser_money());
                                                tpAccountLog.setPay_points(0);
                                                tpAccountLog.setSecond_type(0);
                                                tpAccountLog.setThird_type(200);
                                                tpAccountLog.setChange_type(1);
                                                tpAccountLog.setStatus(0);
                                                tpAccountLog.setIs_delete(0);
                                                int accountlogResult = iTpAccountLogService.insertAccountLog(tpAccountLog);
                                                if (accountlogResult <= 0){
                                                    return "fail";
                                                }
                                            }
                                            //同根
                                            if (null != tpUsers2.getRelation() && !"".equals(tpUsers2.getRelation())){
                                                String[] users = tpUsers2.getRelation().split(",");
                                                double  frozenMoney1 = goodsnum*60/users.length;
                                                for (String userId : users) {
                                                    TpUsers tpUsers3 = tpUsersService.findOneByUserId(Integer.parseInt(userId));
                                                    if (null != tpUsers3){
                                                        int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers3.getUser_id(), frozenMoney1+tpUsers3.getFrozen_money());
                                                        if (moneyResult3 > 0){
                                                            int accountlogResult = insertAccountLog(tpUsers, tpUsers3, frozenMoney1, tpOrder);
                                                            if (accountlogResult <= 0){
                                                                return "fail";
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //级差
                                if (null != tpUsers.getRelation() && !"".equals(tpUsers.getRelation())){
                                    String[] userIds = tpUsers.getRelation().split(",");
                                    int levelNum = 0;
                                    for (String userid : userIds){
                                        TpUsers tpUsers4 = tpUsersService.findOneByUserId(Integer.parseInt(userid));
                                        switch(tpUsers4.getLevel()){
                                            case 20:
                                                if (levelNum == 0){
                                                    //先遇到经理分钱 278
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 278+tpUsers4.getFrozen_money());
                                                    if (moneyResult3 > 0){
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 278, tpOrder);
                                                        if (accountlogResult <= 0){
                                                            return "fail";
                                                        }
                                                    }
                                                    levelNum = 20;
                                                }else if (levelNum == 20){
                                                    //经理已经分过钱
                                                }
                                                break;
                                            case 30:
                                                if (levelNum == 0){
                                                    //先遇到总监分钱 278+80
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 278+80+tpUsers4.getFrozen_money());
                                                    if (moneyResult3 > 0){
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 278+80, tpOrder);
                                                        if (accountlogResult <= 0){
                                                            return "fail";
                                                        }
                                                    }
                                                    levelNum = 30;
                                                }else if (levelNum == 20){
                                                    //已有经理分钱 80
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 80+tpUsers4.getFrozen_money());
                                                    if (moneyResult3 > 0){
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 80, tpOrder);
                                                        if (accountlogResult <= 0){
                                                            return "fail";
                                                        }
                                                    }
                                                    levelNum = 30;
                                                }else if (levelNum == 30){
                                                    //总监已分钱
                                                }
                                                break;
                                            case 40:
                                                if (levelNum == 0){
                                                    //先遇到合伙人分钱 278+80+110
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 278+80+110+tpUsers4.getFrozen_money());
                                                    if (moneyResult3 > 0){
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 278+80+110, tpOrder);
                                                        if (accountlogResult <= 0){
                                                            return "fail";
                                                        }
                                                    }
                                                    levelNum = 40;
                                                }else if (levelNum == 20){
                                                    //已有经理分钱, 没有总监分钱 80+110
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 80+110+tpUsers4.getFrozen_money());
                                                    if (moneyResult3 > 0){
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 80+110, tpOrder);
                                                        if (accountlogResult <= 0){
                                                            return "fail";
                                                        }
                                                    }
                                                    levelNum = 40;
                                                }else if (levelNum == 30){
                                                    //总监已分钱 110
                                                    int moneyResult3 = tpUsersService.updateUserFrozenMoney(tpUsers4.getUser_id(), 110+tpUsers4.getFrozen_money());
                                                    if (moneyResult3 > 0){
                                                        int accountlogResult = insertAccountLog(tpUsers, tpUsers4, 110, tpOrder);
                                                        if (accountlogResult <= 0){
                                                            return "fail";
                                                        }
                                                    }
                                                    levelNum = 40;
                                                }else if (levelNum == 40){
                                                    //合伙人已分钱
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                            return "success";
                        }
                    }
                }

            }
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }

//            out.clear();
            return "fail";
       }else{//验证失败
            return "fail";
        }
    }
    public int insertUplogo(TpOrder tpOrder, int level){
        int result = 0;
        TpUserUpLog  tpUserUpLog = new TpUserUpLog();
        tpUserUpLog.setUser_id(tpOrder.getUser_id());
        tpUserUpLog.setLevel(level);
        tpUserUpLog.setAdd_time((int)new Date().getTime());
        int uplogResult = tpUsersService.insertUserUpLog(tpUserUpLog);
        return result;

    }
    public  int insertAccountLog(TpUsers tpUsers, TpUsers tpUsers3, double frozenMoney1, TpOrder tpOrder){
        //金额变化记录
        TpAccountLog tpAccountLog = new TpAccountLog();
        tpAccountLog.setUser_id(tpUsers3.getUser_id());
        tpAccountLog.setChange_time((int)new Date().getTime());
        tpAccountLog.setFrozen_money(frozenMoney1+tpUsers3.getFrozen_money());
        String desc = "您的好友 " + tpUsers.getMobile() + " 购买智媒体广告机（订单：" + tpOrder.getOrder_sn() + " ），您获得 "+frozenMoney1+" 元奖励";
        tpAccountLog.setDesc(desc);
        tpAccountLog.setOrder_id(tpOrder.getOrder_id());
        tpAccountLog.setOrder_sn(tpOrder.getOrder_sn());
        tpAccountLog.setUser_money(tpUsers3.getUser_money());
        tpAccountLog.setPay_points(0);
        tpAccountLog.setSecond_type(0);
        tpAccountLog.setThird_type(300);
        tpAccountLog.setChange_type(1);
        tpAccountLog.setStatus(0);
        tpAccountLog.setIs_delete(0);
        int accountlogResult = iTpAccountLogService.insertAccountLog(tpAccountLog);
        return accountlogResult;
    }
    @RequestMapping(value="/getCode",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String yopTaketoken(Model model, @RequestParam(required=true) String order_sn,
                                             @RequestParam(required=false) String openid,
                                             @RequestParam(required=true) String token,
                                        @RequestParam(required=true) String pay_code) throws IOException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
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
            }
        }
        return jsonObj.toString();
    }

}
