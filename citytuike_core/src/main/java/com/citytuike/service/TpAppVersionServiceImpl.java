package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpAppVersionMapper;
import com.citytuike.model.TpAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpAppVersionServiceImpl implements TpAppVersionService {
    @Autowired
    private TpAppVersionMapper tpAppVersionMapper;
    @Override
    public TpAppVersion getVersion() {
        TpAppVersion tpAppVersion = tpAppVersionMapper.getVersion();
        return tpAppVersion;
    }

    @Override
    public TpAppVersion getNewVersion() {
        TpAppVersion tpAppVersion = tpAppVersionMapper.getNewVersion();
        return tpAppVersion;
    }

    @Override
    public JSONObject getJson(TpAppVersion version) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",version.getId());
        jsonObject.put("code",version.getCode());
        jsonObject.put("url",version.getUrl());
        jsonObject.put("add_time",version.getAdd_time());
        jsonObject.put("is_use",version.getIs_use());
        jsonObject.put("is_flag",version.getIs_flag());
        jsonObject.put("hash_val",version.getHash_val());
        return jsonObject;
    }

}
