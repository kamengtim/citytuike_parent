package com.citytuike.model;

import java.io.Serializable;

public class TpScanHelp implements Serializable{
    private Integer id;

    private Integer user_id;

    private Integer friend_id;

    private Integer add_time;

    private Integer use_time;

    private Integer help_time;


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

    public Integer getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(Integer friend_id) {
        this.friend_id = friend_id;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getUse_time() {
        return use_time;
    }

    public void setUse_time(Integer use_time) {
        this.use_time = use_time;
    }

    public Integer getHelp_time() {
        return help_time;
    }

    public void setHelp_time(Integer help_time) {
        this.help_time = help_time;
    }

}