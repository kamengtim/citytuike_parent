package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpPaperLog;

public interface TpPaperLogService {
    LimitPageList paperLog(Integer user_id, String page, String device_id, String type);

    JSONObject getJsonLog(TpPaperLog tpPaperLog);

    void save(String device_sn, String number, Integer user_id);
}
