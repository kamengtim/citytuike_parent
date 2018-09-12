package com.citytuike.model;

import java.io.Serializable;

public class TpApplyReport implements Serializable {
    private Integer id;

    private String idcard;

    private String mobile;

    private String name_first;

    private Integer add_time;

    private String bank_name;

    private Integer bank_id;

    private Boolean status;

    private String tig;

    private Boolean send_gift;

    private String gift;

    private Long money;

    private String name_last;

    private String id_card_all;

    private String mobile_all;

    private Integer user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName_first() {
        return name_first;
    }

    public void setName_first(String name_first) {
        this.name_first = name_first;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public Integer getBank_id() {
        return bank_id;
    }

    public void setBank_id(Integer bank_id) {
        this.bank_id = bank_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTig() {
        return tig;
    }

    public void setTig(String tig) {
        this.tig = tig;
    }

    public Boolean getSend_gift() {
        return send_gift;
    }

    public void setSend_gift(Boolean send_gift) {
        this.send_gift = send_gift;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getName_last() {
        return name_last;
    }

    public void setName_last(String name_last) {
        this.name_last = name_last;
    }

    public String getId_card_all() {
        return id_card_all;
    }

    public void setId_card_all(String id_card_all) {
        this.id_card_all = id_card_all;
    }

    public String getMobile_all() {
        return mobile_all;
    }

    public void setMobile_all(String mobile_all) {
        this.mobile_all = mobile_all;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}