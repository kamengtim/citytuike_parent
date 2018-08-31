package com.citytuike.model;

public class TpReportList {
    private Integer id;

    private String area;

    private Integer user_id;

    private String address;

    private Integer send_time;

    private Boolean status;

    private Integer han_time;

    private String report_mess;

    private String image;

    private String reply_mess;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSend_time() {
        return send_time;
    }

    public void setSend_time(Integer send_time) {
        this.send_time = send_time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getHan_time() {
        return han_time;
    }

    public void setHan_time(Integer han_time) {
        this.han_time = han_time;
    }

    public String getReport_mess() {
        return report_mess;
    }

    public void setReport_mess(String report_mess) {
        this.report_mess = report_mess;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReply_mess() {
        return reply_mess;
    }

    public void setReply_mess(String reply_mess) {
        this.reply_mess = reply_mess;
    }
}