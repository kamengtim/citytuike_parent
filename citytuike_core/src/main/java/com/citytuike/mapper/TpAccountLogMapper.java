package com.citytuike.mapper;


import com.citytuike.model.TpAccountLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpAccountLogMapper {
    int deleteByPrimaryKey(Integer log_id);

    int insert(TpAccountLog record);

    TpAccountLog selectByPrimaryKey(Integer log_id);

    List<TpAccountLog> selectAll();

    int updateByPrimaryKey(TpAccountLog record);

<<<<<<< HEAD
=======
    TpAccountLog selectUserMoney(Integer user_id);


    int accountDetail(@Param("user_id") Integer user_id, @Param("type") String type);

    List<TpAccountLog> selectUserMoneyDetail(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("user_id") Integer user_id, @Param("type") String type);

    double SumMoney(Integer user_id);
>>>>>>> 588eed3222952eb7bd34ba2f22254d881f442aac
}