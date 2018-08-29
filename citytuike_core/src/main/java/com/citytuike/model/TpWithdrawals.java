package com.citytuike.model;

import java.math.BigDecimal;

public class TpWithdrawals {
    private Integer id;

    private Integer user_id;

    private BigDecimal money;

    private Integer create_time;

    private Integer check_time;

    private Integer pay_time;

    private Integer refuse_time;

    private String bank_name;

    private String bank_card;

    private String realname;

    private String remark;

    private BigDecimal taxfee;

    private Boolean status;

    private String pay_code;

    private String error_code;

    private Byte is_paid;

    private Integer query_time;

    private String order_sn;

    private String serial_number;

    private Boolean send_type;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Integer check_time) {
        this.check_time = check_time;
    }

    public Integer getPay_time() {
        return pay_time;
    }

    public void setPay_time(Integer pay_time) {
        this.pay_time = pay_time;
    }

    public Integer getRefuse_time() {
        return refuse_time;
    }

    public void setRefuse_time(Integer refuse_time) {
        this.refuse_time = refuse_time;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getTaxfee() {
        return taxfee;
    }

    public void setTaxfee(BigDecimal taxfee) {
        this.taxfee = taxfee;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public Byte getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(Byte is_paid) {
        this.is_paid = is_paid;
    }

    public Integer getQuery_time() {
        return query_time;
    }

    public void setQuery_time(Integer query_time) {
        this.query_time = query_time;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public Boolean getSend_type() {
        return send_type;
    }

    public void setSend_type(Boolean send_type) {
        this.send_type = send_type;
    }
}