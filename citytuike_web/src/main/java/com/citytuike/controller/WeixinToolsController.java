package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.*;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
@Controller
@RequestMapping("api/Sns")
public class WeixinToolsController {
    @Autowired
    private TpUsersService tpUsersService;
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public @ResponseBody
    String validate(@RequestParam String signature, @RequestParam String timestamp,
                    @RequestParam String nonce, @RequestParam String echostr) {
        System.out.println(">>>signature>>>>" + signature);
        System.out.println(">>>timestamp>>>>" + timestamp);
        System.out.println(">>>nonce>>>>" + nonce);
        System.out.println(">>>echostr>>>>" + echostr);

        String[] params = { nonce, timestamp, "YQKEAmS2B5Eqx4MG01vvSNRzD9WkNQYE" };
        Arrays.sort(params);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
        }

        String sign = SHA1.encrypt(sb.toString());
        return signature.equals(sign) ? echostr : "failure";
    }

    /**
     * 微信事件
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public @ResponseBody String message(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String message = "success";
        try {
            Map<String, String> map = XmlUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String eventKey = map.get("EventKey");
            String ticket = map.get("Ticket");
            String eventType = map.get("Event");

            System.out.println("fromUserName>>"+fromUserName);
            System.out.println("toUserName>>"+toUserName);
            System.out.println("msgType>>"+msgType);
            System.out.println("event>>>>" + eventType);
            System.out.println("content>>"+content);
            System.out.println("eventKey>>>>" + eventKey);
            System.out.println("ticket>>>>" + ticket);

           /* MessageUtil messageUtil = new MessageUtil();
            if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){
                if(MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)){
                    message = messageUtil.subscribeForText(toUserName, fromUserName, setting, eventKey);
                }else if(MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)){
                    message = messageUtil.unsubscribe(toUserName, fromUserName, setting);
                } else if(MessageUtil.MESSAGE_SCAN.equals(eventType)){
                    message = messageUtil.scanForText(toUserName, fromUserName, setting, eventKey);
                }
            }
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                message = messageUtil.forText(toUserName, fromUserName, setting);
            }*/
        } catch (DocumentException e) {
            e.printStackTrace();
        }  finally{
            out.println(message);
            if(out!=null){
                out.close();
            }
        }
        return "";
    }
    @RequestMapping(value="/wx_login",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void wxLogin(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required=false) String invite_code,
                                        @RequestParam(required=true) String back_url) throws IOException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "登陆失败!");

        //已经授权用户直接通过。
        if(request.getSession().getAttribute(Constant.WEIXIN_USER)!=null) {
            System.out.println("同一公众号！！！《《《《《《《《《");
            String openid = (String) request.getSession().getAttribute(Constant.WEIXIN_USER);
            TpUsers user = tpUsersService.findOneByOpenId(openid);
            if(user != null){
                System.out.println("用户已授权！！");
            }
        }
        String code = request.getParameter("code");
        System.out.println("code is1 " + code);
        String state = request.getParameter("state");
        System.out.println("state is1 " + state);
        if (code != null && code.length() > 0) {
            // 获取用户openid
            try {
                WeixinAPI api = new WeixinAPI(Constant.WEIXIN_APPID, Constant.WEIXIN_APPSECRET);
                String openid  = api.getWebVisitorOpenIdFromBase(code, invite_code);
                System.out.println("code openid is " + openid);
                request.getSession().setAttribute(Constant.WEIXIN_USER, null);
                request.getSession().setAttribute(Constant.WEIXIN_USER, openid);
            } catch (WeixinApiException e) {
//						StringBuffer back = request.getRequestURL();
                request.getSession().setAttribute(Constant.WEIXIN_USER, null);
//						String backhome = "http://" + request.getServerName() + "/weixin/" +account + "/home.action";
//						System.out.println("code back is " + backhome);
                StringBuffer sb = new StringBuffer().append(WeixinAPI.getAuthorizeUrl(Constant.WEIXIN_APPID, back_url.toString().replaceAll("(&code=[^&]*)", "").replaceAll("(code=[^&]*&)", ""), request.getParameter("auth")==null?"snsapi_base":"snsapi_userinfo"));
                System.out.println("code url is  " + sb.toString());
                response.sendRedirect(sb.toString());
            }
        }else if(state==null){
            //获取weixinCode
//					StringBuffer back = request.getRequestURL();
            //http://www.bear1995.com/weixin/TNS6YHHo8TnmqS2tSkjwM4MaQmoKjd5J/home.action
            request.getSession().setAttribute(Constant.WEIXIN_USER, null);
//					String backhome = "http://" + request.getServerName() + "/weixin/" + account + "/home.action";
//					System.out.println("state back is " + backhome);
            StringBuffer sb = new StringBuffer().append(WeixinAPI.getAuthorizeUrl(Constant.WEIXIN_APPID, back_url.toString(), request.getParameter("auth")==null?"snsapi_base":"snsapi_userinfo"));
            System.out.println("state url is  " + sb.toString());
            response.sendRedirect(sb.toString());
        }
    }






}
