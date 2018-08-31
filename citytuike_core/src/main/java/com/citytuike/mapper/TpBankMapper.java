package com.citytuike.mapper;


import com.citytuike.model.TpBank;

import java.util.List;

public interface TpBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpBank record);

    TpBank selectByPrimaryKey(Integer bank_id);

    List<TpBank> selectAll();

    int updateByPrimaryKey(TpBank record);

    List<TpBank> selectBankList(Integer user_id);

    String selectBank(int id);
}