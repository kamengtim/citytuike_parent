package com.citytuike.model;

import java.math.BigDecimal;

public class TpBusinessShare {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.business_type
     * 1为美食2为酒店3为景点4为美容5为汽修6为KTV
     * @mbggenerated
     */
    private Integer businessType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.business_name
     * 商家类型名
     * @mbggenerated
     */
    private String businessName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.name
     * 商家名
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.cus_nums
     * 人均消费
     * @mbggenerated
     */
    private double cusNums;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.business_start_time
     * 营业起始时间
     * @mbggenerated
     */
    private Integer businessStartTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.business_end_time
     * 营业结束时间
     * @mbggenerated
     */
    private Integer businessEndTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.mobile
     * 联系电话
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.xpoint
     * 经度
     * @mbggenerated
     */
    private String xpoint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.ypoint
     * 纬度
     * @mbggenerated
     */
    private String ypoint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.location_area
     * 所在地区
     * @mbggenerated
     */
    private String locationArea;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.address
     * 详细地址
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.tag
     * 标签简介
     * @mbggenerated
     */
    private String tag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.create_time
     * 创建时间
     * @mbggenerated
     */
    private Integer createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.geohash
     * 经纬度编码
     * @mbggenerated
     */
    private String geohash;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.tuijian
     * 1为推荐
     * @mbggenerated
     */
    private Integer tuijian;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.dongjie
     * 金额冻结
     * @mbggenerated
     */
    private double dongjie;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_business_share.flag
     * 1在线2审核中3下架
     * @mbggenerated
     */
    private Integer flag;
    /**
     * logo
     */
    private String logo;
    /**
     * 描述
     */
    private String desc;
    /**
     * 设备
     */
    private String device;
    /**
     * 总评分
     */
    private Integer sum_star;
    /**
     * 评分人数
     */
    private Integer star_number;
    /**
     * 领取优惠券数量
     */
    private Integer give_quan;
    /**
     * 浏览量
     */
    private Integer pv;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getSum_star() {
        return sum_star;
    }

    public void setSum_star(Integer sum_star) {
        this.sum_star = sum_star;
    }

    public Integer getStar_number() {
        return star_number;
    }

    public void setStar_number(Integer star_number) {
        this.star_number = star_number;
    }

    public Integer getGive_quan() {
        return give_quan;
    }

    public void setGive_quan(Integer give_quan) {
        this.give_quan = give_quan;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.id
     *
     * @return the value of tp_business_share.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.id
     *
     * @param id the value for tp_business_share.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.business_type
     *
     * @return the value of tp_business_share.business_type
     *
     * @mbggenerated
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.business_type
     *
     * @param businessType the value for tp_business_share.business_type
     *
     * @mbggenerated
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.business_name
     *
     * @return the value of tp_business_share.business_name
     *
     * @mbggenerated
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.business_name
     *
     * @param businessName the value for tp_business_share.business_name
     *
     * @mbggenerated
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.name
     *
     * @return the value of tp_business_share.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.name
     *
     * @param name the value for tp_business_share.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.cus_nums
     *
     * @return the value of tp_business_share.cus_nums
     *
     * @mbggenerated
     */
    public double getCusNums() {
        return cusNums;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.cus_nums
     *
     * @param cusNums the value for tp_business_share.cus_nums
     *
     * @mbggenerated
     */
    public void setCusNums(double cusNums) {
        this.cusNums = cusNums;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.business_start_time
     *
     * @return the value of tp_business_share.business_start_time
     *
     * @mbggenerated
     */
    public Integer getBusinessStartTime() {
        return businessStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.business_start_time
     *
     * @param businessStartTime the value for tp_business_share.business_start_time
     *
     * @mbggenerated
     */
    public void setBusinessStartTime(Integer businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.business_end_time
     *
     * @return the value of tp_business_share.business_end_time
     *
     * @mbggenerated
     */
    public Integer getBusinessEndTime() {
        return businessEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.business_end_time
     *
     * @param businessEndTime the value for tp_business_share.business_end_time
     *
     * @mbggenerated
     */
    public void setBusinessEndTime(Integer businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.mobile
     *
     * @return the value of tp_business_share.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.mobile
     *
     * @param mobile the value for tp_business_share.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.xpoint
     *
     * @return the value of tp_business_share.xpoint
     *
     * @mbggenerated
     */
    public String getXpoint() {
        return xpoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.xpoint
     *
     * @param xpoint the value for tp_business_share.xpoint
     *
     * @mbggenerated
     */
    public void setXpoint(String xpoint) {
        this.xpoint = xpoint == null ? null : xpoint.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.ypoint
     *
     * @return the value of tp_business_share.ypoint
     *
     * @mbggenerated
     */
    public String getYpoint() {
        return ypoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.ypoint
     *
     * @param ypoint the value for tp_business_share.ypoint
     *
     * @mbggenerated
     */
    public void setYpoint(String ypoint) {
        this.ypoint = ypoint == null ? null : ypoint.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.location_area
     *
     * @return the value of tp_business_share.location_area
     *
     * @mbggenerated
     */
    public String getLocationArea() {
        return locationArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.location_area
     *
     * @param locationArea the value for tp_business_share.location_area
     *
     * @mbggenerated
     */
    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea == null ? null : locationArea.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.address
     *
     * @return the value of tp_business_share.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.address
     *
     * @param address the value for tp_business_share.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.tag
     *
     * @return the value of tp_business_share.tag
     *
     * @mbggenerated
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.tag
     *
     * @param tag the value for tp_business_share.tag
     *
     * @mbggenerated
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.create_time
     *
     * @return the value of tp_business_share.create_time
     *
     * @mbggenerated
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.create_time
     *
     * @param createTime the value for tp_business_share.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.geohash
     *
     * @return the value of tp_business_share.geohash
     *
     * @mbggenerated
     */
    public String getGeohash() {
        return geohash;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.geohash
     *
     * @param geohash the value for tp_business_share.geohash
     *
     * @mbggenerated
     */
    public void setGeohash(String geohash) {
        this.geohash = geohash == null ? null : geohash.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.tuijian
     *
     * @return the value of tp_business_share.tuijian
     *
     * @mbggenerated
     */
    public Integer getTuijian() {
        return tuijian;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.tuijian
     *
     * @param tuijian the value for tp_business_share.tuijian
     *
     * @mbggenerated
     */
    public void setTuijian(Integer tuijian) {
        this.tuijian = tuijian;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.dongjie
     *
     * @return the value of tp_business_share.dongjie
     *
     * @mbggenerated
     */
    public double getDongjie() {
        return dongjie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.dongjie
     *
     * @param dongjie the value for tp_business_share.dongjie
     *
     * @mbggenerated
     */
    public void setDongjie(double dongjie) {
        this.dongjie = dongjie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.user_id
     *
     * @return the value of tp_business_share.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.user_id
     *
     * @param userId the value for tp_business_share.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_business_share.flag
     *
     * @return the value of tp_business_share.flag
     *
     * @mbggenerated
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_business_share.flag
     *
     * @param flag the value for tp_business_share.flag
     *
     * @mbggenerated
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}