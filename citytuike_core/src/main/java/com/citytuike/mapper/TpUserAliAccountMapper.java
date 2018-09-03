package com.citytuike.mapper;

import com.citytuike.model.TpUserAliAccount;
import java.util.List;

public interface TpUserAliAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpUserAliAccount record);

    TpUserAliAccount selectByPrimaryKey(Integer id);

    List<TpUserAliAccount> selectAll();

    int updateByPrimaryKey(TpUserAliAccount record);
}