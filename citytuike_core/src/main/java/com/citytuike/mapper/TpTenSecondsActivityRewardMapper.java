package com.citytuike.mapper;

import com.citytuike.model.TpTenSecondsActivityReward;
import java.util.List;

public interface TpTenSecondsActivityRewardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpTenSecondsActivityReward record);

    TpTenSecondsActivityReward selectByPrimaryKey(Integer id);

    List<TpTenSecondsActivityReward> selectAll();

    int updateByPrimaryKey(TpTenSecondsActivityReward record);

    List<TpTenSecondsActivityReward> getReward(String activity_id);
}