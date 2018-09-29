package com.citytuike.mapper;

import com.citytuike.model.TpTenSecondsActivityRewardLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpTenSecondsActivityRewardLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpTenSecondsActivityRewardLog record);

    TpTenSecondsActivityRewardLog selectByPrimaryKey(Integer id);

    List<TpTenSecondsActivityRewardLog> selectAll();

    int updateByPrimaryKey(TpTenSecondsActivityRewardLog record);

    int checkWeekShare(@Param("user_id") Integer user_id, @Param("shareId") Integer shareId);

    int getLogCount(@Param("user_id") Integer user_id, @Param("activity_id") String activity_id);

    int checkDayShare(Integer user_id);

    int selectCount(@Param("activity_id") String activity_id);

    int selectWeekCount(@Param("activity_id") String activity_id, @Param("user_id") Integer user_id);

    List fansTypeList();

    TpTenSecondsActivityRewardLog getLogById(@Param("reward_id") String reward_id, @Param("user_id") Integer user_id);

    int update(@Param("Mobile") String Mobile, @Param("address") String address, @Param("province") String province, @Param("city") String city, @Param("district") String district, @Param("twon") String twon, @Param("id") Integer id);

    TpTenSecondsActivityRewardLog getReward(String log_id);
}