package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpArticle;

import java.util.List;

public interface TpArticleService {
    List<TpArticle> getArticleList(String cat_id);

    JSONObject getJson(TpArticle tpArticle);

    TpArticle getArticleCon(String article_id);

    int update(TpArticle tpArticle);

    JSONObject getDetailJson(TpArticle tpArticle);
}
