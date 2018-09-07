package com.citytuike.model;

import java.io.Serializable;
import java.util.Date;

public class TpUserFinance implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer user_id;
    /**
     * 类型（1增加|2减少）
     */
    private Integer change_type;
    /**
     * 变化金额
     */
    private double amount;
    /**
     * 用户剩余可用金额
     */
    private double user_balance;
    /**
     * 业务标识
     */
    private String biz_sign;
    /**
     * 业务单号
     */
    private String biz_sn;
    /**
     * 业务备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date created_at;

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

    public Integer getChange_type() {
        return change_type;
    }

    public void setChange_type(Integer change_type) {
        this.change_type = change_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(double user_balance) {
        this.user_balance = user_balance;
    }

    public String getBiz_sign() {
        return biz_sign;
    }

    public void setBiz_sign(String biz_sign) {
        this.biz_sign = biz_sign;
    }

    public String getBiz_sn() {
        return biz_sn;
    }

    public void setBiz_sn(String biz_sn) {
        this.biz_sn = biz_sn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
