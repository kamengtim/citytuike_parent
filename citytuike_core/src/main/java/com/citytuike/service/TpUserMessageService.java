package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;

public interface TpUserMessageService {
    JSONObject selectMessage(Integer user_id);

    JSONObject NewselectMessage(Integer user_id, String cate);

    JSONObject selectMessageDetail(Integer user_id, String rec_id);
}
