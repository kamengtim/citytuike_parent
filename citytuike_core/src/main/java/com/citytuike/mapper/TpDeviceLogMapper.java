package com.citytuike.mapper;


import com.citytuike.model.TpDeviceLog;

import java.util.List;

public interface TpDeviceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpDeviceLog record);

    TpDeviceLog selectByPrimaryKey(Integer id);

    List<TpDeviceLog> selectAll();

    int updateByPrimaryKey(TpDeviceLog record);

    int getTodayDevice(Integer user_id);

    double getTodayIncome(Integer user_id);

    int getCountMan(Integer user_id);
}