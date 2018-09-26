package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpFestivalsCate;

public interface TpFestivalsCateService {
    JSONObject selectCate(String article_id);
}
