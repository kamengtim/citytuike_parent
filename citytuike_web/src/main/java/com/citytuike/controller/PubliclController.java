package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.configs.UtilConfig;
import com.citytuike.constant.Constant;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.model.TpAdTopUp;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Base64Img;
import com.citytuike.util.Util;
import com.citytuike.util.WeixinAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("api")
public class PubliclController {

    @Autowired
    private TpUsersService tpUsersService;

    /**
     * @param model
     * @param token
     * @return
     * @throws WeixinApiException
     */
    @RequestMapping(value="/Wechat/share",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody  String share(Model model, @RequestParam(required=true) String token) throws WeixinApiException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "登陆失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        WeixinAPI api = new WeixinAPI(Constant.WEIXIN_APPID, Constant.WEIXIN_APPSECRET);
        Map<String, String> map = api.getWXConfig();
        data.put("appId", map.get("appId"));
        data.put("nonceStr", map.get("nonceStr"));
        data.put("timestamp", map.get("timestamp"));
        data.put("url", map.get("url"));
        data.put("signature", map.get("signature"));
        data.put("rawString", map.get("jsapi_ticket"));

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");

        return jsonObj.toString();
    }
    @RequestMapping(value="/Index/make_qrcode",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody  String make_qrcode(HttpServletRequest request, Model model, @RequestParam(required=false) String token,
                                             @RequestParam(required=true) String content) throws WeixinApiException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "登陆失败!");
        /*TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }*/
        String name = UtilConfig.getQrcode(content);
        String url = "http://localhost/upload/1.jpg";
//        String url = "/upload/qrcode/" + name + ".jpg";
        String base64url = Base64Img.GetImageStrFromUrl(url);
        jsonObj.put("result", base64url);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");

        return jsonObj.toString();
    }
    @RequestMapping(value="/index/business_license_recognition",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody  String business_license_recognition(Model model, @RequestParam(required=true) String token,
                                             @RequestParam(required=true) String pic) throws WeixinApiException {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "登陆失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");

        return jsonObj.toString();
    }
}
