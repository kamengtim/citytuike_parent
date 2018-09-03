package com.citytuike.mapper;

import com.citytuike.model.TpAccountLog;
import com.citytuike.model.TpUserAliAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpUserAliAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpUserAliAccount record);

    TpUserAliAccount selectByPrimaryKey(Integer id);

    List<TpUserAliAccount> selectAll();

    int updateByPrimaryKey(TpUserAliAccount record);

    void save(TpUserAliAccount tpUserAliAccount);

    int selectAliAccount();

    List<TpAccountLog> selectAliAccountList(@Param("startPos") int startPos, @Param("pageSize") int pageSize);

    TpUserAliAccount selectAliById(String id);
}