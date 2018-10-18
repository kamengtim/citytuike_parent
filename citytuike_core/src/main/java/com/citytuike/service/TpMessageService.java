package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpMessage;

public interface TpMessageService {
    LimitPageList getLimitPageList(Integer user_id, String page, String cate);

    JSONObject getJson(TpMessage tpMessage,Integer user_id);

    void save(JSONObject jsonObj);

    void insert(TpMessage tpMessage);


    TpMessage getNewMessage(int i);
}
