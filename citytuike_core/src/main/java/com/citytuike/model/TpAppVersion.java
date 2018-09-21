package com.citytuike.model;

import java.io.Serializable;

public class TpAppVersion implements Serializable{
    private Integer id;

    private Integer code;

    private String url;

    private Integer add_time;

    private Boolean is_use;

    private Boolean is_flag;

    private String hash_val;

    public String getHash_val() {
        return hash_val;
    }

    public void setHash_val(String hash_val) {
        this.hash_val = hash_val;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Boolean getIs_use() {
        return is_use;
    }

    public void setIs_use(Boolean is_use) {
        this.is_use = is_use;
    }

    public Boolean getIs_flag() {
        return is_flag;
    }

    public void setIs_flag(Boolean is_flag) {
        this.is_flag = is_flag;
    }
}