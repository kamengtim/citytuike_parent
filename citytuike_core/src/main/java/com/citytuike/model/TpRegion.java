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
	private Integer id;
	/**
	 * 地区名称
	 */
	private String name;
	/**
	 * 地区等级 分省市县区
	 */
	private Byte level;
	/**
	 * 父id
	 */
	private Integer parent_id;
	/**
	 *
	 */
	private String initials;
	/**
	 * 地区机器数量
	 */
	private Integer num;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
