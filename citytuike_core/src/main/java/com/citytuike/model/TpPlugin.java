package com.citytuike.model;

import java.io.Serializable;

public class TpPlugin implements Serializable{

	/**
	 * 支付方式
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 插件编码
	 */
	private String code;
	/**
	 * 中文名字
	 */
	private String name;
	/**
	 * 插件的版本
	 */
	private String version;
	/**
	 * 插件作者
	 */
	private String author;
	/**
	 * 配置信息
	 */
	private String config;
	/**
	 * 配置值信息
	 */
	private String config_value;
	/**
	 * 插件描述
	 */
	private String desc;
	/**
	 * 是否启用
	 */
	private Integer status;
	/**
	 * 插件类型 payment支付 login 登陆 shipping物流
	 */
	private String type;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 网银配置信息
	 */
	private String bank_code;
	/**
	 * 使用场景 0PC+手机 1手机 2PC 3APP 4小程序
	 */
	private Integer scene;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getConfig_value() {
		return config_value;
	}

	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}
}
