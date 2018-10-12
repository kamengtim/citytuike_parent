package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpChatFriendList;
import com.citytuike.model.TpChatLog;
import com.citytuike.model.TpUsers;
import com.citytuike.service.ChatService;
import com.citytuike.service.TpUsersService;
import io.swagger.annotations.Api;
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
@RequestMapping("api/Chat")
@Api(value = "好友消息")
public class ChatController extends BaseController{

    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private ChatService chatService;

    /**
     * @param im_id
     * @param page
     * @return
     * 历史记录
     */
    @RequestMapping(value="/history",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "历史记录", notes = "历史记录")
    public @ResponseBody String history(HttpServletRequest request,
                                        @RequestParam(required=true) String im_id,
                                        @RequestParam(required=true) Integer page) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpUsers tpUsers1 = tpUsersService.findOneByImId(im_id);
        if (null != tpUsers1){
            LimitPageList limitPageList = chatService.getLImitPageLIstByChatLogList(im_id, page);
            data.put("total", limitPageList.getPage().getTotalCount());
            data.put("per_page", limitPageList.getPage().getPageSize());
            data.put("current_page", limitPageList.getPage().getPageNow());
            data.put("last_page", limitPageList.getPage().getTotalPageCount());
            for (TpChatLog tpChatLog : (List<TpChatLog>)limitPageList.getList()) {
                JSONObject object = new JSONObject();
                object.put("id", tpChatLog.getId());
                object.put("from", tpChatLog.getFrom());
                object.put("to", tpChatLog.getTo());
                object.put("add_time", tpChatLog.getAddTime());
                object.put("room", tpChatLog.getRoom());
                object.put("msg_id", tpChatLog.getMsgId());
                object.put("is_read", tpChatLog.getIsRead());
                object.put("content",  JSONObject.parseObject(tpChatLog.getContent()));
                data1.add(object);
            }
            data.put("data", data1);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param page
     * @return
     * 聊天好友列表
     */
    @RequestMapping(value="/chat_list_v2",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "聊天好友列表", notes = "聊天好友列表")
    public @ResponseBody String chatListV2(HttpServletRequest request,
                                        @RequestParam(required=true) Integer page) {
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = chatService.getLimitPageListByChatList(tpUsers.getIm_id(), page);
        for (TpChatFriendList tpChatFriendList : (List<TpChatFriendList>)limitPageList.getList()) {
            JSONObject object = chatService.getChatFriendListJson(tpChatFriendList, tpUsers);
            data.add(object);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    //TODO 环信聊天token
    //TODO 加入\退出群
    //TODO 用户群列表
    //TODO 群聊天记录


}
