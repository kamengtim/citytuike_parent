package com.citytuike.model;

import java.io.Serializable;

public class TpUsers  implements Serializable{
	
	/**
	 * 用户表
	 */
	private static final long serialVersionUID = -2865265431959685551L;
	/**
	 * 表id
	 */
	private Integer user_id;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 支付密码
	 */
	private String paypwd;
	/**
	 * 0 保密 1 男 2 女
	 */
	private Integer sex;
	/**
	 * 生日
	 */
	private int birthday;
	/**
	 * 用户金额
	 */
	private double user_money;
	/**
	 * 冻结金额
	 */
	private double frozen_money;
	/**
	 * 累积分佣金额
	 */
	private double distribut_money;
	/**
	 * 用户下线总数
	 */
	private int underling_number;
	/**
	 * 消费积分
	 */
	private int pay_points;
	/**
	 * 默认收货地址
	 */
	private int address_id;
	/**
	 * 注册时间
	 */
	private int reg_time;
	/**
	 * 最后登录时间
	 */
	private int last_login;
	/**
	 * 最后登录ip
	 */
	private String last_ip;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 是否验证手机
	 */
	private Integer mobile_validated;
	/**
	 * 第三方来源 wx weibo alipay
	 */
	private String oauth;
	/**
	 * 公众号用户唯一标示
	 */
	private String openid;
	/**
	 * 公众号唯一标示
	 */
	private String unionid;
	/**
	 * 头像
	 */
	private String head_pic;
	/**
	 * 省份
	 */
	private int province;
	/**
	 * 市区
	 */
	private int city;
	/**
	 * 县
	 */
	private int district;
	/**
	 * 是否验证电子邮箱
	 */
	private Integer email_validated;
	/**
	 * 微信头像
	 */
	private String nickname;
	/**
	 * 会员等级
	 */
	private Integer level;
	/**
	 * 会员折扣，默认1不享受
	 */
	private double discount;
	/**
	 * 消费累计额度
	 */
	private double total_amount;
	/**
	 * 是否被锁定冻结
	 */
	private Integer is_lock;
	/**
	 * 是否为分销商 0 否 1 是
	 */
	private Integer is_distribut;
	/**
	 * 第一个上级
	 */
	private int first_leader;
	/**
	 * 第二个上级
	 */
	private int second_leader;
	/**
	 * 第三个上级
	 */
	private int third_leader;
	/**
	 * 用于app 授权类似于session_id
	 */
	private String token;
	/**
	 * 消息掩码
	 */
	private Integer message_mask;
	/**
	 * 推送id
	 */
	private String push_id;
	/**
	 * 分销商等级
	 */
	private Integer distribut_level;
	/**
	 * 是否为VIP ：0不是，1是
	 */
	private Integer is_vip;
	/**
	 * 邀请码
	 */
	private String invite_code;
	/**
	 * 
	 */
	private String relation;
	/**
	 * 销售数量
	 */
	private String sale_number;
	/**
	 * 
	 */
	private String parent_id;
	/**
	 * 微信号
	 */
	private String wechat;
	/**
	 * 
	 */
	private String wechat_qrcode;
	/**
	 * 是否已设置密码
	 */
	private String set_pass;
	/**
	 * 
	 */
	private String im_id;
	/**
	 * 
	 */
	private String im_pwd;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPaypwd() {
		return paypwd;
	}
	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public int getBirthday() {
		return birthday;
	}
	public void setBirthday(int birthday) {
		this.birthday = birthday;
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
	public double getDistribut_money() {
		return distribut_money;
	}
	public void setDistribut_money(double distribut_money) {
		this.distribut_money = distribut_money;
	}
	public int getUnderling_number() {
		return underling_number;
	}
	public void setUnderling_number(int underling_number) {
		this.underling_number = underling_number;
	}
	public int getPay_points() {
		return pay_points;
	}
	public void setPay_points(int pay_points) {
		this.pay_points = pay_points;
	}
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public int getReg_time() {
		return reg_time;
	}
	public void setReg_time(int reg_time) {
		this.reg_time = reg_time;
	}
	public int getLast_login() {
		return last_login;
	}
	public void setLast_login(int last_login) {
		this.last_login = last_login;
	}
	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getMobile_validated() {
		return mobile_validated;
	}
	public void setMobile_validated(Integer mobile_validated) {
		this.mobile_validated = mobile_validated;
	}
	public String getOauth() {
		return oauth;
	}
	public void setOauth(String oauth) {
		this.oauth = oauth;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
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
	public Integer getEmail_validated() {
		return email_validated;
	}
	public void setEmail_validated(Integer email_validated) {
		this.email_validated = email_validated;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public Integer getIs_lock() {
		return is_lock;
	}
	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}
	public Integer getIs_distribut() {
		return is_distribut;
	}
	public void setIs_distribut(Integer is_distribut) {
		this.is_distribut = is_distribut;
	}
	public int getFirst_leader() {
		return first_leader;
	}
	public void setFirst_leader(int first_leader) {
		this.first_leader = first_leader;
	}
	public int getSecond_leader() {
		return second_leader;
	}
	public void setSecond_leader(int second_leader) {
		this.second_leader = second_leader;
	}
	public int getThird_leader() {
		return third_leader;
	}
	public void setThird_leader(int third_leader) {
		this.third_leader = third_leader;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getMessage_mask() {
		return message_mask;
	}
	public void setMessage_mask(Integer message_mask) {
		this.message_mask = message_mask;
	}
	public String getPush_id() {
		return push_id;
	}
	public void setPush_id(String push_id) {
		this.push_id = push_id;
	}
	public Integer getDistribut_level() {
		return distribut_level;
	}
	public void setDistribut_level(Integer distribut_level) {
		this.distribut_level = distribut_level;
	}
	public Integer getIs_vip() {
		return is_vip;
	}
	public void setIs_vip(Integer is_vip) {
		this.is_vip = is_vip;
	}
	public String getInvite_code() {
		return invite_code;
	}
	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getSale_number() {
		return sale_number;
	}
	public void setSale_number(String sale_number) {
		this.sale_number = sale_number;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getWechat_qrcode() {
		return wechat_qrcode;
	}
	public void setWechat_qrcode(String wechat_qrcode) {
		this.wechat_qrcode = wechat_qrcode;
	}
	public String getSet_pass() {
		return set_pass;
	}
	public void setSet_pass(String set_pass) {
		this.set_pass = set_pass;
	}
	public String getIm_id() {
		return im_id;
	}
	public void setIm_id(String im_id) {
		this.im_id = im_id;
	}
	public String getIm_pwd() {
		return im_pwd;
	}
	public void setIm_pwd(String im_pwd) {
		this.im_pwd = im_pwd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
