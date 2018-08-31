package com.citytuike.mapper;

import com.citytuike.model.TpUserBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpUserBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpUserBank record);

    TpUserBank selectByPrimaryKey(Integer id);

    List<TpUserBank> selectAll();

    int updateByPrimaryKey(TpUserBank record);

    List<TpUserBank> selectBankByUserId(Integer user_id);

    void deleteBank(@Param("user_id") Integer user_id, @Param("id") String id);

    TpUserBank selectBankCard(@Param("id") int id, @Param("user_id") Integer user_id);
}