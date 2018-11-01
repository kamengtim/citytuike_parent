package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.ChatService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.MD5Utils;
import com.citytuike.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
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
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpUsers tpUsers1 = tpUsersService.findOneByImId(im_id);
        if (null != tpUsers1){
            String room = "";
            String im_ids = tpUsers.getIm_id();
            String friend_im_id = im_id;
            int im = Integer.parseInt(im_ids.substring(1));
            int friend_im = Integer.parseInt(friend_im_id.substring(1));
            if (im > friend_im){
                room = MD5Utils.md5(im_ids + friend_im_id);
            }else{
                room = MD5Utils.md5(friend_im_id + im_ids);
            }
                LimitPageList limitPageList = chatService.getLImitPageLIstByChatLogList(room, page);
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
                    if(tpUsers.getIm_id().equals(tpChatLog.getFrom())){
                        object.put("nickname", tpUsers.getNickname());
                        object.put("head_pic", tpUsers.getHead_pic());
                    }else if (tpUsers.getIm_id().equals(tpChatLog.getTo())){
                        object.put("nickname", tpUsers1.getNickname());
                        object.put("head_pic", tpUsers1.getHead_pic());
                    }
                    object.put("content",  JSONObject.parseObject(tpChatLog.getContent()));
                    data1.add(object);
                }
            data.put("data", data1);
           //更新好友列表已读,如果自己是接收方，更新为已读，否则不更新
            chatService.updataChatFriendListIsRead(room, im_ids);

        }

        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param page
     * @param type 0：全部，1：未读2：已读
     * @return
     * 聊天好友列表
     */
    @RequestMapping(value="/chat_list_v2",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "聊天好友列表", notes = "聊天好友列表")
    public @ResponseBody String chatListV2(HttpServletRequest request,
                                           @RequestParam(required=true) Integer page,
                                           @RequestParam(required=false) Integer type) {
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = chatService.getLimitPageListByChatList(tpUsers.getIm_id(),type, page);
        for (TpChatFriendList tpChatFriendList : (List<TpChatFriendList>)limitPageList.getList()) {
            JSONObject object = chatService.getChatFriendListJson(tpChatFriendList, tpUsers);
            data.add(object);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    @RequestMapping(value="/join_group",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "加入\\退出群", notes = "加入\\退出群")
    public @ResponseBody String joinGroup(HttpServletRequest request,
                                           @RequestParam(required=true) String user_id,
                                           @RequestParam(required=true) String group_id,
                                           @RequestParam(required=false) String is_join) {
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpUsers tpUsers1 = tpUsersService.findOneByUserId(Integer.parseInt(user_id));
        if (null == tpUsers1){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "用户不存在");
            return jsonObj.toString();
        }
        if (is_join.equals("1")){
            TpChatGroupUser tpChatGroupUser = new TpChatGroupUser();
            tpChatGroupUser.setGroupId(group_id);
            tpChatGroupUser.setUserId(Integer.parseInt(user_id));
            tpChatGroupUser.setAddTime(new Timestamp(new Date().getTime()));
            tpChatGroupUser.setStatus(1);
            int insertGoup = chatService.insertChatGroupUser(tpChatGroupUser);
            if (insertGoup > 0){
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
                return jsonObj.toString();
            }
        }else if (is_join.equals("0")){
            int deleteGroup = chatService.updateChatGroupUserByStatus(group_id, user_id);
            if (deleteGroup > 0){
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
                return jsonObj.toString();
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/group_history",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "群聊天记录", notes = "群聊天记录")
    public @ResponseBody String groupHistory(HttpServletRequest request,
                                           @RequestParam(required=true) String group_id) {
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }

        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    //TODO 环信聊天token
    //TODO 用户群列表
    //TODO redis


}
