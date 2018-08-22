package com.citytuike.model;

import java.io.Serializable;

public class TpOrderAction implements Serializable{

	/**
	 * 订单变动记录
	 */
	private static final long serialVersionUID = 3684229213478406896L;
	/**
	 * id
	 */
	private Integer action_id;
	/**
	 * 订单id
	 */
	private Integer order_id;
	/**
	 * 操作人 0 为用户操作，其他为管理员id
	 */
	private Integer action_user;
	/**
	 * 订单状态
	 */
	private int order_status;
	/**
	 * 配送状态
	 */
	private int shipping_status;
	/**
	 * 支付状态
	 */
	private int pay_status;
	/**
	 * 操作备注
	 */
	private String action_note;
	/**
	 * 操作时间
	 */
	private int log_time;
	/**
	 * 状态描述
	 */
	private String status_desc;
	public Integer getAction_id() {
		return action_id;
	}
	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getAction_user() {
		return action_user;
	}
	public void setAction_user(Integer action_user) {
		this.action_user = action_user;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public int getShipping_status() {
		return shipping_status;
	}
	public void setShipping_status(int shipping_status) {
		this.shipping_status = shipping_status;
	}
	public int getPay_status() {
		return pay_status;
	}
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}
	public String getAction_note() {
		return action_note;
	}
	public void setAction_note(String action_note) {
		this.action_note = action_note;
	}
	public int getLog_time() {
		return log_time;
	}
	public void setLog_time(int log_time) {
		this.log_time = log_time;
	}
	public String getStatus_desc() {
		return status_desc;
	}
	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
