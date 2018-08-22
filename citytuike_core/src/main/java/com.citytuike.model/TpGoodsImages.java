package com.citytuike.model;

import java.io.Serializable;

public class TpGoodsImages implements Serializable{

	/**
	 * 验证码记录表
	 */
	private static final long serialVersionUID = -5451266569853979384L;
	/**
	 * id
	 */
	private int img_id;
	/**
	 * 商品ID
	 */
	private int goods_id;
	/**
	 * 图片url
	 */
	private String image_url;
	
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
