package com.citytuike.model;

import java.io.Serializable;

public class TpApplicationForBusinessCooperation implements Serializable{

	/**
	 * 申请商务合作
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer user_id;
	/**
	 *
	 */
	private String name;
	/**
	 *
	 */
	private String email;
	/**
	 *
	 */
	private Integer province;
	/**
	 *
	 */
	private String city;
	/**
	 *
	 */
	private Integer district;
	/**
	 * 1 信用卡，2 纸巾厂，3附近商家 4 广告投放
	 */
	private Integer type;
	/**
	 * 合作银行ID
	 */
	private String bank_name;
	/**
	 * 合作方式
	 */
	private String ways_of_cooperation;
	/**
	 *公司名称
	 */
	private String company;
	/**
	 * 纸巾日产量
	 */
	private Integer day_number;
	/**
	 * 合作内容
	 */
	private String details;
	/**
	 *
	 */
	private String files;
	/**
	 *
	 */
	private String mobile;
	/**
	 *
	 */
	private String reason;
	/**
	 *
	 */
	private Integer read;
	/**
	 *
	 */
	private String reply_mess;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getWays_of_cooperation() {
		return ways_of_cooperation;
	}

	public void setWays_of_cooperation(String ways_of_cooperation) {
		this.ways_of_cooperation = ways_of_cooperation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getDay_number() {
		return day_number;
	}

	public void setDay_number(Integer day_number) {
		this.day_number = day_number;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public String getReply_mess() {
		return reply_mess;
	}

	public void setReply_mess(String reply_mess) {
		this.reply_mess = reply_mess;
	}
}
