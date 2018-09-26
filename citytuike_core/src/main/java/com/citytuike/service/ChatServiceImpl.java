package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpChatFriendListMapper;
import com.citytuike.mapper.TpChatLogMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private TpChatFriendListMapper tpChatFriendListMapper;
    @Autowired
    private TpChatLogMapper tpChatLogMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Override
    public LimitPageList getLimitPageListByChatList(String im_id, Integer p) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpChatFriendListMapper.getCount(im_id);//获取总的记录数
        List<TpChatFriendList> stuList=new ArrayList<TpChatFriendList>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(10);
            stuList=tpChatFriendListMapper.selectByPage(im_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(10);
            stuList=tpChatFriendListMapper.selectByPage(im_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public JSONObject getChatFriendListJson(TpChatFriendList tpChatFriendList, TpUsers tpUsers) {
        JSONObject object = new JSONObject();
        object.put("id", tpChatFriendList.getId());
        object.put("room_id", tpChatFriendList.getRoomId());
        object.put("from", tpChatFriendList.getFrom());
        object.put("to", tpChatFriendList.getTo());
        object.put("message", tpChatFriendList.getMessage());
        object.put("update_time", tpChatFriendList.getUpdateTime());
        object.put("is_read", tpChatFriendList.getIsRead());
        TpUsers tpUsers1 = null;
        if (!tpUsers.getIm_id().equals(tpChatFriendList.getFrom())){
            tpUsers1 = tpUsersMapper.findOneByImId(tpChatFriendList.getFrom());
            if (null != tpUsers1){
                object.put("im_id", tpUsers1.getIm_id());
                object.put("add_time", tpUsers1.getReg_time());
                object.put("user_id", tpUsers1.getUser_id());
                object.put("nickname", tpUsers1.getNickname());
                object.put("head_pic", tpUsers1.getHead_pic());
                object.put("content", "");
            }
        }else if (!tpUsers.getIm_id().equals(tpChatFriendList.getTo())){
            tpUsers1 = tpUsersMapper.findOneByImId(tpChatFriendList.getTo());
            if (null != tpUsers1){
                object.put("im_id", tpUsers1.getIm_id());
                object.put("add_time", tpUsers1.getReg_time());
                object.put("user_id", tpUsers1.getUser_id());
                object.put("nickname", tpUsers1.getNickname());
                object.put("head_pic", tpUsers1.getHead_pic());
                object.put("content", "");
            }
        }
        return object;
    }

    @Override
    public LimitPageList getLImitPageLIstByChatLogList(String im_id, Integer p) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpChatLogMapper.getCount(im_id);//获取总的记录数
        List<TpChatLog> stuList=new ArrayList<TpChatLog>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(10);
            stuList=tpChatLogMapper.selectByPage(im_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(10);
            stuList=tpChatLogMapper.selectByPage(im_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }
}
