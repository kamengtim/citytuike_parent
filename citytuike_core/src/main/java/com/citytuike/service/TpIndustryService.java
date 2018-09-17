package com.citytuike.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpIndustry;

import java.util.List;

public interface TpIndustryService {
    List<TpIndustry> getIndustry();

    JSONObject getJson(TpIndustry tpIndustry);

    List<TpIndustry> getIndustrySon(Integer fid);

    JSONObject getSonJson(TpIndustry tpIndustriesSon);
}
