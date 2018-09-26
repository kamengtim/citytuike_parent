package com.citytuike.service;

import com.citytuike.model.TpFestivals;

public interface TpFestivalsService {
    int setMidAutumn(Integer user_id, int date, String article_id);

    TpFestivals selectFestivals(String user_id, String ha_id);
}
