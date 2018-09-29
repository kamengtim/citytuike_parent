package com.citytuike.model;

import java.io.Serializable;

public class TpFestivalsCate implements Serializable{
    private Integer id;

    private String catename;

    private Boolean view_status;

    private Integer add_time;

    private String article_id;

    private String image;

    private Integer parent_id;

    private Integer level;

    private Integer sort_order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public Boolean getView_status() {
        return view_status;
    }

    public void setView_status(Boolean view_status) {
        this.view_status = view_status;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }
}