package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.interceptor.RedisConstant;
import com.citytuike.model.TpArticle;
import com.citytuike.model.TpUserFeedback;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUserFeedbackService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Type;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Feedback")
public class FeedbackController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpUserFeedbackService tpUserFeedbackService;
    @Autowired
    private TpUsersService tpUsersService;
    /**
     * @return 统一文章列表
     */
    @RequestMapping(value = "add_feedback", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String add_feedback(HttpServletRequest request,
                        @RequestParam(required = true) String content,
                        @RequestParam(required = true) String type){
        JSONObject jsonObj = new JSONObject();
        String header = request.getHeader("p-token");
        if(header == null || header.trim().length() == 0){
            throw new RuntimeException("登录异常");
        }

        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = tpUsersService.getToken(token);
        if(tpUsers == null){
            throw new RuntimeException("登录超时");
        }
        TpUserFeedback tpUserFeedback = new TpUserFeedback();
        if(type.equals("image")){
            tpUserFeedback.setType(Type.image);
        }else if(type.equals("text")){
            tpUserFeedback.setType(Type.text);
        }else{
            tpUserFeedback.setType(Type.sound);
        }
            tpUserFeedback.setContent(content);
            tpUserFeedback.setUser_id(tpUsers.getUser_id());
            tpUserFeedback.setCs_id(1);
            tpUserFeedback.setUser_send(Byte.valueOf("1"));
            tpUserFeedback.setStatus(Byte.valueOf("1"));
            tpUserFeedback.setIs_delete(Byte.valueOf("0"));
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            tpUserFeedback.setSend_date(new Date());
            tpUserFeedback.setSend_time((int)(new Date().getTime()/1000));
            tpUserFeedbackService.insert(tpUserFeedback);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "成功");
            return jsonObj.toString();
    }
    /**
     * @return 意见反馈列表接口
     */
    @RequestMapping(value = "index", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String index(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject =new JSONObject();
        String header = request.getHeader("p-token");
        if(header == null || header.trim().length() == 0){
            throw new RuntimeException("登录异常");
        }

        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = tpUsersService.getToken(token);
        if(tpUsers == null){
            throw new RuntimeException("登录超时");
        }
        PageInfo pageInfo = tpUserFeedbackService.query(tpUsers.getUser_id());
        List<TpUserFeedback> tpUserFeedbacks = pageInfo.getList();
        for (TpUserFeedback tpUserFeedback : tpUserFeedbacks) {
            jsonObject = tpUserFeedbackService.getJson(tpUserFeedback);
        }
        jsonObj.put("",jsonObject);
        return jsonObj.toString();
    }

}
