package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpCartGift;
import com.citytuike.model.TpUsers;
import com.github.pagehelper.PageInfo;

public interface TpCartGiftService {
    int getCount(Integer user_id);

    PageInfo query(int count, Integer user_id);

    JSONObject getJson(TpUsers tpUsers, TpCartGift tpCartGift, Integer level, String nickname);
}
