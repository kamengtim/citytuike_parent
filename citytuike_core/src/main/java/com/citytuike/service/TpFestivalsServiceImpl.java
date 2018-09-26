package com.citytuike.service;

import com.citytuike.mapper.TpFestivalsMapper;
import com.citytuike.model.TpFestivals;
import com.citytuike.model.TpFestivalsContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpFestivalsServiceImpl implements TpFestivalsService {
    @Autowired
    private TpFestivalsMapper tpFestivalsMapper;
    @Override
    public int setMidAutumn(Integer user_id, int date, String article_id) {
        TpFestivals tpFestivals = new TpFestivals();
        tpFestivals.setUser_id(user_id);
        tpFestivals.setAdd_time(date);
        tpFestivals.setArticle_id(article_id);
        int i = tpFestivalsMapper.insert(tpFestivals);
        return i;
    }

    @Override
    public TpFestivals selectFestivals(String user_id, String ha_id) {
        TpFestivals tpFestivals = tpFestivalsMapper.selectFestivals(user_id,ha_id);
        return tpFestivals;
    }
}
