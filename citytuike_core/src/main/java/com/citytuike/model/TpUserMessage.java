package com.citytuike.model;

import lombok.*;

import java.util.List;

public class TpUserMessage {
    private Integer rec_id;

    private Integer user_id;

    private Integer message_id;

    private Integer category;

    private Boolean status;

    private List<TpMessage> messages;

    public Integer getRec_id() {
        return rec_id;
    }

    public void setRec_id(Integer rec_id) {
        this.rec_id = rec_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<TpMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<TpMessage> messages) {
        this.messages = messages;
    }
}