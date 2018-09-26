package com.citytuike.mapper;

import com.citytuike.model.TpTenSecondsActivityLog;
import java.util.List;

public interface TpTenSecondsActivityLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpTenSecondsActivityLog record);

    TpTenSecondsActivityLog selectByPrimaryKey(Integer id);

    List<TpTenSecondsActivityLog> selectAll();

    int updateByPrimaryKey(TpTenSecondsActivityLog record);
}