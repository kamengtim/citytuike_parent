package com.citytuike.model;

import java.io.Serializable;

public class TpGoodsCollect implements Serializable{

	/**
	 * 商品收藏表
	 */
	private static final long serialVersionUID = -5451266569853979384L;
	/**
	 * id
	 */
	private int collect_id;
	/**
	 * 用户id
	 */
	private Integer user_id;
	/**
	 * 商品id
	 */
	private Integer goods_id;
	/**
	 * 添加时间
	 */
	private int add_time;
	public int getCollect_id() {
		return collect_id;
	}
	public void setCollect_id(int collect_id) {
		this.collect_id = collect_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public int getAdd_time() {
		return add_time;
	}
	public void setAdd_time(int add_time) {
		this.add_time = add_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
