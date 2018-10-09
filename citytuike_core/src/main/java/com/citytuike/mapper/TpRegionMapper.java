package com.citytuike.mapper;

import com.citytuike.model.TpRegion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpRegionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpRegion record);

    TpRegion selectByPrimaryKey(Integer id);

    List<TpRegion> selectAll();

    int updateByPrimaryKey(TpRegion record);


    String getAddressProvince(Integer province);

    String getAddressCity(Integer city);

    String getAddressDistrict(Integer district);

    String getCityName(Integer city);

    String getProvince(Integer province);

    String getDistrict(Integer district);

    TpRegion getNameByFanId(Integer address);

    String getTwon(Integer twonName);
}