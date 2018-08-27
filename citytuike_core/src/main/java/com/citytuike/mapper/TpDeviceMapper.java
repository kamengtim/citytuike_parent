package com.citytuike.mapper;

import com.citytuike.model.TpDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpDevice record);

    TpDevice selectByPrimaryKey(Integer id);

    List<TpDevice> selectAll();

    int updateByPrimaryKey(TpDevice record);

    int selectCountDevice(@Param("user_id") Integer user_id);

    List<TpDevice> selectByPage(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("user_id") Integer user_id);

    List<TpDevice> selectIncome(Integer user_id);

    List<TpDevice> selectParent(Integer user_id);
}