package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpAppVersion;

public interface TpAppVersionService {
    TpAppVersion getVersion();

    TpAppVersion getNewVersion();

    JSONObject getJson(TpAppVersion version);
}
