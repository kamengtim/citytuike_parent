package com.citytuike.model;

import java.io.Serializable;

public class TpFestivalsContent implements Serializable{
    private Integer id;

    private Integer he_id;

    private Integer add_time;

    private Integer user_id;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHe_id() {
        return he_id;
    }

    public void setHe_id(Integer he_id) {
        this.he_id = he_id;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}