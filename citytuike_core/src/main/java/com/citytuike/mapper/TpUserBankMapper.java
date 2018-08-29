package com.citytuike.mapper;

import com.citytuike.model.TpUserBank;

import java.util.List;

public interface TpUserBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpUserBank record);

    TpUserBank selectByPrimaryKey(Integer id);

    List<TpUserBank> selectAll();

    int updateByPrimaryKey(TpUserBank record);

    List<TpUserBank> selectBankByUserId(Integer user_id);
}