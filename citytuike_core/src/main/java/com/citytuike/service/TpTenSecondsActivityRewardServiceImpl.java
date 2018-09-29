package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpTenSecondsActivityRewardMapper;
import com.citytuike.model.TpTenSecondsActivityReward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TpTenSecondsActivityRewardServiceImpl implements TpTenSecondsActivityRewardService {
    @Autowired
    private TpTenSecondsActivityRewardMapper tpTenSecondsActivityRewardMapper;
    @Override
    public List<TpTenSecondsActivityReward> getReward(String activity_id) {
        List<TpTenSecondsActivityReward> tpTenSecondsActivityReward = tpTenSecondsActivityRewardMapper.getReward(activity_id);
        return tpTenSecondsActivityReward;
    }

    @Override
    public JSONObject getJson(TpTenSecondsActivityReward tpTenSecondsActivityReward) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpTenSecondsActivityReward.getId());
        jsonObject.put("name",tpTenSecondsActivityReward.getName());
        jsonObject.put("alias",tpTenSecondsActivityReward.getAlias());
        jsonObject.put("reward",tpTenSecondsActivityReward.getReward());
        jsonObject.put("condition",tpTenSecondsActivityReward.getCondition());
        jsonObject.put("level",tpTenSecondsActivityReward.getLevel());

        return jsonObject;
    }

    @Override
    public TpTenSecondsActivityReward getRewardBySecond(String second, String activity_id) {
        TpTenSecondsActivityReward tpTenSecondsActivityReward = tpTenSecondsActivityRewardMapper.getRewardBySecond(second,activity_id);
        return tpTenSecondsActivityReward;
    }

    @Override
    public TpTenSecondsActivityReward getRewardById(Integer reward_id) {
        TpTenSecondsActivityReward tpTenSecondsActivityReward = tpTenSecondsActivityRewardMapper.getRewardById(reward_id);
        return tpTenSecondsActivityReward;
    }
}
