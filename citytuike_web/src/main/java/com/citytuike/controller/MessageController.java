package com.citytuike.controller;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpMessage;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpMessageService;
import com.citytuike.service.TpUserMessageService;
import com.citytuike.service.TpUsersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("api/Message")
public class MessageController extends BaseController{
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
    @RequestMapping(value = "index",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "消息首页", notes = "消息首页")
    public @ResponseBody String Index(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject data = tpUserMessageService.selectMessage(tpUsers.getUser_id());

        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 消息列表
     */
    @RequestMapping(value = "getList",method = RequestMethod.POST,produces =  "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "消息列表", notes = "消息列表")
    public @ResponseBody String getList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String cate = jsonRequest.getString("cate");
        String page = jsonRequest.getString("page");
        if(cate == null || cate.equals("") || page == null || page.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
    /**
     * @return
     * 消息详情
     */
    @RequestMapping(value = "detail",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "消息详情", notes = "消息详情")
    public @ResponseBody String Detail(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String rec_id = jsonRequest.getString("rec_id");
        if(rec_id == null || rec_id.equals("")){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误!");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject data = tpUserMessageService.selectMessageDetail(tpUsers.getUser_id(),rec_id);
        jsonObj.put("return",data);
        jsonObj.put("status", 1);
        jsonObj.put("msg","请求成功");
        return jsonObj.toString();
    }
}
