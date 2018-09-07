package com.citytuike.model;

import java.io.Serializable;

public class TpAdApplyMaterial implements Serializable {
    /**
     * 广告id
     */
    private Integer material_id;
    /**
     * 广告类型
     */
    private Integer media_type;
    /**
     * 广告名称
     */
    private String ad_name;
    /**
     * 链接地址
     */
    private String ad_link;
    /**
     * 图片地址
     */
    private String ad_code;
    /**
     * 投放时间
     */
    private Integer start_time;
    /**
     * 结束时间
     */
    private Integer end_time;
    /**
     * 添加人
     */
    private String link_man;
    /**
     * 添加人邮箱
     */
    private String link_email;
    /**
     * 添加人联系电话
     */
    private String link_phone;
    /**
     * 点击量
     */
    private Integer click_count;
    /**
     * 是否显示
     */
    private Integer enabled;
    /**
     * 排序
     */
    private Integer orderby;
    /**
     * 是否开启浏览器新窗口
     */
    private Integer target;
    /**
     * 背景颜色
     */
    private String bgcolor;
    /**
     * 所属用户
     */
    private Integer user_id;
    /**
     * 广告分类
     */
    private Integer cat_id;
    /**
     * 广告描述
     */
    private String description;
    /**
     *
     */
    private Integer add_time;
    /**
     *
     */
    private Integer order_id;

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public Integer getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Integer media_type) {
        this.media_type = media_type;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_link() {
        return ad_link;
    }

    public void setAd_link(String ad_link) {
        this.ad_link = ad_link;
    }

    public String getAd_code() {
        return ad_code;
    }

    public void setAd_code(String ad_code) {
        this.ad_code = ad_code;
    }

    public Integer getStart_time() {
        return start_time;
    }

    public void setStart_time(Integer start_time) {
        this.start_time = start_time;
    }

    public Integer getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Integer end_time) {
        this.end_time = end_time;
    }

    public String getLink_man() {
        return link_man;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public String getLink_email() {
        return link_email;
    }

    public void setLink_email(String link_email) {
        this.link_email = link_email;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public Integer getClick_count() {
        return click_count;
    }

    public void setClick_count(Integer click_count) {
        this.click_count = click_count;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
