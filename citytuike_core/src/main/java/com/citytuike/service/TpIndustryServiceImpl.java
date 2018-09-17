package com.citytuike.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpIndustryMapper;
import com.citytuike.model.TpIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpIndustryServiceImpl implements TpIndustryService {
    @Autowired
    private TpIndustryMapper tpIndustryMapper;
    @Override
    public List<TpIndustry> getIndustry() {
        List<TpIndustry> tpIndustries = tpIndustryMapper.getIndustry();
        return tpIndustries;
    }

    @Override
    public JSONObject getJson(TpIndustry tpIndustry) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpIndustry.getId());
        jsonObject.put("name",tpIndustry.getName());
        jsonObject.put("fid",tpIndustry.getFid());
        jsonObject.put("level",tpIndustry.getLevel());
        return jsonObject;
    }

    @Override
    public List<TpIndustry> getIndustrySon(Integer fid) {
        List<TpIndustry> tpIndustries = tpIndustryMapper.getIndustrySon(fid);
        return tpIndustries;
    }

    @Override
    public JSONObject getSonJson(TpIndustry tpIndustriesSon) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpIndustriesSon.getId());
        jsonObject.put("name",tpIndustriesSon.getName());
        jsonObject.put("fid",tpIndustriesSon.getFid());
        jsonObject.put("level",tpIndustriesSon.getLevel());
        return jsonObject;
    }
}
