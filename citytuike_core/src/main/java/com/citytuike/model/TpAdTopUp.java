package com.citytuike.model;

import java.io.Serializable;
import java.util.Date;

public class TpAdTopUp implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer user_id;
    /**
     * 订单号
     */
    private String order_sn;
    /**
     * 金额
     */
    private double order_amount;
    /**
     * 状态（0提交|1已支付|2失效）
     */
    private Integer pay_status;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 交易单号
     */
    private String transaction_id;
    /**
     * 支付时间
     */
    private Date paid_at;
    /**
     * 创建时间
     */
    private Date created_at;
    /**
     * 更新时间
     */
    private Date updated_at;

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

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Date getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(Date paid_at) {
        this.paid_at = paid_at;
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
