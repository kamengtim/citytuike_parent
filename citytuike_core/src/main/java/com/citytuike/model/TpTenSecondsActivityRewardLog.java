package com.citytuike.model;

import java.io.Serializable;
import java.util.Date;

public class TpTenSecondsActivityRewardLog implements Serializable{
    private Integer id;

    private Integer activity_id;

    private Integer reward_id;

    private Integer user_id;

    private Date add_time;

    private String seconds;

    private Integer status;

    private Date send_time;

    private String logistics_number;

    private String logistics_name;

    private String reward_name;

    private String consignee;

    private String province;

    private String city;

    private String district;

    private String twon;

    private String address;

    private String mobile;

    private Integer from_user_id;

    private Integer goods_id;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

    public Integer getReward_id() {
        return reward_id;
    }

    public void setReward_id(Integer reward_id) {
        this.reward_id = reward_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public String getLogistics_number() {
        return logistics_number;
    }

    public void setLogistics_number(String logistics_number) {
        this.logistics_number = logistics_number;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getReward_name() {
        return reward_name;
    }

    public void setReward_name(String reward_name) {
        this.reward_name = reward_name;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTwon() {
        return twon;
    }

    public void setTwon(String twon) {
        this.twon = twon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(Integer from_user_id) {
        this.from_user_id = from_user_id;
    }
}