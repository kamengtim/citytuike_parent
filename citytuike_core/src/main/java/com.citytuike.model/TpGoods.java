package com.citytuike.model;

import java.io.Serializable;

public class TpGoods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2593257332252037123L;
	/**
	 * 商品id
	 */
	private Integer goods_id;
	/**
	 * 分类id
	 */
	private int cat_id;
	/**
	 * 扩展分类id
	 */
	private int extend_cat_id;
	/**
	 * 商品编号
	 */
	private String goods_sn;
	/**
	 * 商品名称
	 */
	private String goods_name;
	/**
	 * 点击数
	 */
	private int click_count;
	/**
	 * 品牌id
	 */
	private Integer brand_id;
	/**
	 * 发送场景,1:用户注册,2:找回密码,3:客户下单,4:客户支付,5:商家发货,6:身份验证
	 */
	private int store_count;
	/**
	 * 商品评论数
	 */
	private Integer comment_count;
	/**
	 * 商品重量克为单位
	 */
	private Integer weight;
	/**
	 * 商品体积。单位立方米
	 */
	private double volume;
	/**
	 * 市场价
	 */
	private double market_price;
	/**
	 * 本店价
	 */
	private double shop_price;
	/**
	 * 商品成本价
	 */
	private double cost_price;
	/**
	 * 价格阶梯
	 */
	private String price_ladder;
	/**
	 * 商品关键词
	 */
	private String keywords;
	/**
	 * 商品简单描述
	 */
	private String goods_remark;
	/**
	 * 商品详细描述
	 */
	private String goods_content;
	/**
	 * 手机端商品详情
	 */
	private String mobile_content;
	/**
	 * 商品上传原始图
	 */
	private String original_img;
	/**
	 * 是否为虚拟商品 1是，0否
	 */
	private Integer is_virtual;
	/**
	 * 虚拟商品有效期
	 */
	private Integer virtual_indate;
	/**
	 * 虚拟商品购买上限
	 */
	private Integer virtual_limit;
	/**
	 * 是否允许过期退款， 1是，0否
	 */
	private Integer virtual_refund;
	/**
	 * 是否上架
	 */
	private Integer is_on_sale;
	/**
	 * 是否包邮0否1是
	 */
	private Integer is_free_shipping;
	/**
	 * 商品上架时间
	 */
	private Integer on_time;
	/**
	 * 商品排序
	 */
	private Integer sort;
	/**
	 * 是否推荐
	 */
	private Integer is_recommend;
	/**
	 * 是否新品
	 */
	private Integer is_new;
	/**
	 * 是否热卖
	 */
	private Integer is_hot;
	/**
	 * 最后更新时间
	 */
	private Integer last_update;
	/**
	 * 商品所属类型id，取值表goods_type的cat_id
	 */
	private Integer goods_type;
	/**
	 * 商品规格类型，取值表goods_type的cat_id
	 */
	private Integer spec_type;
	/**
	 * 购买商品赠送积分
	 */
	private Integer give_integral;
	/**
	 * 积分兑换：0不参与积分兑换，积分和现金的兑换比例见后台配置
	 */
	private Integer exchange_integral;
	/**
	 * 供货商ID
	 */
	private Integer suppliers_id;
	/**
	 * 商品销量
	 */
	private Integer sales_sum;
	/**
	 * 0默认1抢购2团购3优惠促销4预售5虚拟(5其实没用)6拼团
	 */
	private Integer prom_type;
	/**
	 * 优惠活动id
	 */
	private Integer prom_id;
	/**
	 * 佣金用于分销分成
	 */
	private double commission;
	/**
	 * spu
	 */
	private Integer spu;
	/**
	 * sku
	 */
	private Integer sku;
	/**
	 * 运费模板ID
	 */
	private Integer template_id;
	/**
	 * 视频
	 */
	private Integer video;
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
	public int getExtend_cat_id() {
		return extend_cat_id;
	}
	public void setExtend_cat_id(int extend_cat_id) {
		this.extend_cat_id = extend_cat_id;
	}
	public String getGoods_sn() {
		return goods_sn;
	}
	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public int getClick_count() {
		return click_count;
	}
	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}
	public Integer getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}
	public int getStore_count() {
		return store_count;
	}
	public void setStore_count(int store_count) {
		this.store_count = store_count;
	}
	public Integer getComment_count() {
		return comment_count;
	}
	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getMarket_price() {
		return market_price;
	}
	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}
	public double getShop_price() {
		return shop_price;
	}
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}
	public double getCost_price() {
		return cost_price;
	}
	public void setCost_price(double cost_price) {
		this.cost_price = cost_price;
	}
	public String getPrice_ladder() {
		return price_ladder;
	}
	public void setPrice_ladder(String price_ladder) {
		this.price_ladder = price_ladder;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getGoods_remark() {
		return goods_remark;
	}
	public void setGoods_remark(String goods_remark) {
		this.goods_remark = goods_remark;
	}
	public String getGoods_content() {
		return goods_content;
	}
	public void setGoods_content(String goods_content) {
		this.goods_content = goods_content;
	}
	public String getMobile_content() {
		return mobile_content;
	}
	public void setMobile_content(String mobile_content) {
		this.mobile_content = mobile_content;
	}
	public String getOriginal_img() {
		return original_img;
	}
	public void setOriginal_img(String original_img) {
		this.original_img = original_img;
	}
	public Integer getIs_virtual() {
		return is_virtual;
	}
	public void setIs_virtual(Integer is_virtual) {
		this.is_virtual = is_virtual;
	}
	public Integer getVirtual_indate() {
		return virtual_indate;
	}
	public void setVirtual_indate(Integer virtual_indate) {
		this.virtual_indate = virtual_indate;
	}
	public Integer getVirtual_limit() {
		return virtual_limit;
	}
	public void setVirtual_limit(Integer virtual_limit) {
		this.virtual_limit = virtual_limit;
	}
	public Integer getVirtual_refund() {
		return virtual_refund;
	}
	public void setVirtual_refund(Integer virtual_refund) {
		this.virtual_refund = virtual_refund;
	}
	public Integer getIs_on_sale() {
		return is_on_sale;
	}
	public void setIs_on_sale(Integer is_on_sale) {
		this.is_on_sale = is_on_sale;
	}
	public Integer getIs_free_shipping() {
		return is_free_shipping;
	}
	public void setIs_free_shipping(Integer is_free_shipping) {
		this.is_free_shipping = is_free_shipping;
	}
	public Integer getOn_time() {
		return on_time;
	}
	public void setOn_time(Integer on_time) {
		this.on_time = on_time;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIs_recommend() {
		return is_recommend;
	}
	public void setIs_recommend(Integer is_recommend) {
		this.is_recommend = is_recommend;
	}
	public Integer getIs_new() {
		return is_new;
	}
	public void setIs_new(Integer is_new) {
		this.is_new = is_new;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public Integer getLast_update() {
		return last_update;
	}
	public void setLast_update(Integer last_update) {
		this.last_update = last_update;
	}
	public Integer getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(Integer goods_type) {
		this.goods_type = goods_type;
	}
	public Integer getSpec_type() {
		return spec_type;
	}
	public void setSpec_type(Integer spec_type) {
		this.spec_type = spec_type;
	}
	public Integer getGive_integral() {
		return give_integral;
	}
	public void setGive_integral(Integer give_integral) {
		this.give_integral = give_integral;
	}
	public Integer getExchange_integral() {
		return exchange_integral;
	}
	public void setExchange_integral(Integer exchange_integral) {
		this.exchange_integral = exchange_integral;
	}
	public Integer getSuppliers_id() {
		return suppliers_id;
	}
	public void setSuppliers_id(Integer suppliers_id) {
		this.suppliers_id = suppliers_id;
	}
	public Integer getSales_sum() {
		return sales_sum;
	}
	public void setSales_sum(Integer sales_sum) {
		this.sales_sum = sales_sum;
	}
	public Integer getProm_type() {
		return prom_type;
	}
	public void setProm_type(Integer prom_type) {
		this.prom_type = prom_type;
	}
	public Integer getProm_id() {
		return prom_id;
	}
	public void setProm_id(Integer prom_id) {
		this.prom_id = prom_id;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public Integer getSpu() {
		return spu;
	}
	public void setSpu(Integer spu) {
		this.spu = spu;
	}
	public Integer getSku() {
		return sku;
	}
	public void setSku(Integer sku) {
		this.sku = sku;
	}
	public Integer getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Integer template_id) {
		this.template_id = template_id;
	}
	public Integer getVideo() {
		return video;
	}
	public void setVideo(Integer video) {
		this.video = video;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
