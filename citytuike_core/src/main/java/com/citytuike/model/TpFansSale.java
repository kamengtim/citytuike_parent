package com.citytuike.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TpFansSale implements Serializable{
    private Integer id;

    private Integer user_id;

    private Integer sel_class;

    private Integer tag;

    private String logo;

    private String title;

    private String fan_num;

    private String fan_dev;

    private BigDecimal price;

    private Integer address;

    private String catename;

    private Integer auth;

    private String auth_body;

    private Integer original;

    private String read_num;

    private String industry;

    private Integer type;

    private String scale_man;

    private String scale_women;

    private Integer liu;

    private Integer auth_bodys;

    private Integer auth_body_change;

    private Integer used_existx;

    private String account;

    private BigDecimal mon_money;

    private BigDecimal ad_money;

    private String mobile;

    private String wechat;

    private Date created_at;

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

    public Integer getSel_class() {
        return sel_class;
    }

    public void setSel_class(Integer sel_class) {
        this.sel_class = sel_class;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFan_num() {
        return fan_num;
    }

    public void setFan_num(String fan_num) {
        this.fan_num = fan_num;
    }

    public String getFan_dev() {
        return fan_dev;
    }

    public void setFan_dev(String fan_dev) {
        this.fan_dev = fan_dev;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getAuth_body() {
        return auth_body;
    }

    public void setAuth_body(String auth_body) {
        this.auth_body = auth_body;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getScale_man() {
        return scale_man;
    }

    public void setScale_man(String scale_man) {
        this.scale_man = scale_man;
    }

    public String getScale_women() {
        return scale_women;
    }

    public void setScale_women(String scale_women) {
        this.scale_women = scale_women;
    }

    public Integer getLiu() {
        return liu;
    }

    public void setLiu(Integer liu) {
        this.liu = liu;
    }

    public Integer getAuth_bodys() {
        return auth_bodys;
    }

    public void setAuth_bodys(Integer auth_bodys) {
        this.auth_bodys = auth_bodys;
    }

    public Integer getAuth_body_change() {
        return auth_body_change;
    }

    public void setAuth_body_change(Integer auth_body_change) {
        this.auth_body_change = auth_body_change;
    }

    public Integer getUsed_existx() {
        return used_existx;
    }

    public void setUsed_existx(Integer used_existx) {
        this.used_existx = used_existx;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getMon_money() {
        return mon_money;
    }

    public void setMon_money(BigDecimal mon_money) {
        this.mon_money = mon_money;
    }

    public BigDecimal getAd_money() {
        return ad_money;
    }

    public void setAd_money(BigDecimal ad_money) {
        this.ad_money = ad_money;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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