package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.Data;
import com.alipay.api.domain.DataEntry;
import com.citytuike.mapper.TpFestivalsContentMapper;
import com.citytuike.model.TpFestivalsContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TpFestivalsContentServiceImpl implements TpFestivalsContentService {
    @Autowired
    private TpFestivalsContentMapper tpFestivalsContentMapper;
    @Override
    public List<TpFestivalsContent> midAutumn(String ha_id) {
        List<TpFestivalsContent> tpFestivalsContents = tpFestivalsContentMapper.midAutumn(ha_id);
        return tpFestivalsContents;
    }

    @Override
    public JSONObject getJson(TpFestivalsContent tpFestivalsContent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpFestivalsContent.getId());
        jsonObject.put("he_id",tpFestivalsContent.getHe_id());
        jsonObject.put("add_time",tpFestivalsContent.getAdd_time());
        jsonObject.put("user_id",tpFestivalsContent.getUser_id());
        jsonObject.put("content",tpFestivalsContent.getContent());
        return jsonObject;
    }

    @Override
    public int insertFestivals(String ha_id, String user_id, String content) {
        TpFestivalsContent tpFestivalsContent = new TpFestivalsContent();
        tpFestivalsContent.setAdd_time((int)(new Date().getTime()/1000));
        tpFestivalsContent.setUser_id(Integer.valueOf(user_id));
        tpFestivalsContent.setContent(content);
        tpFestivalsContent.setHe_id(Integer.valueOf(ha_id));
        int i = tpFestivalsContentMapper.insert(tpFestivalsContent);
        return i;
    }
}
