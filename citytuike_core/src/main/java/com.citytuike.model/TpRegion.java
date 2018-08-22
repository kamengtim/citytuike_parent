package com.citytuike.model;

import java.io.Serializable;

public class TpRegion implements Serializable{

	/**
	 * 地区表
	 */
	private static final long serialVersionUID = 8047922416881294619L;
	/**
	 * id
	 */
	private int id;
	/**
	 * 地区名称
	 */
	private String name;
	/**
	 * 地区等级 分省市县区
	 */
	private String level;
	/**
	 * 父id
	 */
	private String parent_id;
	/**
	 * 
	 */
	private String initials;
	/**
	 * 地区机器数量
	 */
	private int num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
