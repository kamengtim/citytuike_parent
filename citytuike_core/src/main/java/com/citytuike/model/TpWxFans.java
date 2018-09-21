package com.citytuike.model;

import java.io.Serializable;

public class TpWxFans implements Serializable{
    private Integer id;

    private Integer user_id;

    private String add_ref_date;

    private Integer add_user_source;

    private Integer new_user;

    private Integer cancel_user;

    private Integer add_time;

    private Integer cumulate_user;

    private String count_ref_date;

    private Integer count_user_source;

    private String flag_date;

    private Integer need_fans_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAdd_ref_date() {
        return add_ref_date;
    }

    public void setAdd_ref_date(String add_ref_date) {
        this.add_ref_date = add_ref_date;
    }

    public Integer getAdd_user_source() {
        return add_user_source;
    }

    public void setAdd_user_source(Integer add_user_source) {
        this.add_user_source = add_user_source;
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }

    public Integer getCancel_user() {
        return cancel_user;
    }

    public void setCancel_user(Integer cancel_user) {
        this.cancel_user = cancel_user;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getCumulate_user() {
        return cumulate_user;
    }

    public void setCumulate_user(Integer cumulate_user) {
        this.cumulate_user = cumulate_user;
    }

    public String getCount_ref_date() {
        return count_ref_date;
    }

    public void setCount_ref_date(String count_ref_date) {
        this.count_ref_date = count_ref_date;
    }

    public Integer getCount_user_source() {
        return count_user_source;
    }

    public void setCount_user_source(Integer count_user_source) {
        this.count_user_source = count_user_source;
    }

    public String getFlag_date() {
        return flag_date;
    }

    public void setFlag_date(String flag_date) {
        this.flag_date = flag_date;
    }

    public Integer getNeed_fans_id() {
        return need_fans_id;
    }

    public void setNeed_fans_id(Integer need_fans_id) {
        this.need_fans_id = need_fans_id;
    }
}