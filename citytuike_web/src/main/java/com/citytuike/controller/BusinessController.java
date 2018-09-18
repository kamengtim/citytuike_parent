package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/Business")
public class BusinessController {
    @Autowired
    private TpUsersService tpUsersService;

    @RequestMapping(value="/Upload/qiniu_token",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String qiniuToken(Model model, @RequestParam(required=true) String token,
                      @RequestParam(required=true) String domain) {
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
