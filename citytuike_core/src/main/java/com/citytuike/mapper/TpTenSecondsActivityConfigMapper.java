package com.citytuike.mapper;

import com.citytuike.model.TpTenSecondsActivityConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpTenSecondsActivityConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpTenSecondsActivityConfig record);

    TpTenSecondsActivityConfig selectByPrimaryKey(Integer id);

    List<TpTenSecondsActivityConfig> selectAll();

    int updateByPrimaryKey(TpTenSecondsActivityConfig record);

    TpTenSecondsActivityConfig getConfig(String activity_id);

}