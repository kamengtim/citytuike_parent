package com.citytuike.model;

import java.math.BigDecimal;

public class TpFansOrder {
    private Integer order_id;

    private String order_sn;

    private Integer user_id;

    private Boolean order_status;

    private Boolean shipping_status;

    private Boolean pay_status;

    private String consignee;

    private Integer country;

    private Integer province;

    private Integer city;

    private Integer district;

    private Integer twon;

    private String address;

    private String zipcode;

    private String mobile;

    private String email;

    private String shipping_code;

    private String shipping_name;

    private String pay_code;

    private String pay_name;

    private String invoice_title;

    private String taxpayer;

    private BigDecimal goods_price;

    private BigDecimal shipping_price;

    private BigDecimal user_money;

    private BigDecimal coupon_price;

    private Integer integral;

    private BigDecimal integral_money;

    private BigDecimal order_amount;

    private BigDecimal total_amount;

    private Integer add_time;

    private Integer shipping_time;

    private Integer confirm_time;

    private Integer pay_time;

    private String transaction_id;

    private Integer prom_id;

    private Byte prom_type;

    private Short order_prom_id;

    private BigDecimal order_prom_amount;

    private BigDecimal discount;

    private String user_note;

    private String admin_note;

    private String parent_sn;

    private Boolean is_distribut;

    private BigDecimal paid_money;

    private Boolean deleted;

    private Byte settle_status;

    private BigDecimal all_income;

    private String invoice_email;

    private String invoice_contact;

    private BigDecimal invoice_fee;

    private Integer goods_id;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Boolean getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Boolean order_status) {
        this.order_status = order_status;
    }

    public Boolean getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(Boolean shipping_status) {
        this.shipping_status = shipping_status;
    }

    public Boolean getPay_status() {
        return pay_status;
    }

    public void setPay_status(Boolean pay_status) {
        this.pay_status = pay_status;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getTwon() {
        return twon;
    }

    public void setTwon(Integer twon) {
        this.twon = twon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public BigDecimal getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(BigDecimal shipping_price) {
        this.shipping_price = shipping_price;
    }

    public BigDecimal getUser_money() {
        return user_money;
    }

    public void setUser_money(BigDecimal user_money) {
        this.user_money = user_money;
    }

    public BigDecimal getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(BigDecimal coupon_price) {
        this.coupon_price = coupon_price;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BigDecimal getIntegral_money() {
        return integral_money;
    }

    public void setIntegral_money(BigDecimal integral_money) {
        this.integral_money = integral_money;
    }

    public BigDecimal getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(BigDecimal order_amount) {
        this.order_amount = order_amount;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(Integer shipping_time) {
        this.shipping_time = shipping_time;
    }

    public Integer getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(Integer confirm_time) {
        this.confirm_time = confirm_time;
    }

    public Integer getPay_time() {
        return pay_time;
    }

    public void setPay_time(Integer pay_time) {
        this.pay_time = pay_time;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Integer getProm_id() {
        return prom_id;
    }

    public void setProm_id(Integer prom_id) {
        this.prom_id = prom_id;
    }

    public Byte getProm_type() {
        return prom_type;
    }

    public void setProm_type(Byte prom_type) {
        this.prom_type = prom_type;
    }

    public Short getOrder_prom_id() {
        return order_prom_id;
    }

    public void setOrder_prom_id(Short order_prom_id) {
        this.order_prom_id = order_prom_id;
    }

    public BigDecimal getOrder_prom_amount() {
        return order_prom_amount;
    }

    public void setOrder_prom_amount(BigDecimal order_prom_amount) {
        this.order_prom_amount = order_prom_amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getAdmin_note() {
        return admin_note;
    }

    public void setAdmin_note(String admin_note) {
        this.admin_note = admin_note;
    }

    public String getParent_sn() {
        return parent_sn;
    }

    public void setParent_sn(String parent_sn) {
        this.parent_sn = parent_sn;
    }

    public Boolean getIs_distribut() {
        return is_distribut;
    }

    public void setIs_distribut(Boolean is_distribut) {
        this.is_distribut = is_distribut;
    }

    public BigDecimal getPaid_money() {
        return paid_money;
    }

    public void setPaid_money(BigDecimal paid_money) {
        this.paid_money = paid_money;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Byte getSettle_status() {
        return settle_status;
    }

    public void setSettle_status(Byte settle_status) {
        this.settle_status = settle_status;
    }

    public BigDecimal getAll_income() {
        return all_income;
    }

    public void setAll_income(BigDecimal all_income) {
        this.all_income = all_income;
    }

    public String getInvoice_email() {
        return invoice_email;
    }

    public void setInvoice_email(String invoice_email) {
        this.invoice_email = invoice_email;
    }

    public String getInvoice_contact() {
        return invoice_contact;
    }

    public void setInvoice_contact(String invoice_contact) {
        this.invoice_contact = invoice_contact;
    }

    public BigDecimal getInvoice_fee() {
        return invoice_fee;
    }

    public void setInvoice_fee(BigDecimal invoice_fee) {
        this.invoice_fee = invoice_fee;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }
}