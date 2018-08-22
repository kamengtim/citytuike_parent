package com.citytuike.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.model.TpFreightConfig;
import com.citytuike.model.TpGoods;
import com.citytuike.model.TpInvoice;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpOrderAction;
import com.citytuike.model.TpOrderGoods;
import com.citytuike.model.TpUserAddress;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpGoodsService;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Util;

@Controller
@RequestMapping("api/Cart")
public class CartController {
	
	@Autowired
	private TpUsersService tpUsersService;
	@Autowired
	private TpGoodsService tpGoodsService;
	@Autowired
	private TpOrderService tpOrderService;
	/**
	 * @param model
	 * @param id
	 * @param p
	 * @return
	 * 商品列表
	 */
	@RequestMapping(value="/ajaxGoodsList",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String ajaxGoodsList(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String address_id,
			@RequestParam(required=false) String invoice_title,
			@RequestParam(required=false) String taxpayer,
			@RequestParam(required=false) String coupon_id,
			@RequestParam(required=false) String pay_points,
			@RequestParam(required=false) String user_money,
			@RequestParam(required=false) String user_note,
			@RequestParam(required=true) String goods_id,
			@RequestParam(required=true) String goods_num,
			@RequestParam(required=false) String item_id,
			@RequestParam(required=false) String action,
			@RequestParam(required=false) String payPwd,
			@RequestParam(required=false) String act,
			@RequestParam(required=false) String invoice_email,
			@RequestParam(required=false) String invoice_contact,
			@RequestParam(required=false) String is_invoice,
			@RequestParam(required=false) String shipping_id,
			@RequestParam(required=false) String type){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpOrder tpOrder = new TpOrder();
		TpGoods tpGoods = tpGoodsService.findById(Integer.parseInt(goods_id));
		double freightTotal = 0.00;
		double invoiceTotal = 0.00;
		double goodsTotal = 0.00;
		double orderTotal = 0.00;
		if (null != tpGoods) {
			String orderno = new Date().getTime()+ Util.generateString(4);
			System.out.println("订单编号为:" + orderno);
			tpOrder.setOrder_sn(orderno);
			tpOrder.setUser_id(tpUsers.getUser_id());
			tpOrder.setOrder_status(0);
			TpUserAddress tpUserAddress = tpUsersService.findUserAddresById(Integer.parseInt(address_id));
			if (null == tpUserAddress) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "请先填写地址!");
				return jsonObj.toString();
			}
			tpOrder.setConsignee(tpUserAddress.getConsignee());
			tpOrder.setCountry(tpUserAddress.getCountry());
			tpOrder.setProvince(tpUserAddress.getProvince());
			tpOrder.setCity(tpUserAddress.getCity());
			tpOrder.setDistrict(tpUserAddress.getDistrict());
			tpOrder.setTwon(tpUserAddress.getTwon());
			tpOrder.setAddress(tpUserAddress.getAddress());
			tpOrder.setZipcode(tpUserAddress.getZipcode());
			tpOrder.setMobile(tpUserAddress.getMobile());
			tpOrder.setEmail(tpUserAddress.getEmail());
			goodsTotal = tpGoods.getShop_price() * Integer.parseInt(goods_num);
			if (null != tpGoods.getIs_free_shipping() && tpGoods.getIs_free_shipping() == 0) {
				if (null != tpGoods.getTemplate_id() && tpGoods.getTemplate_id() > 0) {
					TpFreightConfig tpFreightConfig = tpOrderService.findByTemId(tpGoods.getTemplate_id());
					if (null != tpFreightConfig) {
						//运费模板
						freightTotal =(int)Math.rint(tpFreightConfig.getFirst_unit() / Integer.parseInt(goods_num)) 
								* tpFreightConfig.getFirst_money();
					}
				}
			}
			if (null != is_invoice && "1".equals(is_invoice)) {
				//发票运费
				invoiceTotal = goodsTotal * Constant.INVOICE_FREIGHT;
				tpOrder.setInvoice_title(invoice_title);
				tpOrder.setTaxpayer(taxpayer);
				tpOrder.setInvoice_email(invoice_email);
				tpOrder.setInvoice_contact(invoice_contact);
				tpOrder.setInvoice_fee(invoiceTotal);
			}
			orderTotal = tpGoods.getShop_price() * Integer.parseInt(goods_num) + invoiceTotal + freightTotal;
			tpOrder.setOrder_amount(orderTotal);
			tpOrder.setTotal_amount(orderTotal);
			tpOrder.setGoods_price(goodsTotal);
			tpOrder.setShipping_price(invoiceTotal + freightTotal);
			tpOrder.setAdd_time((int)new Date().getTime());
			tpOrder.setUser_note(user_note);
			int result = tpOrderService.intsertOrder(tpOrder);
			if (result > 0) {
				//订单商品
				TpOrderGoods tpOrderGoods = new TpOrderGoods();
				tpOrderGoods.setOrder_id(tpOrder.getOrder_id());
				tpOrderGoods.setGoods_id(tpGoods.getGoods_id());
				tpOrderGoods.setGoods_name(tpGoods.getGoods_name());
				tpOrderGoods.setGoods_sn(tpGoods.getGoods_sn());
				tpOrderGoods.setGoods_num(Integer.parseInt(goods_num));
				tpOrderGoods.setFinal_price(tpGoods.getShop_price());
				tpOrderGoods.setGoods_price(tpGoods.getShop_price());
				tpOrderGoods.setCost_price(tpGoods.getCost_price());
				tpOrderGoods.setMember_goods_price(tpGoods.getShop_price());;
				tpOrderGoods.setOriginal_img(tpGoods.getOriginal_img());
				tpOrderGoods.setGoods_type(tpGoods.getGoods_type());
				int goodsResult = tpOrderService.insertOrderGoods(tpOrderGoods);
				if (goodsResult > 0) {
					//订单变化记录
					TpOrderAction tpOrderAction = new TpOrderAction();
					tpOrderAction.setOrder_id(tpOrder.getOrder_id());
					tpOrderAction.setOrder_status(tpOrder.getOrder_status());
					tpOrderAction.setAction_note("您提交了订单，请等待系统确认");
					tpOrderAction.setLog_time((int)new Date().getTime());
					tpOrderAction.setStatus_desc("提交订单");
					int goodsResult1 =tpOrderService.insertOrderAction(tpOrderAction);
					if (goodsResult1 <= 0) {
						jsonObj.put("status", "0");
						jsonObj.put("msg", "添加失败!");
						return jsonObj.toString();
					}
				}
				
				//发票
				if (null != is_invoice && "1".equals(is_invoice)) {
					TpInvoice invoice = new TpInvoice();
					invoice.setOrder_id(tpOrder.getOrder_id());
					invoice.setInvoice_type(Integer.parseInt(type));
					invoice.setInvoice_money(Constant.INVOICE_FREIGHT);
					invoice.setTaxpayer(taxpayer);
					invoice.setAtime((int)new Date().getTime());
					invoice.setCtime((int)new Date().getTime());
					int goodsResult2 = tpOrderService.insertInvoic(invoice);
					if (goodsResult2 <= 0) {
						jsonObj.put("status", "0");
						jsonObj.put("msg", "添加失败!");
						return jsonObj.toString();
					}
				}
			}
		}
		jsonObj.put("return", data);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "OK!");
		return jsonObj.toString();
	}
	
}
