package com.citytuike.model;

import java.io.Serializable;

public class TpMessage implements Serializable{
    private Integer message_id;

    private Short admin_id;

    private Boolean type;

    private Integer category;

    private Integer send_time;

    private Byte send_status;

    private String error_msg;

    private Integer error_num;

    private Integer create_time;

    private String message;

    private String data;

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

    public Byte getSend_status() {
        return send_status;
    }

    public void setSend_status(Byte send_status) {
        this.send_status = send_status;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Integer getError_num() {
        return error_num;
    }

    public void setError_num(Integer error_num) {
        this.error_num = error_num;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
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