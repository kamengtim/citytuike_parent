package com.citytuike.model;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
public class TpMessage implements Serializable {
    private Integer message_id;

    private Short admin_id;

    private Boolean type;

    private Integer category;

    private Integer send_time;

    private String message;

    private String data;

    private List<TpUserMessage> tpUserMessages = new ArrayList<TpUserMessage>();

    public List<TpUserMessage> getTpUserMessages() {
        return tpUserMessages;
    }

    public void setTpUserMessages(List<TpUserMessage> tpUserMessages) {
        this.tpUserMessages = tpUserMessages;
    }



    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Short getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Short admin_id) {
        this.admin_id = admin_id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getSend_time() {
        return send_time;
    }

    public void setSend_time(Integer send_time) {
        this.send_time = send_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}