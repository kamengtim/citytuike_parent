package com.citytuike.mapper;

import com.citytuike.model.TpWxUser;

import java.util.List;

public interface TpWxUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpWxUser record);

    TpWxUser selectByPrimaryKey(Integer id);

    List<TpWxUser> selectAll();

    int updateByPrimaryKey(TpWxUser record);

    List<TpWxUser> getWxUser();
}