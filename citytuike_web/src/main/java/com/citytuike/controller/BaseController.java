package com.citytuike.controller;

import com.citytuike.interceptor.RedisConstant;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpUsersService tpUsersService;

    public TpUsers initUser(HttpServletRequest request){
        String header = request.getHeader("P-Token");
        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = null;
        if(token != null && !"".equals(token)){
            tpUsers = tpUsersService.getToken(token);
        }
        return tpUsers;
    }



}
