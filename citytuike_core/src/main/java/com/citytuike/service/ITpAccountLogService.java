package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpAccountLog;

public interface ITpAccountLogService {
    JSONObject UserMoney(Integer user_id);

    LimitPageList getDetail(Integer user_id, String type,String page);

    JSONObject getJsonToAccount(TpAccountLog tpAccountLog);

    double SumMoney(Integer user_id);
}
