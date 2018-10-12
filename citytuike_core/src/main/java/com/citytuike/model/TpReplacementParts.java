package com.citytuike.model;

import java.io.Serializable;

public class TpReplacementParts implements Serializable{
    private Integer id;

    private Integer user_id;

    private String name;

    private String reason;

    public TpDevice getTpDevice() {
        return tpDevice;
    }

    public void setTpDevice(TpDevice tpDevice) {
        this.tpDevice = tpDevice;
    }

    private Integer add_time;

    private Integer device_id;

    private String shipping_name;

    private String invoice_no;

    private Integer shipping_time;

    private String address;

    private Integer status;

    private String linkman;

    private String mobile;

    private Integer inspect_status;

    private Integer inspect_time;

    private String refute_reason;

    private Integer refute_time;

    private String refute_imgs;

    private Integer confirm_time;

    private String files;

    private String shipping_files;

    private TpDevice tpDevice;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public Integer getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(Integer shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getInspect_status() {
        return inspect_status;
    }

    public void setInspect_status(Integer inspect_status) {
        this.inspect_status = inspect_status;
    }

    public Integer getInspect_time() {
        return inspect_time;
    }

    public void setInspect_time(Integer inspect_time) {
        this.inspect_time = inspect_time;
    }

    public String getRefute_reason() {
        return refute_reason;
    }

    public void setRefute_reason(String refute_reason) {
        this.refute_reason = refute_reason;
    }

    public Integer getRefute_time() {
        return refute_time;
    }

    public void setRefute_time(Integer refute_time) {
        this.refute_time = refute_time;
    }

    public String getRefute_imgs() {
        return refute_imgs;
    }

    public void setRefute_imgs(String refute_imgs) {
        this.refute_imgs = refute_imgs;
    }

    public Integer getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(Integer confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getShipping_files() {
        return shipping_files;
    }

    public void setShipping_files(String shipping_files) {
        this.shipping_files = shipping_files;
    }
}