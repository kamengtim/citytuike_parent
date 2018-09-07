package com.citytuike.model;

import java.math.BigDecimal;

public class TpDeviceQr {
    private Integer qr_id;

    private String scene_str;

    private Integer item_type;

    private Integer item_id;

    private Integer add_time;

    private Integer status;

    private Integer scan_user_id;

    private Integer scan_time;

    private BigDecimal lat;

    private BigDecimal lng;

    public Integer getQr_id() {
        return qr_id;
    }

    public void setQr_id(Integer qr_id) {
        this.qr_id = qr_id;
    }

    public String getScene_str() {
        return scene_str;
    }

    public void setScene_str(String scene_str) {
        this.scene_str = scene_str;
    }

    public Integer getItem_type() {
        return item_type;
    }

    public void setItem_type(Integer item_type) {
        this.item_type = item_type;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScan_user_id() {
        return scan_user_id;
    }

    public void setScan_user_id(Integer scan_user_id) {
        this.scan_user_id = scan_user_id;
    }

    public Integer getScan_time() {
        return scan_time;
    }

    public void setScan_time(Integer scan_time) {
        this.scan_time = scan_time;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }
}