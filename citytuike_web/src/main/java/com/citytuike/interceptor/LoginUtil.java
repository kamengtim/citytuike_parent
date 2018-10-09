package com.citytuike.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;

public class LoginUtil {
    public static JSONObject jsonObj = new JSONObject();
    public static TpUsers login(HttpServletRequest request,RedisTemplate redisTemplate,TpUsersService tpUsersService){
        String header = request.getHeader("p-token");
        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        if(token == null){
            token = "";
        }
        TpUsers tpUsers = tpUsersService.getToken(token);
        return tpUsers;
    }
}