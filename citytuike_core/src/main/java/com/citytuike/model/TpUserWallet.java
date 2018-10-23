package com.citytuike.model;

import java.io.Serializable;
import java.util.Date;

public class TpUserWallet implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer user_id;
    /**
     * 可用余额
     */
    private double balance;
    /**
     * 可用余额
     */
    private double paid_amount;
    /**
     * 创建时间
     */
    private Date created_at;
    /**
     * 更新时间
     */
    private Date updated_at;

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
