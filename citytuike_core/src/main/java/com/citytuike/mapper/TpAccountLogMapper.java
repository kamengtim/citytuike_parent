package com.citytuike.mapper;


import com.citytuike.model.TpAccountLog;

import java.util.List;

public interface TpAccountLogMapper {
    int deleteByPrimaryKey(Integer log_id);

    int insert(TpAccountLog record);

    TpAccountLog selectByPrimaryKey(Integer log_id);

    List<TpAccountLog> selectAll();

    int updateByPrimaryKey(TpAccountLog record);
}