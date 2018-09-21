package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUserLog;

import java.util.List;

public interface TpUserLogService  {
    List<TpUserLog> paper();

    JSONObject getJson(TpUserLog tpUserLog);
}
