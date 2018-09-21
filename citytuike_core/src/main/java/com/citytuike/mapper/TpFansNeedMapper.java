package com.citytuike.mapper;

import com.citytuike.model.TpFansNeed;

import java.util.List;

public interface TpFansNeedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpFansNeed record);

    List<TpFansNeed> selectByPrimaryKey(Integer user_id);

    List<TpFansNeed> selectAll();

    int updateByPrimaryKey(TpFansNeed record);

    void save(TpFansNeed tpFansNeed);

    TpFansNeed getNeedByOrderId(Integer user_id, int order_id);
}