package com.citytuike.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpRegion;

import java.util.List;

public interface TpRegionService {
    String getCityName(Integer city);

    String getProvince(Integer province);

    String getDistrict(Integer district);

    TpRegion getNameByFanId(Integer address);


    String getTwon(Integer twonName);

    List<TpRegion>  selectOne();

    JSONObject selectById(TpRegion tpRegion);

    List<TpRegion> selectByParentId(Integer id);

    List<TpRegion> getDis(Integer id);
}
