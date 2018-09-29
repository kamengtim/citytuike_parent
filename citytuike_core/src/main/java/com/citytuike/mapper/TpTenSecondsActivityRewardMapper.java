package com.citytuike.mapper;

import com.citytuike.model.TpTenSecondsActivityReward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpTenSecondsActivityRewardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpTenSecondsActivityReward record);

    TpTenSecondsActivityReward selectByPrimaryKey(Integer id);

    List<TpTenSecondsActivityReward> selectAll();

    int updateByPrimaryKey(Integer id);

    List<TpTenSecondsActivityReward> getReward(String activity_id);

    TpTenSecondsActivityReward getRewardBySecond(@Param("second") String second, @Param("activity_id") String activity_id);

    int update(Integer id);
}