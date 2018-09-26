package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.ChatService;
import com.citytuike.service.FriendService;
import com.citytuike.service.TpUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Friend")
@Api(value = "好友")
public class FriendController {

    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private FriendService friendService;

    /**
     * @param model
     * @param token
     * @return
     * 好友申请
     */
    @RequestMapping(value="/apply",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "好友申请", notes = "好友申请")
    public @ResponseBody String apply(Model model, @RequestParam(required=true) String token,
                                        @RequestParam(required=true) Integer friend_uid,
                                        @RequestParam(required=true) String msg) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "申请失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        if (tpUsers.getUser_id().equals(friend_uid)){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "不能添加自己为好友");
            return jsonObj.toString();
        }
        TpUsers tpUsers1 = tpUsersService.findOneByUserId(friend_uid);
        if (tpUsers1 != null){
            TpUserFriendApply tpUserFriendApply = new TpUserFriendApply();
            tpUserFriendApply.setUserId(tpUsers.getUser_id());
            tpUserFriendApply.setFriendUserId(tpUsers1.getUser_id());
            tpUserFriendApply.setMsg(msg);
            tpUserFriendApply.setState(0);
            tpUserFriendApply.setCreatedAt(new Timestamp(new Date().getTime()));
            tpUserFriendApply.setUpdatedAt(new Timestamp(new Date().getTime()));
            int resultFriend = friendService.insertUserFriendsApply(tpUserFriendApply);
            if (resultFriend > 0){
                jsonObj.put("result", data);
                jsonObj.put("status", "1");
                jsonObj.put("msg", "申请成功");
            }

        }else {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "找不到该用户");
            return jsonObj.toString();
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/myHandleApplies",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "我的申请处理列表", notes = "我的申请处理列表")
    public @ResponseBody String myHandleApplies(Model model, @RequestParam(required=true) String token,
                                        @RequestParam(required=true) Integer p,
                                        @RequestParam(required=true) Integer size) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = friendService.getLimitListByApply(p, size, tpUsers.getUser_id());
        data.put("pages", limitPageList.getPage().getTotalPageCount());
        for (TpUserFriendApply tpUserFriendApply : (List<TpUserFriendApply>)limitPageList.getList()){
            JSONObject object = new JSONObject();
            object.put("id", tpUserFriendApply.getId());
            object.put("user_id", tpUserFriendApply.getUserId());
            object.put("msg", tpUserFriendApply.getMsg());
            object.put("time_str", tpUserFriendApply.getCreatedAt());
            TpUsers tpUsers1 = tpUsersService.findOneByUserId(tpUserFriendApply.getUserId());
            if (null != tpUsers1){
                object.put("nickname", tpUsers1.getNickname());
                object.put("head_pic", tpUsers1.getHead_pic());
                object.put("invite_code", tpUsers1.getInvite_code());
            }
            data1.add(object);
        }
        data.put("list", data1);
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    /**
     * @param model
     * @param token
     * @param id
     * @return
     * 通过好友申请
     */
    @RequestMapping(value="/accept",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "通过好友申请", notes = "通过好友申请")
    public @ResponseBody String accept(Model model, @RequestParam(required=true) String token,
                                      @RequestParam(required=true) Integer id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "操作失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpUserFriendApply tpUserFriendApply = friendService.findOneUserFriendApplyById(id);
        if (null != tpUserFriendApply){
            if (tpUserFriendApply.getState() == 0){
                TpUserFriendApply tpUserFriendApply1 = new TpUserFriendApply();
                tpUserFriendApply1.setId(tpUserFriendApply.getId());
                tpUserFriendApply1.setState(1);
                tpUserFriendApply1.setUpdatedAt(new Timestamp(new Date().getTime()));
                int updataResultApply = friendService.updataApplyFriends(tpUserFriendApply1);
                if (updataResultApply > 0){
                    TpUserFriends tpUserFriends = new TpUserFriends();
                    tpUserFriends.setUserId(tpUsers.getUser_id());
                    tpUserFriends.setFriendUserId(tpUserFriendApply.getUserId());
                    tpUserFriends.setInvalid(0);
                    tpUserFriends.setCreatedAt(new Timestamp(new Date().getTime()));
                    tpUserFriends.setUpdatedAt(new Timestamp(new Date().getTime()));
                    int insertFriendsResult = friendService.insertUserFriends(tpUserFriends);
                    if (insertFriendsResult > 0){
                        TpUserFriends tpUserFriends1 = new TpUserFriends();
                        tpUserFriends1.setUserId(tpUserFriendApply.getUserId());
                        tpUserFriends1.setFriendUserId(tpUsers.getUser_id());
                        tpUserFriends1.setInvalid(0);
                        tpUserFriends1.setCreatedAt(new Timestamp(new Date().getTime()));
                        tpUserFriends1.setUpdatedAt(new Timestamp(new Date().getTime()));
                        int insertFriendsResult1 = friendService.insertUserFriends(tpUserFriends1);
                        if (insertFriendsResult1 > 0){
                            jsonObj.put("result", data);
                            jsonObj.put("status", "1");
                            jsonObj.put("msg", "操作成功");
                        }
                    }
                }
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/refuse",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "拒绝好友申请", notes = "拒绝好友申请")
    public @ResponseBody String refuse(Model model, @RequestParam(required=true) String token,
                                      @RequestParam(required=true) Integer id) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "操作失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpUserFriendApply tpUserFriendApply = friendService.findOneUserFriendApplyById(id);
        if (null != tpUserFriendApply){
            if (tpUserFriendApply.getState() == 0){
                TpUserFriendApply tpUserFriendApply1 = new TpUserFriendApply();
                tpUserFriendApply1.setId(tpUserFriendApply.getId());
                tpUserFriendApply1.setState(-1);
                tpUserFriendApply1.setUpdatedAt(new Timestamp(new Date().getTime()));
                int updataResultApply = friendService.updataApplyFriends(tpUserFriendApply1);
                if (updataResultApply > 0){
                    jsonObj.put("result", data);
                    jsonObj.put("status", "1");
                    jsonObj.put("msg", "操作成功");
                }
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/myFriends",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "我的好友接口", notes = "我的好友接口")
    public @ResponseBody String myFriends(Model model, @RequestParam(required=true) String token,
                                                @RequestParam(required=false) String keyword,
                                                @RequestParam(required=true) Integer p,
                                                @RequestParam(required=true) Integer size) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray data1 = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = friendService.getLimitListByUserFriends(p, size, tpUsers.getUser_id(), keyword);
        data.put("pages", limitPageList.getPage().getTotalPageCount());
        for (TpUserFriends tpUserFriend : (List<TpUserFriends>)limitPageList.getList()) {
            JSONObject object = new JSONObject();
            object.put("user_id", tpUserFriend.getFriendUserId());
            TpUsers tpUsers1 = tpUsersService.findOneByUserId(tpUserFriend.getFriendUserId());
            if (null != tpUsers1){
                object.put("nickname", tpUsers1.getNickname());
                object.put("head_pic", tpUsers1.getHead_pic());
                object.put("im_id", tpUsers1.getIm_id());
                object.put("letter", "");
            }
            data1.add(object);
        }
        data.put("list", data1);
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }
}
