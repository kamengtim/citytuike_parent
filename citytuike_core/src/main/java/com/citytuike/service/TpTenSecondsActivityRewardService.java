package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpTenSecondsActivityReward;

import java.util.List;

public interface TpTenSecondsActivityRewardService {
    List<TpTenSecondsActivityReward> getReward(String activity_id);

    JSONObject getJson(TpTenSecondsActivityReward tpTenSecondsActivityReward);

    TpTenSecondsActivityReward getRewardBySecond(String second, String activity_id);
}
