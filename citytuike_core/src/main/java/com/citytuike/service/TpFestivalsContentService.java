package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpFestivalsContent;

import java.util.List;

public interface TpFestivalsContentService {
    List<TpFestivalsContent> midAutumn(String ha_id);

    JSONObject getJson(TpFestivalsContent tpFestivalsContent);

    int insertFestivals(String ha_id, String user_id, String content);
}
