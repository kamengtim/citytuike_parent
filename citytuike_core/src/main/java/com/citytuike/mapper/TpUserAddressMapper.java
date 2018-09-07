package com.citytuike.mapper;

import com.citytuike.model.TpUserAddress;

import java.util.List;

public interface TpUserAddressMapper {
    int deleteByPrimaryKey(Integer address_id);

    int insert(TpUserAddress record);

    TpUserAddress selectByPrimaryKey(Integer address_id);

    List<TpUserAddress> selectAll();

    int updateByPrimaryKey(TpUserAddress record);
}