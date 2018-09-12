package com.citytuike.mapper;

import com.citytuike.model.TpScanLog;

import java.util.List;

public interface TpScanLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpScanLog record);

    TpScanLog selectByPrimaryKey(Integer id);

    List<TpScanLog> selectAll();

    int updateByPrimaryKey(TpScanLog record);

    TpScanLog getStutas(Integer id);

    void update(Integer id);
}