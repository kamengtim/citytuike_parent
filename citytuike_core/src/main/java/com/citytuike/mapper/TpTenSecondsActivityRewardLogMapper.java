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
}