package com.citytuike.service;

import com.citytuike.mapper.TpRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpRegionServiceImpl implements TpRegionService {
    @Autowired
    private TpRegionMapper tpRegionMapper;
    @Override
    public String getCityName(Integer city) {
        String cityName = tpRegionMapper.getCityName(city);
        return cityName;
    }
}
