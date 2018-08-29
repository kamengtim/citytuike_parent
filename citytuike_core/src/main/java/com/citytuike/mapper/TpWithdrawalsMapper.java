package com.citytuike.mapper;

import com.citytuike.model.TpWithdrawals;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpWithdrawalsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpWithdrawals record);

    TpWithdrawals selectByPrimaryKey(Integer id);

    List<TpWithdrawals> selectAll();

    int updateByPrimaryKey(TpWithdrawals record);

    int CountWithdrawals(Integer user_id);

    List<TpWithdrawals> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("user_id") Integer user_id);
}