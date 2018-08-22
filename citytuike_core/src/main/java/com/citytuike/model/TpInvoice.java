package com.citytuike.model;

import java.io.Serializable;

public class TpInvoice implements Serializable{

	/**
	 * 发票
	 */
	private static final long serialVersionUID = 15898777487924007L;
	/**
	 * 表id
	 */
	private Integer invoice_id;
	/**
	 * 订单id
	 */
	private Integer order_id;
	/**
	 * 用户id
	 */
	private String user_id;
	/**
	 * 0普通发票1电子发票2增值税发票
	 */
	private int invoice_type;
	/**
	 * 发票金额
	 */
	private double invoice_money;
	/**
	 * 发票抬头
	 */
	private String invoice_title;
	/**
	 * 发票内容
	 */
	private String invoice_desc;
	/**
	 * 发票税率
	 */
	private double invoice_rate;
	/**
	 * 纳税人识别号
	 */
	private String taxpayer;
	/**
	 * 发票状态0待开1已开2作废
	 */
	private Integer status;
	/**
	 * 开票时间
	 */
	private Integer atime;
	/**
	 * 创建时间
	 */
	private Integer ctime;
	
	public int getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(int invoice_type) {
		this.invoice_type = invoice_type;
	}
	public Integer getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public double getInvoice_money() {
		return invoice_money;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	public void setInvoice_money(double invoice_money) {
		this.invoice_money = invoice_money;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getInvoice_desc() {
		return invoice_desc;
	}
	public void setInvoice_desc(String invoice_desc) {
		this.invoice_desc = invoice_desc;
	}
	public double getInvoice_rate() {
		return invoice_rate;
	}
	public void setInvoice_rate(double invoice_rate) {
		this.invoice_rate = invoice_rate;
	}
	public String getTaxpayer() {
		return taxpayer;
	}
	public void setTaxpayer(String taxpayer) {
		this.taxpayer = taxpayer;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAtime() {
		return atime;
	}
	public void setAtime(Integer atime) {
		this.atime = atime;
	}
	public Integer getCtime() {
		return ctime;
	}
	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
