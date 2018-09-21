package com.citytuike.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("api/Index")
public class IndexController {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @return ios APP 设置审核版本
     */
    @RequestMapping(value = "setIosVersionDo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String setIosVersionDo(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String version = request.getParameter("version");
        jsonObject.put("app_version",version);
        jsonObject.toString();
        redisTemplate.opsForValue().set("ios",jsonObject.toString());
        return jsonObject.toString();
    }
    /**
     * @return ios 获取审核版本
     */
    @RequestMapping(value = "getIosVersion", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getIosVersion(HttpServletRequest request){
        String vesion = (String) redisTemplate.opsForValue().get("ios");
        return vesion;
    }
}
