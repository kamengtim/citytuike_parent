package com.citytuike.service;

import com.citytuike.model.TpRegion;

import java.util.List;

public interface TpRegionService {
    String getCityName(Integer city);

    String getProvince(Integer province);

    String getDistrict(Integer district);

    TpRegion getNameByFanId(Integer address);


    String getTwon(Integer twonName);
}
