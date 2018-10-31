package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpArticle;
import com.citytuike.model.TpArticleCat;

import java.util.List;

public interface TpArticleService {
    List<TpArticle> getArticleList(String cat_id);

    JSONObject getJson(TpArticle tpArticle);

    TpArticle getArticleCon(String article_id);

    int update(TpArticle tpArticle);

    JSONObject getDetailJson(TpArticle tpArticle);

    List<TpArticleCat> findArticleCatByPid(Integer pid);

    int getClick_Count(Integer catId);
}
