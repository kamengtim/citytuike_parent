package com.citytuike.model;

import java.io.Serializable;

public class TpAccountLog<integer> implements Serializable{

	/**
	 * 支付记录
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 表id
	 */
	private Integer log_id;
	/**
	 * 表id
	 */
	private Integer user_id;
	/**
	 * 用户金额
	 */
	private double user_money;
	/**
	 * 冻结金额
	 */
	private double frozen_money;
	/**
	 * 支付积分
	 */
	private Integer pay_points;
	/**
	 * 变动时间
	 */
	private Integer change_time;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 订单编号
	 */
	private String order_sn;
	/**
	 * 订单id
	 */
	private Integer order_id;
	/**
	 * 类型
	 */
	private Integer change_type;
	/**
	 *
	 */
	private Integer second_type;
	/**
	 *
	 */
	private Integer third_type;
	/**
	 *
	 */
	private Integer status;
	/**
	 * 是否删除
	 */
	private Integer is_delete;

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public double getUser_money() {
		return user_money;
	}

	public void setUser_money(double user_money) {
		this.user_money = user_money;
	}

	public double getFrozen_money() {
		return frozen_money;
	}

	public void setFrozen_money(double frozen_money) {
		this.frozen_money = frozen_money;
	}

	public Integer getPay_points() {
		return pay_points;
	}

	public void setPay_points(Integer pay_points) {
		this.pay_points = pay_points;
	}

	public Integer getChange_time() {
		return change_time;
	}

	public void setChange_time(Integer change_time) {
		this.change_time = change_time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getChange_type() {
		return change_type;
	}

	public void setChange_type(Integer change_type) {
		this.change_type = change_type;
	}

	public Integer getSecond_type() {
		return second_type;
	}

	public void setSecond_type(Integer second_type) {
		this.second_type = second_type;
	}

	public Integer getThird_type() {
		return third_type;
	}

	public void setThird_type(Integer third_type) {
		this.third_type = third_type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}
}
