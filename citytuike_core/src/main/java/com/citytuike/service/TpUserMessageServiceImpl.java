package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpMessageMapper;
import com.citytuike.mapper.TpUserMessageMapper;
import com.citytuike.model.TpMessage;
import com.citytuike.model.TpUserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service@Transactional
public class TpUserMessageServiceImpl implements TpUserMessageService {
    @Autowired
    private TpUserMessageMapper tpUserMessageMapper;
    @Autowired
    private TpMessageMapper tpMessageMapper;
    public JSONObject selectMessage(Integer user_id) {
        List<TpUserMessage> tpUserMessages = tpUserMessageMapper.selectMessageByUser(user_id);
        JSONObject data = new JSONObject();
        for (TpUserMessage tpUserMessage : tpUserMessages) {
        data.put("category",tpUserMessage.getCategory());
        data.put("message_id",tpUserMessage.getMessage_id());
        data.put("status",tpUserMessage.getStatus());
        List<TpMessage>tpMessages = tpMessageMapper.selectMessageByMessageId(tpUserMessage.getMessage_id());
            for (TpMessage tpMessage : tpMessages) {
                data.put("admin_id",tpMessage.getAdmin_id());
                data.put("type",tpMessage.getType());
                data.put("send_time",tpMessage.getSend_time());
                data.put("data",tpMessage.getData());
                data.put("message",tpMessage.getMessage());
            }
        }
        return data;
    }

    public JSONObject NewselectMessage(Integer user_id, String cate) {
        List<TpUserMessage> tpUserMessages = tpUserMessageMapper.NewSelectMessageByUser(user_id,cate);
        JSONObject data = new JSONObject();
        for (TpUserMessage tpUserMessage : tpUserMessages) {
            data.put("category",tpUserMessage.getCategory());
            data.put("message_id",tpUserMessage.getMessage_id());
            data.put("status",tpUserMessage.getStatus());
            List<TpMessage>tpMessages = tpMessageMapper.NewSelectMessageByMessageId(tpUserMessage.getMessage_id(),tpUserMessage.getCategory());
            for (TpMessage tpMessage : tpMessages) {
                data.put("admin_id",tpMessage.getAdmin_id());
                data.put("type",tpMessage.getType());
                data.put("send_time",tpMessage.getSend_time());
                data.put("data",tpMessage.getData());
                data.put("message",tpMessage.getMessage());
            }
        }
        return data;
    }

    public JSONObject selectMessageDetail(Integer user_id, String rec_id) {
        List<TpUserMessage> tpUserMessages = tpUserMessageMapper.selectMessageByUserDetail(user_id,rec_id);
        JSONObject data = new JSONObject();
        for (TpUserMessage tpUserMessage : tpUserMessages) {
            data.put("category",tpUserMessage.getCategory());
            data.put("message_id",tpUserMessage.getMessage_id());
            data.put("status",tpUserMessage.getStatus());
            List<TpMessage>tpMessages = tpMessageMapper.selectMessageByMessageId(tpUserMessage.getMessage_id());
            for (TpMessage tpMessage : tpMessages) {
                data.put("admin_id",tpMessage.getAdmin_id());
                data.put("type",tpMessage.getType());
                data.put("send_time",tpMessage.getSend_time());
                data.put("data",tpMessage.getData());
                data.put("message",tpMessage.getMessage());
            }
        }
        return data;
    }
}
