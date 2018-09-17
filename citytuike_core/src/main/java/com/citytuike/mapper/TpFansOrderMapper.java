package com.citytuike.mapper;

import com.citytuike.model.TpFansOrder;

import java.util.List;

public interface TpFansOrderMapper {
    int deleteByPrimaryKey(Integer order_id);

    int insert(TpFansOrder record);

    TpFansOrder selectByPrimaryKey(Integer order_id);

    List<TpFansOrder> selectAll();

    int updateByPrimaryKey(TpFansOrder record);
}