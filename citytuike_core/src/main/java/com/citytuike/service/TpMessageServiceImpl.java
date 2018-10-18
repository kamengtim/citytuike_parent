package com.citytuike.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpMessageMapper;
import com.citytuike.mapper.TpUserMessageMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpMessage;
import com.citytuike.model.TpUserMessage;
import com.sun.org.apache.bcel.internal.generic.NEW;
import de.ailis.pherialize.Pherialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service@Transactional
public class TpMessageServiceImpl implements TpMessageService {
    @Autowired
    private TpMessageMapper tpMessageMapper;
    @Autowired
    private TpUserMessageMapper tpUserMessageMapper;
    public LimitPageList getLimitPageList(Integer user_id, String page, String cate) {
        List<TpUserMessage> tpUserMessages = tpUserMessageMapper.selectMessageByUser(user_id);
         LimitPageList limitPageList = new LimitPageList();
        for (TpUserMessage tpUserMessage : tpUserMessages) {
            List<TpMessage> tpMessages = tpMessageMapper.selectMessageByMessageId(tpUserMessage.getMessage_id());
            for (TpMessage tpMessage : tpMessages) {
                int totalCount = tpMessageMapper.selectCount(tpMessage.getMessage_id());
                List<TpMessage> stuList = new ArrayList<TpMessage>();
                Page PageSize = null;
                if(page != null){
                    PageSize=new Page(totalCount,Integer.valueOf(page));
                    PageSize.setPageSize(10);
                    stuList = tpMessageMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),tpMessage.getMessage_id(),cate,user_id);
                }else{
                    PageSize = new Page(totalCount,1);
                    PageSize.setPageSize(10);
                    stuList = tpMessageMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),tpMessage.getMessage_id(),cate,user_id);
                }
                limitPageList.setPage(PageSize);
                limitPageList.setList(stuList);
            }
        }
                return limitPageList;
    }

    @Override
    public JSONObject getJson(TpMessage tpMessage,Integer user_id) {
        JSONObject jsonObject = new JSONObject();
        List<TpUserMessage>tpUserMessages = tpUserMessageMapper.selectStutsa(tpMessage.getMessage_id(),tpMessage.getCategory());
        for (TpUserMessage tpUserMessage : tpUserMessages) {
        jsonObject.put("category",tpMessage.getCategory());
        jsonObject.put("message_id",tpMessage.getMessage_id());
        jsonObject.put("send_time",tpMessage.getSend_time());
        jsonObject.put("type",tpMessage.getType());
        jsonObject.put("data",tpMessage.getData());
        jsonObject.put("status",tpUserMessage.getStatus());
        }
        return jsonObject;
    }

    @Override
    public void save(JSONObject jsonObj) {
        TpMessage tpMessage = new TpMessage();
        tpMessage.setAdmin_id(Short.valueOf("0"));
        JSONObject table = (JSONObject) jsonObj.get("table");
        String category = table.getString("category");
        tpMessage.setCategory(Integer.valueOf(category));
        tpMessage.setType(false);
        JSONObject msg = (JSONObject) jsonObj.get("msg");
        tpMessage.setMessage(msg.get("discription").toString());
        tpMessage.setSend_time((int)(new Date().getTime()/1000));
        String serialize = Pherialize.serialize(msg);
        tpMessage.setData(serialize);
        tpMessage.setSend_status(Byte.valueOf("0"));
        tpMessage.setError_msg("0");
        tpMessage.setError_num(0);
        tpMessage.setCreate_time((int)(new Date().getTime()/1000));
        tpMessageMapper.save(tpMessage);
    }

    @Override
    public void insert(TpMessage tpMessage) {
        tpMessageMapper.save(tpMessage);
    }

    @Override
    public TpMessage getNewMessage(int i) {
        TpMessage tpMessage = tpMessageMapper.getNewMessage(i);
        return tpMessage;
    }
}
