package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpMessage;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpMessageService;
import com.citytuike.service.TpUserMessageService;
import com.citytuike.service.TpUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("api/Message")
public class MessageController {
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpMessageService tpMessageService;
    @Autowired
    private TpUserMessageService tpUserMessageService;
    /**
     * @return
     * 消息首页
     */
    @RequestMapping(value = "index",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String Index(@RequestParam(required = true) String token) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject data = tpUserMessageService.selectMessage(tpUsers.getUser_id());

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 消息列表
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET,produces =  "text/html;charset=UTF-8")
    public @ResponseBody String getList(@RequestParam(required = true) String token,
                                        @RequestParam(required = true) String cate,
                                        @RequestParam(required = true) String page){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = tpMessageService.getLimitPageList(tpUsers.getUser_id(),page,cate);
        data.put("current_page", limitPageList.getPage().getPageNow());
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageSize());
        data.put("last_page",limitPageList.getPage().getTotalPageCount());
        List<TpMessage>tpMessages = (List<TpMessage>) limitPageList.getList();
        for (TpMessage tpMessage : tpMessages) {
            JSONObject jsonObject = tpMessageService.getJson(tpMessage,tpUsers.getUser_id());
            data.put("data",jsonObject);
        tpUserMessageService.setMessageReadByCat(tpUsers.getUser_id(),cate,tpMessage.getMessage_id());
        }
        //消息设置为已读
       // JSONObject message = tpUserMessageService.NewselectMessage(tpUsers.getUser_id(),cate);
        jsonObj.put("result",data);
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 消息详情
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public @ResponseBody String Detail(@RequestParam(required = true)String token,
                                       @RequestParam(required = true,defaultValue = "13")String rec_id){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        JSONObject data = tpUserMessageService.selectMessageDetail(tpUsers.getUser_id(),rec_id);
        jsonObj.put("return",data);
        jsonObj.put("status","1");
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
}
