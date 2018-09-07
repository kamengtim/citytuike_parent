package com.citytuike.model;

import java.io.Serializable;

public class TpStatisticsData implements Serializable {
    /**
     * id
     */
    private Integer rec_id;
    /**
     * 统计项目
     */
    private Integer statistics_id;
    /**
     * 统计目标
     */
    private Integer object_id;
    /**
     * 年统计
     */
    private Integer year;
    /**
     * 季度统计
     */
    private Integer quarter;
    /**
     * 月统计
     */
    private Integer month;
    /**
     * 日统计
     */
    private Integer day;
    /**
     * 小时统计
     */
    private Integer hour;
    /**
     * 分钟统计
     */
    private Integer minute;
    /**
     * 整型计数值
     */
    private Integer int_value;
    /**
     * 浮点计数值
     */
    private double double_value;
    /**
     * 创建时间
     */
    private Integer add_time;
    /**
     * 更新时间
     */
    private Integer update_time;

    public Integer getRec_id() {
        return rec_id;
    }

    public void setRec_id(Integer rec_id) {
        this.rec_id = rec_id;
    }

    public Integer getStatistics_id() {
        return statistics_id;
    }

    public void setStatistics_id(Integer statistics_id) {
        this.statistics_id = statistics_id;
    }

    public Integer getObject_id() {
        return object_id;
    }

    public void setObject_id(Integer object_id) {
        this.object_id = object_id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getInt_value() {
        return int_value;
    }

    public void setInt_value(Integer int_value) {
        this.int_value = int_value;
    }

    public double getDouble_value() {
        return double_value;
    }

    public void setDouble_value(double double_value) {
        this.double_value = double_value;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public Integer getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Integer update_time) {
        this.update_time = update_time;
    }
}
