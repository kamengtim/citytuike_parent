package com.citytuike.model;

import java.math.BigDecimal;

public class TpUserLevel {
    private Short level_id;

    private String level_name;

    private BigDecimal amount;

    private Short discount;

    private String describe;

    public Short getLevel_id() {
        return level_id;
    }

    public void setLevel_id(Short level_id) {
        this.level_id = level_id;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Short getDiscount() {
        return discount;
    }

    public void setDiscount(Short discount) {
        this.discount = discount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}