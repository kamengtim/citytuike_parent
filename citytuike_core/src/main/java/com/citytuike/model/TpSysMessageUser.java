package com.citytuike.model;

import java.io.Serializable;

public class TpSysMessageUser implements Serializable{

	/**
	 * 消息板块记录
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 *
	 */
	private Integer user_id;
	/**
	 *
	 */
	private Integer sys_msg_id;
	/**
	 *
	 */
	private Integer status;
	/**
	 *
	 */
	private int create_time;

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

	public Integer getSys_msg_id() {
		return sys_msg_id;
	}

	public void setSys_msg_id(Integer sys_msg_id) {
		this.sys_msg_id = sys_msg_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getCreate_time() {
		return create_time;
	}

	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
}
