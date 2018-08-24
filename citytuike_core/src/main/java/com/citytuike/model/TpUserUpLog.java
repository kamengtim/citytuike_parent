package com.citytuike.model;

import java.io.Serializable;

public class TpUserUpLog implements Serializable{

	/**
	 * 升级记录表
	 */
	private static final long serialVersionUID = -5451266569853979384L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 用户Id
	 */
	private Integer user_id;
	/**
	 * 创建时间
	 */
	private Integer add_time;
	/**
	 * 等级
	 */
	private Integer level;

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

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
