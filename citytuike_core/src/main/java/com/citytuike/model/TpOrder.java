package com.citytuike.model;

import java.io.Serializable;

public class TpOrder implements Serializable{

	/**
	 * 订单表
	 */
	private static final long serialVersionUID = -5822369884481186693L;
	/**
	 * id
	 */
	private Integer order_id;
	/**
	 * 订单编号
	 */
	private String order_sn;
	/**
	 * 用户id
	 */
	private Integer user_id;
	/**
	 * 订单状态
	 */
	private int order_status;
	/**
	 * 发货状态
	 */
	private int shipping_status;
	/**
	 * 支付状态
	 */
	private int pay_status;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 国家
	 */
	private int country;
	/**
	 * 省份
	 */
	private int province;
	/**
	 * 省份
	 */
	private int city;
	/**
	 * 县区
	 */
	private int district;
	/**
	 * 乡镇
	 */
	private int twon;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮政编码
	 */
	private String zipcode;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 物流code
	 */
	private String shipping_code;
	/**
	 * 物流名称
	 */
	private String shipping_name;
	/**
	 * 支付code
	 */
	private String pay_code;
	/**
	 * 支付方式名称
	 */
	private String pay_name;
	/**
	 * 发票抬头
	 */
	private String invoice_title;
	/**
	 * 纳税人识别号
	 */
	private String taxpayer;
	/**
	 * 商品总价
	 */
	private double goods_price;
	/**
	 * 邮费
	 */
	private double shipping_price;
	/**
	 * 使用余额
	 */
	private double user_money;
	/**
	 * 优惠券抵扣
	 */
	private double coupon_price;
	/**
	 * 使用积分
	 */
	private int integral;
	/**
	 * 使用积分抵多少钱
	 */
	private int integral_money;
	/**
	 * 应付款金额
	 */
	private double order_amount;
	/**
	 * 订单总价
	 */
	private double total_amount;
	/**
	 * 下单时间
	 */
	private int add_time;
	/**
	 * 收货确认时间
	 */
	private int confirm_time;
	/**
	 * 支付时间
	 */
	private int pay_time;
	/**
	 * 第三方平台交易流水号
	 */
	private String transaction_id;
	/**
	 * 活动ID
	 */
	private int prom_id;
	/**
	 * 订单类型：0普通订单4预售订单5虚拟订单6拼团订单
	 */
	private int prom_type;
	/**
	 * 活动id
	 */
	private int order_prom_id;
	/**
	 * 活动优惠金额
	 */
	private double order_prom_amount;
	/**
	 * 价格调整
	 */
	private double discount;
	/**
	 * 用户备注
	 */
	private String user_note;
	/**
	 * 管理员备注
	 */
	private String admin_note;
	/**
	 * 父单单号
	 */
	private String parent_sn;
	/**
	 * 是否已分成0未分成1已分成
	 */
	private int is_distribut;
	/**
	 * 订金
	 */
	private double paid_money;
	/**
	 * 用户假删除标识,1:删除,0未删除
	 */
	private int deleted;
	/**
	 * 0:未结算，1：已冻结，2：已完成
	 */
	private int settle_status;
	/**
	 * 
	 */
	private double all_income;
	/**
	 * 发票邮箱
	 */
	private String invoice_email;
	/**
	 * 发票联系方式
	 */
	private String invoice_contact;
	/**
	 * 发票费用
	 */
	private double invoice_fee;
	/**
	 * 是否已退货
	 */
	private int is_return;
	
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public int getDistrict() {
		return district;
	}
	public void setDistrict(int district) {
		this.district = district;
	}
	public int getTwon() {
		return twon;
	}
	public void setTwon(int twon) {
		this.twon = twon;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShipping_code() {
		return shipping_code;
	}
	public void setShipping_code(String shipping_code) {
		this.shipping_code = shipping_code;
	}
	public String getShipping_name() {
		return shipping_name;
	}
	public void setShipping_name(String shipping_name) {
		this.shipping_name = shipping_name;
	}
	public String getPay_code() {
		return pay_code;
	}
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	public String getPay_name() {
		return pay_name;
	}
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getTaxpayer() {
		return taxpayer;
	}
	public void setTaxpayer(String taxpayer) {
		this.taxpayer = taxpayer;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public double getShipping_price() {
		return shipping_price;
	}
	public void setShipping_price(double shipping_price) {
		this.shipping_price = shipping_price;
	}
	public double getUser_money() {
		return user_money;
	}
	public void setUser_money(double user_money) {
		this.user_money = user_money;
	}
	public double getCoupon_price() {
		return coupon_price;
	}
	public void setCoupon_price(double coupon_price) {
		this.coupon_price = coupon_price;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getIntegral_money() {
		return integral_money;
	}
	public void setIntegral_money(int integral_money) {
		this.integral_money = integral_money;
	}
	public double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
	}
	public int getAdd_time() {
		return add_time;
	}
	public void setAdd_time(int add_time) {
		this.add_time = add_time;
	}
	public int getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(int confirm_time) {
		this.confirm_time = confirm_time;
	}
	public int getPay_time() {
		return pay_time;
	}
	public void setPay_time(int pay_time) {
		this.pay_time = pay_time;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public int getProm_id() {
		return prom_id;
	}
	public void setProm_id(int prom_id) {
		this.prom_id = prom_id;
	}
	public int getProm_type() {
		return prom_type;
	}
	public void setProm_type(int prom_type) {
		this.prom_type = prom_type;
	}
	public int getOrder_prom_id() {
		return order_prom_id;
	}
	public void setOrder_prom_id(int order_prom_id) {
		this.order_prom_id = order_prom_id;
	}
	public double getOrder_prom_amount() {
		return order_prom_amount;
	}
	public void setOrder_prom_amount(double order_prom_amount) {
		this.order_prom_amount = order_prom_amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getUser_note() {
		return user_note;
	}
	public void setUser_note(String user_note) {
		this.user_note = user_note;
	}
	public String getAdmin_note() {
		return admin_note;
	}
	public void setAdmin_note(String admin_note) {
		this.admin_note = admin_note;
	}
	public String getParent_sn() {
		return parent_sn;
	}
	public void setParent_sn(String parent_sn) {
		this.parent_sn = parent_sn;
	}
	public int getIs_distribut() {
		return is_distribut;
	}
	public void setIs_distribut(int is_distribut) {
		this.is_distribut = is_distribut;
	}
	public double getPaid_money() {
		return paid_money;
	}
	public void setPaid_money(double paid_money) {
		this.paid_money = paid_money;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public int getSettle_status() {
		return settle_status;
	}
	public void setSettle_status(int settle_status) {
		this.settle_status = settle_status;
	}
	public double getAll_income() {
		return all_income;
	}
	public void setAll_income(double all_income) {
		this.all_income = all_income;
	}
	public String getInvoice_email() {
		return invoice_email;
	}
	public void setInvoice_email(String invoice_email) {
		this.invoice_email = invoice_email;
	}
	public String getInvoice_contact() {
		return invoice_contact;
	}
	public void setInvoice_contact(String invoice_contact) {
		this.invoice_contact = invoice_contact;
	}
	public double getInvoice_fee() {
		return invoice_fee;
	}
	public void setInvoice_fee(double invoice_fee) {
		this.invoice_fee = invoice_fee;
	}
	public int getIs_return() {
		return is_return;
	}
	public void setIs_return(int is_return) {
		this.is_return = is_return;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
