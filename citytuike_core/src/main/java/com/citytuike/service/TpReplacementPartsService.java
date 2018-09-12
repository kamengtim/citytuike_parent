package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpReplacementParts;

public interface TpReplacementPartsService {
    void insertReplacement(TpDevice tpDevice, String name, String reason, String files, String address);

    LimitPageList getLimitPageList(Integer type, String pageNo, String pageSize);

    JSONObject getJson(TpReplacementParts tpReplacementPart,Integer type);
}
