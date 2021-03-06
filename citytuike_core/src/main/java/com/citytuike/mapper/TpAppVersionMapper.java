package com.citytuike.mapper;

import com.citytuike.model.TpAppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpAppVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpAppVersion record);

    TpAppVersion selectByPrimaryKey(Integer id);

    List<TpAppVersion> selectAll();

    int updateByPrimaryKey(TpAppVersion record);

    TpAppVersion getVersion();

    TpAppVersion getNewVersion();

}