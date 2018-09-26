package com.citytuike.model;

import java.util.Date;

public class TpTenSecondsActivityConfig {
    private Integer id;

    private Integer hour;

    private Integer hour_num;

    private Integer week;

    private Integer week_num;

    private Integer activity_num;

    private String activity_title;

    private String activity_desc;

    private Date add_time;

    private Byte status;

    private Integer probability;

    private String agent_config;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getHour_num() {
        return hour_num;
    }

    public void setHour_num(Integer hour_num) {
        this.hour_num = hour_num;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getWeek_num() {
        return week_num;
    }

    public void setWeek_num(Integer week_num) {
        this.week_num = week_num;
    }

    public Integer getActivity_num() {
        return activity_num;
    }

    public void setActivity_num(Integer activity_num) {
        this.activity_num = activity_num;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getActivity_desc() {
        return activity_desc;
    }

    public void setActivity_desc(String activity_desc) {
        this.activity_desc = activity_desc;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public String getAgent_config() {
        return agent_config;
    }

    public void setAgent_config(String agent_config) {
        this.agent_config = agent_config;
    }
}