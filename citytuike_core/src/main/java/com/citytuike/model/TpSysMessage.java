package com.citytuike.model;

import java.io.Serializable;

public class TpSysMessage implements Serializable{

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
	private String send_msg;
	/**
	 *
	 */
	private int send_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSend_msg() {
		return send_msg;
	}

	public void setSend_msg(String send_msg) {
		this.send_msg = send_msg;
	}

	public int getSend_time() {
		return send_time;
	}

	public void setSend_time(int send_time) {
		this.send_time = send_time;
	}
}
