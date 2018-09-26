package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpCartGift;
import com.citytuike.model.TpUsers;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.Map;

public interface TpCartGiftService {
    int getCount(Integer user_id);

    PageInfo query(int count, Integer user_id);

    JSONObject getJson(TpUsers tpUsers, TpCartGift tpCartGift, Integer level, String nickname);

    TpCartGift getCartGift(int id, Integer user_id);

    Map<String, Object> refill(String mobile, BigDecimal money);

    int update(int id, Integer user_id, String mobile, String taskid);

    TpCartGift getCartGiftByUserId(Integer user_id);

    int getGifts(Integer user_id, int id, int gift_type, String $gift_name, int date);

    PageInfo queryList(Integer user_id);

    JSONObject getJsonCartGift(TpCartGift tpCartGift);

    TpCartGift useGifts(Integer user_id, int id);
}
