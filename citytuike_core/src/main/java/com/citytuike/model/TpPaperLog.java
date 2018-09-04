package com.citytuike.model;

import java.math.BigDecimal;

public class TpPaperLog {
    private Integer id;

    private Integer device_id;

    private Integer number;

    private Byte type;

    private Integer add_time;

    private BigDecimal income;

    private String scene_str;

    private Integer handel_user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getScene_str() {
        return scene_str;
    }

    public void setScene_str(String scene_str) {
        this.scene_str = scene_str;
    }

    public Integer getHandel_user_id() {
        return handel_user_id;
    }

    public void setHandel_user_id(Integer handel_user_id) {
        this.handel_user_id = handel_user_id;
    }
}