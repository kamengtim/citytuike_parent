package com.citytuike.service;

import com.citytuike.mapper.TpTenSecondsActivityRewardLogMapper;
import com.citytuike.model.TpTenSecondsActivityRewardLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpTenSecondsActivityRewardLogServiceImpl implements TpTenSecondsActivityRewardLogService {
    @Autowired
    private TpTenSecondsActivityRewardLogMapper tpTenSecondsActivityRewardLogMapper;
    @Override
    public int checkWeekShare(Integer user_id, Integer shareId) {
        int count = tpTenSecondsActivityRewardLogMapper.checkWeekShare(user_id,shareId);
        return count;
    }

    @Override
    public int getLogCount(Integer user_id, String activity_id) {
        int count = tpTenSecondsActivityRewardLogMapper.getLogCount(user_id,activity_id);
        return count;
    }
}
