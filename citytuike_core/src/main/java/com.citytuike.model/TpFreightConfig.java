package com.citytuike.model;

import java.io.Serializable;

public class TpFreightConfig implements Serializable{

	/**
	 * 运费模板
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 表id
	 */
	private Integer config_id;
	/**
	 * 首(重：体积：件）
	 */
	private double first_unit;
	/**
	 * 首(重：体积：件）运费
	 */
	private double first_money;
	/**
	 * 继续加（件：重量：体积）区间
	 */
	private double continue_unit;
	/**
	 * 继续加（件：重量：体积）的运费
	 */
	private double continue_money;
	/**
	 * 运费模板ID
	 */
	private Integer template_id;
	/**
	 * 是否是默认运费配置.0不是，1是
	 */
	private Integer is_default;
	
	public Integer getConfig_id() {
		return config_id;
	}
	public void setConfig_id(Integer config_id) {
		this.config_id = config_id;
	}
	public double getFirst_unit() {
		return first_unit;
	}
	public void setFirst_unit(double first_unit) {
		this.first_unit = first_unit;
	}
	public double getFirst_money() {
		return first_money;
	}
	public void setFirst_money(double first_money) {
		this.first_money = first_money;
	}
	public double getContinue_unit() {
		return continue_unit;
	}
	public void setContinue_unit(double continue_unit) {
		this.continue_unit = continue_unit;
	}
	public double getContinue_money() {
		return continue_money;
	}
	public void setContinue_money(double continue_money) {
		this.continue_money = continue_money;
	}
	public Integer getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Integer template_id) {
		this.template_id = template_id;
	}
	public Integer getIs_default() {
		return is_default;
	}
	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
