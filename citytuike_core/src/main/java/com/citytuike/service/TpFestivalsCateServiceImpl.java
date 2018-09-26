package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpFestivalsCateMapper;
import com.citytuike.model.TpFestivalsCate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpFestivalsCateServiceImpl implements TpFestivalsCateService {
    @Autowired
    private TpFestivalsCateMapper tpFestivalsCateMapper;
    @Override
    public JSONObject selectCate(String article_id) {
        JSONObject jsonObject = new JSONObject();
        TpFestivalsCate tpFestivalsCate = tpFestivalsCateMapper.selectCate(article_id);
        jsonObject.put("id",tpFestivalsCate.getId());
        jsonObject.put("catename",tpFestivalsCate.getCatename());
        jsonObject.put("view_status",tpFestivalsCate.getView_status());
        jsonObject.put("add_time",tpFestivalsCate.getAdd_time());
        jsonObject.put("article_id",tpFestivalsCate.getArticle_id());
        jsonObject.put("image",tpFestivalsCate.getImage());
        jsonObject.put("parent_id",tpFestivalsCate.getParent_id());
        jsonObject.put("level",tpFestivalsCate.getLevel());
        jsonObject.put("sort_order",tpFestivalsCate.getSort_order());
        return jsonObject;
    }
}
