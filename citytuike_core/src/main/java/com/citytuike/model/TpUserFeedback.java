package com.citytuike.model;

import com.citytuike.util.Type;

import java.util.Date;

public class TpUserFeedback {
    private Integer id;

    private Integer user_id;

    private Integer cs_id;

    private Type type;

    private Byte user_send;

    private Byte status;

    private Byte is_delete;

    private Date send_date;

    private Integer send_time;

    private String content;

    public TpUsers getTpUsers() {
        return tpUsers;
    }

    public void setTpUsers(TpUsers tpUsers) {
        this.tpUsers = tpUsers;
    }

    private TpUsers tpUsers;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

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

    public Integer getCs_id() {
        return cs_id;
    }

    public void setCs_id(Integer cs_id) {
        this.cs_id = cs_id;
    }


    public Byte getUser_send() {
        return user_send;
    }

    public void setUser_send(Byte user_send) {
        this.user_send = user_send;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Byte is_delete) {
        this.is_delete = is_delete;
    }

    public Date getSend_date() {
        return send_date;
    }

    public void setSend_date(Date send_date) {
        this.send_date = send_date;
    }

    public Integer getSend_time() {
        return send_time;
    }

    public void setSend_time(Integer send_time) {
        this.send_time = send_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}