package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpDeviceLog;

public interface TpDeviceLogService {
    JSONObject getTodayDevice(Integer user_id);
}
