package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.citytuike.constant.Constant;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.model.*;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.SHA1;
import com.citytuike.util.WeixinAPI;
import com.citytuike.util.XmlUtil;
import org.dom4j.DocumentException;
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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("api/Pay")
public class PayController {
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpOrderService tpOrderService;

    @RequestMapping(value = "/pay_back", method = RequestMethod.GET)
    public @ResponseBody String message(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException, AlipayApiException {
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

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
      //  boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

      //  if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            TpOrder tpOrder = tpOrderService.findOrderByOrderSn(out_trade_no);
            if (null != tpOrder){
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
                        //升级和返佣金
                        List<TpOrderGoods> tpOrderGoodsList = tpOrderService.findAllGoodsByOrderId(tpOrder.getOrder_id());
                        for (TpOrderGoods tpOrderGoods: tpOrderGoodsList) {
                            if (144 == tpOrderGoods.getGoods_id()){
                                //当前用户升级
                                //上面三级是否升级
                                //升级记录
                                TpUserUpLog  tpUserUpLog = new TpUserUpLog();
                                tpUserUpLog.setUser_id(tpOrder.getUser_id());
                                tpUserUpLog.setLevel(20);
                                tpUserUpLog.setAdd_time((int)new Date().getTime());
                                int uplogResult = tpUsersService.insertUserUpLog(tpUserUpLog);
                                //返佣金
                                //金额变化记录
                            }
                        }
                        return "success";
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
       /* }else{//验证失败
            return "fail";
        }*/
    }





}
