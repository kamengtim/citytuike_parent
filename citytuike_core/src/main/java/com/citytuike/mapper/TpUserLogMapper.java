package com.citytuike.mapper;

import com.citytuike.model.TpUserLog;

import java.util.List;

public interface TpUserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpUserLog record);

    TpUserLog selectByPrimaryKey(Integer id);

    List<TpUserLog> selectAll();

    int updateByPrimaryKey(TpUserLog record);

    List<TpUserLog> getPaper();
}