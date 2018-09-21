package com.citytuike.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TpCartGift implements Serializable{
    private Integer id;

    private String gift_name;

    private Integer create_time;

    private Integer add_time;

    private Boolean gift_type;

    private Boolean status;

    private BigDecimal money;

    private Integer user_id;

    private Integer card_list_id;

    private Integer report_id;

    private String mess;

    private String orderid;

    private String mobile;

    private String taskid;

    private Boolean obtain;

    private Integer user_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Boolean getGift_type() {
        return gift_type;
    }

    public void setGift_type(Boolean gift_type) {
        this.gift_type = gift_type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCard_list_id() {
        return card_list_id;
    }

    public void setCard_list_id(Integer card_list_id) {
        this.card_list_id = card_list_id;
    }

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public Boolean getObtain() {
        return obtain;
    }

    public void setObtain(Boolean obtain) {
        this.obtain = obtain;
    }

    public Integer getUser_time() {
        return user_time;
    }

    public void setUser_time(Integer user_time) {
        this.user_time = user_time;
    }
}