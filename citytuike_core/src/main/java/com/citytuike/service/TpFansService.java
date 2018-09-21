package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpFans;
import com.citytuike.model.TpRegion;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TpFansService {

    PageInfo getLimtPageList(String area_id, String industry);

    JSONObject getJson(TpFans tpFan);

    TpFans fansDetails(String id);

    JSONObject getJson(TpFans tpFans, TpRegion tpRegion);

    PageInfo fansTypeList();

    void saveFansList(TpFans tpFans);

    TpFans getFansOrderSn(Integer id);
}
