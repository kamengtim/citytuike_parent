package com.citytuike.model;

import java.io.Serializable;

public class TpAdTrade implements Serializable {
    /**
     * id
     */
    private Integer trade_id;
    /**
     * 上级id
     */
    private Integer parent_id;
    /**
     *
     */
    private String name;

    public Integer getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Integer trade_id) {
        this.trade_id = trade_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
