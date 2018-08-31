package com.citytuike.model;

import java.math.BigDecimal;

public class TpCardList {
    private Integer id;

    private String logo;

    private String title;

    private Integer apply_num;

    private String tig;

    private Long money;

    private String gift;

    private Integer create_time;

    private String url;

    private String card_logo;

    private String card_logo2;

    private BigDecimal partner_money;

    private BigDecimal chief_money;

    private BigDecimal manager_money;

    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getApply_num() {
        return apply_num;
    }

    public void setApply_num(Integer apply_num) {
        this.apply_num = apply_num;
    }

    public String getTig() {
        return tig;
    }

    public void setTig(String tig) {
        this.tig = tig;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCard_logo() {
        return card_logo;
    }

    public void setCard_logo(String card_logo) {
        this.card_logo = card_logo;
    }

    public String getCard_logo2() {
        return card_logo2;
    }

    public void setCard_logo2(String card_logo2) {
        this.card_logo2 = card_logo2;
    }

    public BigDecimal getPartner_money() {
        return partner_money;
    }

    public void setPartner_money(BigDecimal partner_money) {
        this.partner_money = partner_money;
    }

    public BigDecimal getChief_money() {
        return chief_money;
    }

    public void setChief_money(BigDecimal chief_money) {
        this.chief_money = chief_money;
    }

    public BigDecimal getManager_money() {
        return manager_money;
    }

    public void setManager_money(BigDecimal manager_money) {
        this.manager_money = manager_money;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}