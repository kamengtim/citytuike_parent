package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpArticleCatMapper;
import com.citytuike.mapper.TpArticleMapper;
import com.citytuike.model.TpArticle;
import com.citytuike.model.TpArticleCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpArticleServiceImpl implements TpArticleService {
    @Autowired
    private TpArticleMapper tpArticleMapper;
    @Autowired
    private TpArticleCatMapper tpArticleCatMapper;
    @Override
    public List<TpArticle> getArticleList(String cat_id) {
        List<TpArticle>tpArticles = tpArticleMapper.getArticleList(cat_id);
        return tpArticles;
    }

    @Override
    public JSONObject getJson(TpArticle tpArticle) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_id",tpArticle.getArticle_id());
        jsonObject.put("title",tpArticle.getTitle());
        jsonObject.put("description",tpArticle.getDescription());
        jsonObject.put("add_time",tpArticle.getAdd_time());
        jsonObject.put("click",tpArticle.getClick());
        return jsonObject;
    }

    @Override
    public TpArticle getArticleCon(String article_id) {
        TpArticle tpArticle =  tpArticleMapper.getArticleCon(article_id);
        return tpArticle;
    }

    @Override
    public int update(TpArticle tpArticle) {
        int a =  tpArticleMapper.update(tpArticle);
        return a;
    }

    @Override
    public JSONObject getDetailJson(TpArticle tpArticle) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_id",tpArticle.getArticle_id());
        jsonObject.put("cat_id",tpArticle.getCat_id());
        jsonObject.put("title",tpArticle.getTitle());
        jsonObject.put("content",tpArticle.getContent());
        jsonObject.put("author",tpArticle.getAuthor());
        jsonObject.put("author_email",tpArticle.getAuthor_email());
        jsonObject.put("keywords",tpArticle.getKeywords());
        jsonObject.put("article_type",tpArticle.getArticle_type());
        jsonObject.put("is_open",tpArticle.getIs_open());
        jsonObject.put("add_time",tpArticle.getAdd_time());
        jsonObject.put("file_url",tpArticle.getFile_url());
        jsonObject.put("open_type",tpArticle.getOpen_type());
        jsonObject.put("link",tpArticle.getLink());
        jsonObject.put("description",tpArticle.getDescription());
        jsonObject.put("click",tpArticle.getClick());
        jsonObject.put("publish_time",tpArticle.getPublish_time());
        jsonObject.put("thumb",tpArticle.getThumb());
        return jsonObject;
    }

    @Override
    public List<TpArticleCat> findArticleCatByPid(Integer pid) {
        return tpArticleCatMapper.findArticleCatByPid(pid);
    }

    @Override
    public int getClick_Count(Integer catId) {
        return tpArticleCatMapper.getClick_Count(catId);
    }
}
