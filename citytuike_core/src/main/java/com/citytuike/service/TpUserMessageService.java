package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUserMessage;

public interface TpUserMessageService {
    JSONObject selectMessage(Integer user_id);

    JSONObject NewselectMessage(Integer user_id, String cate);

    JSONObject selectMessageDetail(Integer user_id, String rec_id);

    void setMessageReadByCat(Integer user_id, String cate, Integer message_id);

    int insert(TpUserMessage tpUserMessage);
}
