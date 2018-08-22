package com.citytuike.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpOrderAction;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;

@Controller
@RequestMapping("api/Order")
public class OrderController {
	
	@Autowired
	private TpOrderService tpOrderService;
	@Autowired
	private TpUsersService tpUsersService;
	/**
	 * @param model
	 * @param token
	 * @param type
	 * @param p
	 * @return
	 * 订单列表
	 */
	@RequestMapping(value="/order_list",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String orderList(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String type,
			@RequestParam(required=true, defaultValue="1") Integer p){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("0", "待确认");
		jsonObject.put("1", "已确认");
		jsonObject.put("2", "已收货");
		jsonObject.put("3", "已取消");
		jsonObject.put("4", "已完成");
		jsonObject.put("5", "已作废");
		jsonArray.add(jsonObject);
		data.put("order_status", jsonArray);
		jsonArray = new JSONArray();
		jsonObject = new JSONObject();
		jsonObject.put("0", "未发货");
		jsonObject.put("1", "已发货");
		jsonObject.put("2", "部分发货");
		jsonArray.add(jsonObject);
		data.put("shipping_status", jsonArray);
		jsonArray = new JSONArray();
		jsonObject = new JSONObject();
		jsonObject.put("0", "未支付");
		jsonObject.put("1", "已支付");
		jsonObject.put("2", "部分支付");
		jsonObject.put("3", "已退款");
		jsonObject.put("4", "拒绝退款");
		jsonArray.add(jsonObject);
		data.put("pay_status", jsonArray);
		
		LimitPageList limitPageList = tpOrderService.getLimitPageList(type, p);
		data.put("url", "https://api.citytuike.cn");
		data.put("page", limitPageList.getPage().getPageNow());
		data.put("count", limitPageList.getPage().getTotalCount());
		data.put("totalPage", limitPageList.getPage().getPageSize());
		data.put("active", "order_list");
		data.put("active_status", "CANCEL");
		
		JSONArray jsonArray1 = new JSONArray();
		List<TpOrder> tpOrders = (List<TpOrder>) limitPageList.getList();
		for (TpOrder tpOrder : tpOrders) {
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2 = tpOrderService.getOrderJson(tpOrder);
			jsonArray1.add(jsonObject2);
		}
		data.put("lists", jsonArray1);
		jsonObj.put("result", data);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "ok");
		System.out.println("结果:" + jsonObj.toString());
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param id
	 * @param orderSn
	 * @return
	 * 订单详情
	 */
	@RequestMapping(value="/order_detail",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String orderDetail(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String id,
			@RequestParam(required=false) String orderSn){
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
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(id));
		if (null != tpOrder) {
			data = tpOrderService.getOrderJson(tpOrder);
			jsonObj.put("result", data);
			jsonObj.put("status", "1");
			jsonObj.put("msg", "ok");
			System.out.println("结果:" + jsonObj.toString());
		}
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param id
	 * @return
	 * 申请取消订单
	 */
	@RequestMapping(value="/record_refund_order",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String recordRefundOrder(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String order_id){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(order_id));
		if (null != tpOrder) {
			int result = tpOrderService.updataRecordRefundOrder(tpOrder);
			if (result > 0) {
				TpOrderAction tpOrderAction = new TpOrderAction();
				tpOrderAction.setOrder_id(tpOrder.getOrder_id());
				tpOrderAction.setOrder_status(tpOrder.getOrder_status());
				tpOrderAction.setAction_note("您取消了订单，请等待系统确认");
				tpOrderAction.setLog_time((int)new Date().getTime());
				tpOrderAction.setStatus_desc("用户取消已付款订单");
				int goodsResult1 =tpOrderService.insertOrderAction(tpOrderAction);
				if (goodsResult1 > 0) {
					jsonObj.put("status", "1");
					jsonObj.put("msg", "ok");
					System.out.println("结果:" + jsonObj.toString());
				}
			}
			
		}
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param id
	 * @return
	 * 确认收货
	 */
	@RequestMapping(value="/order_confirm",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String orderConfirm(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String id){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(id));
		if (null != tpOrder) {
			int result = tpOrderService.updataOrderConfirm(tpOrder);
			if (result > 0) {
				TpOrderAction tpOrderAction = new TpOrderAction();
				tpOrderAction.setOrder_id(tpOrder.getOrder_id());
				tpOrderAction.setOrder_status(tpOrder.getOrder_status());
				tpOrderAction.setAction_note("您订单已发货，请等待系统确认");
				tpOrderAction.setLog_time((int)new Date().getTime());
				tpOrderAction.setStatus_desc("订单发货");
				int goodsResult1 =tpOrderService.insertOrderAction(tpOrderAction);
				if (goodsResult1 > 0) {
					jsonObj.put("status", "1");
					jsonObj.put("msg", "ok");
					System.out.println("结果:" + jsonObj.toString());
				}
			}
			
		}
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param token
	 * @param order_id
	 * @param province
	 * @param city
	 * @param district
	 * @param address
	 * @return
	 * 修改订单收货地址
	 */
	@RequestMapping(value="/edit_order_address",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String editOrderAddress(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String order_id,
			@RequestParam(required=true) String province,
			@RequestParam(required=true) String city,
			@RequestParam(required=true) String district,
			@RequestParam(required=true) String address){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(order_id));
		if (null != tpOrder) {
			tpOrder.setProvince(Integer.parseInt(province));
			tpOrder.setCity(Integer.parseInt(city));
			tpOrder.setDistrict(Integer.parseInt(district));
			tpOrder.setAddress(address);
			int result = tpOrderService.updateOrderAddress(tpOrder);
			if (result > 0) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "ok");
				System.out.println("结果:" + jsonObj.toString());
			}
			
		}
		return jsonObj.toString();
	}
	
}
