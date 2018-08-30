package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpAccountLog;
public interface ITpAccountLogService {


    int insertAccountLog(TpAccountLog tpAccountLog);


    LimitPageList getDetail(Integer user_id, String type,String page);

    JSONObject getJsonToAccount(TpAccountLog tpAccountLog);

    double SumMoney(Integer user_id);
}
