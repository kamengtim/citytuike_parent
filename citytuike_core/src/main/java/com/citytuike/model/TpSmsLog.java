package com.citytuike.model;

import java.io.Serializable;

public class TpSmsLog implements Serializable{

	/**
	 * 验证码记录表
	 */
	private static final long serialVersionUID = -5451266569853979384L;
	/**
	 * id
	 */
	private int id;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 缓存id
	 */
	private String session_id;
	/**
	 * 发送时间
	 */
	private int add_time;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 使用状态0未使用1已使用
	 */
	private int status;
	/**
	 * 内容
	 */
	private String msg;
	/**
	 * 发送场景,1:用户注册,2:找回密码,3:客户下单,4:客户支付,5:商家发货,6:身份验证
	 */
	private int scene;
	/**
	 * 发送短信异常内容
	 */
	private String error_msg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public int getAdd_time() {
		return add_time;
	}

	public void setAdd_time(int add_time) {
		this.add_time = add_time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
}
