package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpFansNeed;

import java.util.List;

public interface TpFansNeedService {
    void save(TpFansNeed tpFansNeed);

    List<TpFansNeed> NeedFansList(Integer user_id);

    int getFanNum(Integer id, int status, Integer user_id);

    JSONObject getJson(TpFansNeed tpFansNeed);

    TpFansNeed getNeedByOrderId(Integer user_id, int order_id);
}
