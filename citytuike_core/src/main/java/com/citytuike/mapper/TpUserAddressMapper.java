package com.citytuike.mapper;

import com.citytuike.model.TpUserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpUserAddressMapper {
    int deleteByPrimaryKey(Integer address_id);

    int insert(TpUserAddress record);

    TpUserAddress selectByPrimaryKey(Integer address_id);

    List<TpUserAddress> selectAll();

    int updateByPrimaryKey(TpUserAddress record);

    TpUserAddress getAddress(@Param("address_id") String address_id, @Param("user_id") Integer user_id);
}