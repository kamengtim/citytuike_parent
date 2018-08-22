package com.citytuike.model;

import java.io.Serializable;

public class TpUserAddress implements Serializable{

	/**
	 * 用户地址
	 */
	private static final long serialVersionUID = 4317292147456207802L;
	/**
	 * 表id
	 */
	private Integer address_id;
	/**
	 * 用户id
	 */
	private Integer user_id;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 国家
	 */
	private Integer country;
	/**
	 * 省份
	 */
	private Integer province;
	/**
	 * 城市
	 */
	private Integer city;
	/**
	 * 地区
	 */
	private Integer district;
	/**
	 * 乡镇
	 */
	private Integer twon;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮政编码
	 */
	private String zipcode;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 默认收货地址
	 */
	private Integer is_default;
	/**
	 * 是否自提点
	 */
	private Integer is_pickup;
	public Integer getAddress_id() {
		return address_id;
	}
	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getTwon() {
		return twon;
	}
	public void setTwon(Integer twon) {
		this.twon = twon;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getIs_default() {
		return is_default;
	}
	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}
	public Integer getIs_pickup() {
		return is_pickup;
	}
	public void setIs_pickup(Integer is_pickup) {
		this.is_pickup = is_pickup;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
