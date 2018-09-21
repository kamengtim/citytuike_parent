package com.citytuike.model;

import java.io.Serializable;

public class TpScanLog implements Serializable{
    private Integer id;

    private Integer user_id;

    private Integer device_id;

    private Integer add_time;

    private String scene_str;

    private Integer wx_id;

    private String openid;

    private Integer help_id;

    private Boolean status;

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

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public String getScene_str() {
        return scene_str;
    }

    public void setScene_str(String scene_str) {
        this.scene_str = scene_str;
    }

    public Integer getWx_id() {
        return wx_id;
    }

    public void setWx_id(Integer wx_id) {
        this.wx_id = wx_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getHelp_id() {
        return help_id;
    }

    public void setHelp_id(Integer help_id) {
        this.help_id = help_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}