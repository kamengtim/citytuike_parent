package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.model.TpUserFeedback;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpUserFeedbackService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Type;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
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
public class FeedbackController extends BaseController{
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpUserFeedbackService tpUserFeedbackService;
    /**
     * @return 统一文章列表
     */
    @RequestMapping(value = "add_feedback", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "统一文章列表", notes = "统一文章列表")
    public @ResponseBody String add_feedback(HttpServletRequest request,
                        @RequestParam(required = true) String content,
                        @RequestParam(required = true) String type){
        JSONObject jsonObj = new JSONObject();
        if(content == null || content.equals("") || type == null || type.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
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
            jsonObj.put("status", 1);
            jsonObj.put("msg", "成功");
            return jsonObj.toString();
    }
    /**
     * @return 意见反馈列表接口
     */
    @RequestMapping(value = "index", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "意见反馈列表接口", notes = "意见反馈列表接口")
    public @ResponseBody String index(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject =new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
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
