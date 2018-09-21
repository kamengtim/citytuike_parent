package com.citytuike.mapper;

import com.citytuike.model.TpArticle;

import java.util.List;

public interface TpArticleMapper {
    int deleteByPrimaryKey(Integer article_id);

    int insert(TpArticle record);

    TpArticle selectByPrimaryKey(Integer article_id);

    List<TpArticle> selectAll();

    int updateByPrimaryKey(TpArticle record);

    List<TpArticle> getArticleList(String cat_id);

    TpArticle getArticleCon(String article_id);

    int update(TpArticle tpArticle);
}