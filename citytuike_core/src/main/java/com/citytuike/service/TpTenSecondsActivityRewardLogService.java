package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpTenSecondsActivityReward;
import com.citytuike.model.TpTenSecondsActivityRewardLog;
import com.citytuike.model.TpUsers;
import com.github.pagehelper.PageInfo;


public interface TpTenSecondsActivityRewardLogService {
    int checkWeekShare(Integer user_id, Integer shareId);

    int getLogCount(Integer user_id, String activity_id);

    int checkDayShare(Integer user_id);

    int selectCount(String activity_id);

    int selectWeekCount(String activity_id, Integer user_id);

    JSONObject insertLog(String rewardGet, String activity_id, TpUsers share_id, TpUsers user_id, String second, TpTenSecondsActivityReward tpTenSecondsActivityReward, String nickName);

    PageInfo reward_list();

    JSONObject getJson(TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog);

    TpTenSecondsActivityRewardLog getLogById(String reward_id,Integer user_id);

    int update(TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog);

    TpTenSecondsActivityRewardLog getReward(String log_id);
}
