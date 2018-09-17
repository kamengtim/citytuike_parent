package com.citytuike.service;

import com.citytuike.mapper.TpRegionMapper;
import com.citytuike.model.TpRegion;
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

    @Override
    public String getProvince(Integer province) {
        String provinceNmae = tpRegionMapper.getProvince(province);
        return provinceNmae;
    }

    @Override
    public String getDistrict(Integer district) {
        String districtName = tpRegionMapper.getDistrict(district);
        return districtName;
    }

    @Override
    public TpRegion getNameByFanId(Integer address) {
        TpRegion tpRegion = tpRegionMapper.getNameByFanId(address);
        return tpRegion;
    }
}
