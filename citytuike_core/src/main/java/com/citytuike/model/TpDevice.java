package com.citytuike.model;

import java.math.BigDecimal;

public class TpDevice {
    private Integer id;

    private String device_sn;

    private Integer ad_position_id;

    private String qrscene;

    private String iot_id;

    private String product_key;

    private String device_name;

    private String device_secret;

    private Integer add_time;

    private Integer user_id;

    private Integer paper_number;

    private Integer paper_send;

    private Integer paper_inventory;

    private BigDecimal income;

    private BigDecimal one_income;

    private Integer capacity;

    private Integer active_time;

    private Boolean no_send;

    private Boolean open_status;

    private Boolean online;

    private String landmark_picture;

    private Integer province;

    private Integer city;

    private Integer district;

    private Byte is_active;

    private Integer order_id;

    private Integer goods_id;

    private Integer distribution_time;

    private String address;

    private Double longitude;

    private Double latitude;

    private String active_code;

    private String shipping_sn;

    public int isLack_paper() {
        return lack_paper;
    }

    public void setLack_paper(int lack_paper) {
        this.lack_paper = lack_paper;
    }

    private String shipping_name;

    private Integer today_num;

    private Integer today_time;

    private int lack_paper;

    private int run_status;

    public int getRun_status() {
        return run_status;
    }

    public void setRun_status(int run_status) {
        this.run_status = run_status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public Integer getAd_position_id() {
        return ad_position_id;
    }

    public void setAd_position_id(Integer ad_position_id) {
        this.ad_position_id = ad_position_id;
    }

    public String getQrscene() {
        return qrscene;
    }

    public void setQrscene(String qrscene) {
        this.qrscene = qrscene;
    }

    public String getIot_id() {
        return iot_id;
    }

    public void setIot_id(String iot_id) {
        this.iot_id = iot_id;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_secret() {
        return device_secret;
    }

    public void setDevice_secret(String device_secret) {
        this.device_secret = device_secret;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPaper_number() {
        return paper_number;
    }

    public void setPaper_number(Integer paper_number) {
        this.paper_number = paper_number;
    }

    public Integer getPaper_send() {
        return paper_send;
    }

    public void setPaper_send(Integer paper_send) {
        this.paper_send = paper_send;
    }

    public Integer getPaper_inventory() {
        return paper_inventory;
    }

    public void setPaper_inventory(Integer paper_inventory) {
        this.paper_inventory = paper_inventory;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getOne_income() {
        return one_income;
    }

    public void setOne_income(BigDecimal one_income) {
        this.one_income = one_income;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getActive_time() {
        return active_time;
    }

    public void setActive_time(Integer active_time) {
        this.active_time = active_time;
    }

    public Boolean getNo_send() {
        return no_send;
    }

    public void setNo_send(Boolean no_send) {
        this.no_send = no_send;
    }

    public Boolean getOpen_status() {
        return open_status;
    }

    public void setOpen_status(Boolean open_status) {
        this.open_status = open_status;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getLandmark_picture() {
        return landmark_picture;
    }

    public void setLandmark_picture(String landmark_picture) {
        this.landmark_picture = landmark_picture;
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

    public Byte getIs_active() {
        return is_active;
    }

    public void setIs_active(Byte is_active) {
        this.is_active = is_active;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getDistribution_time() {
        return distribution_time;
    }

    public void setDistribution_time(Integer distribution_time) {
        this.distribution_time = distribution_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getActive_code() {
        return active_code;
    }

    public void setActive_code(String active_code) {
        this.active_code = active_code;
    }

    public String getShipping_sn() {
        return shipping_sn;
    }

    public void setShipping_sn(String shipping_sn) {
        this.shipping_sn = shipping_sn;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public Integer getToday_num() {
        return today_num;
    }

    public void setToday_num(Integer today_num) {
        this.today_num = today_num;
    }

    public Integer getToday_time() {
        return today_time;
    }

    public void setToday_time(Integer today_time) {
        this.today_time = today_time;
    }
}