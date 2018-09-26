package com.citytuike.service;

public interface TpTenSecondsActivityRewardLogService {
    int checkWeekShare(Integer user_id, Integer shareId);

    int getLogCount(Integer user_id, String activity_id);
}
