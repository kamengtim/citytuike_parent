package com.citytuike.model;

import java.io.Serializable;

public class TpAdCategory implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 申请订单ID
     */
    private Integer apply_id;
    /**
     * 地区ID
     */
    private Integer region_id;
    /**
     * 机器数量
     */
    private Integer num;

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

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
