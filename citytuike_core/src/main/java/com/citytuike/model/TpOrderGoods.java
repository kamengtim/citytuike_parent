package com.citytuike.model;

import java.io.Serializable;

public class TpOrderGoods implements Serializable{


	/**
	 * 订单商品
	 */
	private static final long serialVersionUID = 5720476378726613432L;
	/**
	 * id
	 */
	private Integer rec_id;
	/**
	 * 订单id
	 */
	private Integer order_id;
	/**
	 * 商品id
	 */
	private Integer goods_id;
	/**
	 * 商品名称
	 */
	private String goods_name;
	/**
	 * 商品货号
	 */
	private String goods_sn;
	/**
	 * 购买数量
	 */
	private int goods_num;
	/**
	 * 商品实际购买价
	 */
	private double final_price;
	/**
	 * 本店价
	 */
	private double goods_price;
	/**
	 * 商品成本价
	 */
	private double cost_price;
	/**
	 * 会员折扣价
	 */
	private double member_goods_price;
	/**
	 * 购买商品赠送积分
	 */
	private Integer give_integral;
	/**
	 * 商品规格key
	 */
	private String spec_key;
	/**
	 * 规格对应的中文名字
	 */
	private String spec_key_name;
	/**
	 * 条码
	 */
	private String bar_code;
	/**
	 * 是否评价
	 */
	private int is_comment;
	/**
	 * 0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠,4预售
	 */
	private int prom_type;
	/**
	 * 活动id
	 */
	private int prom_id;
	/**
	 * 0未发货，1已发货，2已换货，3已退货
	 */
	private int is_send;
	/**
	 * 发货单ID
	 */
	private int delivery_id;
	/**
	 * 
	 */
	private String sku;
	/**
	 * 
	 */
	private String original_img;
	/**
	 * 商品所属类型id，取值表goods_type的cat_id
	 */
	private int goods_type;
	/**
	 * 购物车类型[0， 默认， 1： 地区与机器]
	 */
	private int type;
	
	public Integer getRec_id() {
		return rec_id;
	}
	public void setRec_id(Integer rec_id) {
		this.rec_id = rec_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_sn() {
		return goods_sn;
	}
	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}
	public int getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}
	public double getFinal_price() {
		return final_price;
	}
	public void setFinal_price(double final_price) {
		this.final_price = final_price;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public double getCost_price() {
		return cost_price;
	}
	public void setCost_price(double cost_price) {
		this.cost_price = cost_price;
	}
	public double getMember_goods_price() {
		return member_goods_price;
	}
	public void setMember_goods_price(double member_goods_price) {
		this.member_goods_price = member_goods_price;
	}
	public Integer getGive_integral() {
		return give_integral;
	}
	public void setGive_integral(Integer give_integral) {
		this.give_integral = give_integral;
	}
	public String getSpec_key() {
		return spec_key;
	}
	public void setSpec_key(String spec_key) {
		this.spec_key = spec_key;
	}
	public String getSpec_key_name() {
		return spec_key_name;
	}
	public void setSpec_key_name(String spec_key_name) {
		this.spec_key_name = spec_key_name;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public int getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(int is_comment) {
		this.is_comment = is_comment;
	}
	public int getProm_type() {
		return prom_type;
	}
	public void setProm_type(int prom_type) {
		this.prom_type = prom_type;
	}
	public int getProm_id() {
		return prom_id;
	}
	public void setProm_id(int prom_id) {
		this.prom_id = prom_id;
	}
	public int getIs_send() {
		return is_send;
	}
	public void setIs_send(int is_send) {
		this.is_send = is_send;
	}
	public int getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(int delivery_id) {
		this.delivery_id = delivery_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getOriginal_img() {
		return original_img;
	}
	public void setOriginal_img(String original_img) {
		this.original_img = original_img;
	}
	public int getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(int goods_type) {
		this.goods_type = goods_type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
