package com.citytuike.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TpPaperTransfer implements Serializable{
    private Integer id;

    private Integer from_user_id;

    private Integer to_user_id;

    private Integer number;

    private BigDecimal money;

    private BigDecimal all_money;

    private Date add_time;

    private Byte status;

    private Integer from_before_number;

    private Integer from_after_number;

    private Integer to_before_number;

    private Integer to_after_number;

    private Integer refuse_before_number;

    private Integer refuse_after_number;

    private String remark;

    private Date operate_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(Integer from_user_id) {
        this.from_user_id = from_user_id;
    }

    public Integer getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(Integer to_user_id) {
        this.to_user_id = to_user_id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAll_money() {
        return all_money;
    }

    public void setAll_money(BigDecimal all_money) {
        this.all_money = all_money;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getFrom_before_number() {
        return from_before_number;
    }

    public void setFrom_before_number(Integer from_before_number) {
        this.from_before_number = from_before_number;
    }

    public Integer getFrom_after_number() {
        return from_after_number;
    }

    public void setFrom_after_number(Integer from_after_number) {
        this.from_after_number = from_after_number;
    }

    public Integer getTo_before_number() {
        return to_before_number;
    }

    public void setTo_before_number(Integer to_before_number) {
        this.to_before_number = to_before_number;
    }

    public Integer getTo_after_number() {
        return to_after_number;
    }

    public void setTo_after_number(Integer to_after_number) {
        this.to_after_number = to_after_number;
    }

    public Integer getRefuse_before_number() {
        return refuse_before_number;
    }

    public void setRefuse_before_number(Integer refuse_before_number) {
        this.refuse_before_number = refuse_before_number;
    }

    public Integer getRefuse_after_number() {
        return refuse_after_number;
    }

    public void setRefuse_after_number(Integer refuse_after_number) {
        this.refuse_after_number = refuse_after_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(Date operate_time) {
        this.operate_time = operate_time;
    }
}