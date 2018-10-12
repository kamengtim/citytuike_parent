package com.citytuike.controller;


import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUsers;
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
public class IndexController extends BaseController{
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @return ios APP 设置审核版本
     */
    @RequestMapping(value = "setIosVersionDo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "设置审核版本", notes = "设置审核版本")
    public @ResponseBody String setIosVersionDo(HttpServletRequest request,
                                                @RequestParam(required = true)String version){
        JSONObject jsonObj = new JSONObject();
        if(version == null || version.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("app_version",version);
        jsonObject.toString();
        redisTemplate.opsForValue().set("ios",jsonObject.toString());
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功");
        jsonObj.put("result",jsonObject);
        return jsonObj.toString();
    }
    /**
     * @return ios 获取审核版本
     */
    @RequestMapping(value = "getIosVersion", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取审核版本", notes = "获取审核版本")
    public @ResponseBody String getIosVersion(HttpServletRequest request){
        String vesion = (String) redisTemplate.opsForValue().get("ios");
        return vesion;
    }
}
