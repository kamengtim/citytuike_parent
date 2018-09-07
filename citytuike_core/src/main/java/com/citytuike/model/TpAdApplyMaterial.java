package com.citytuike.model;

import java.io.Serializable;

public class TpAdApplyMaterial implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 申请ID
     */
    private Integer apply_id;
    /**
     * 广告材料地址
     */
    private String url;
    /**
     * 显示（1显示|0不显示）
     */
    private Integer display;
    /**
     * 排序
     */
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApply_id() {
        return apply_id;
    }

    public void setApply_id(Integer apply_id) {
        this.apply_id = apply_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
