package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BaseController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpUsersService tpUsersService;

    public TpUsers initUser(HttpServletRequest request){
        String header = request.getHeader("P-Token");
//        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = null;
        if(header != null && !"".equals(header)){
            tpUsers = tpUsersService.getToken(header);
        }
        return tpUsers;
    }
    public JSONObject getRequestJson(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try {
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
}
