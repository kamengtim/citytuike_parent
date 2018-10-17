package com.citytuike.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpOrderAction;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("api/Order")
public class OrderController extends BaseController{
	
	@Autowired
	private TpOrderService tpOrderService;
	@Autowired
	private TpUsersService tpUsersService;
	/**
	 * @return
	 * 订单列表
	 */
	@RequestMapping(value="/order_list",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "订单列表", notes = "订单列表")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String orderList(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String type = jsonO.getString("type");
		Integer p = jsonO.getInteger("p");
		if (null == type || "".equals(type) || null == p || "".equals(p)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
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
		
		LimitPageList limitPageList = tpOrderService.getLimitPageList(tpUsers.getUser_id(), type, p);
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
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功");
		System.out.println("结果:" + jsonObj.toString());
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 订单详情
	 */
	@RequestMapping(value="/order_detail",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "订单详情", notes = "订单详情")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String orderDetail(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String id = jsonO.getString("id");
		String orderSn = jsonO.getString("orderSn");
		if (null == id || "".equals(id)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
			return jsonObj.toString();
		}
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(id));
		if (null != tpOrder) {
			data = tpOrderService.getOrderJson(tpOrder);
			jsonObj.put("result", data);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
			System.out.println("结果:" + jsonObj.toString());
		}
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 申请取消订单
	 */
	@RequestMapping(value="/record_refund_order",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "申请取消订单", notes = "申请取消订单")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String recordRefundOrder(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String order_id = jsonO.getString("order_id");
		if (null == order_id || "".equals(order_id)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
			return jsonObj.toString();
		}
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(order_id));
		if (null != tpOrder) {
			int result = tpOrderService.updataRecordRefundOrder(tpOrder);
			if (result > 0) {
				TpOrderAction tpOrderAction = tpOrderService.getOrderAction(tpOrder, 0);
				int goodsResult1 =tpOrderService.insertOrderAction(tpOrderAction);
				if (goodsResult1 > 0) {
					jsonObj.put("status", 1);
					jsonObj.put("msg", "请求成功!");
					System.out.println("结果:" + jsonObj.toString());
				}
			}
			
		}
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 确认收货
	 */
	@RequestMapping(value="/order_confirm",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "确认收货", notes = "确认收货")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String orderConfirm(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String id = jsonO.getString("id");
		if (null == id || "".equals(id)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
			return jsonObj.toString();
		}
		TpOrder tpOrder = tpOrderService.findOrderById(Integer.parseInt(id));
		if (null != tpOrder) {
			int result = tpOrderService.updataOrderConfirm(tpOrder);
			if (result > 0) {
				TpOrderAction tpOrderAction = tpOrderService.getOrderAction(tpOrder, 2);
				int goodsResult1 =tpOrderService.insertOrderAction(tpOrderAction);
				if (goodsResult1 > 0) {
					jsonObj.put("status", 1);
					jsonObj.put("msg", "请求成功!");
					System.out.println("结果:" + jsonObj.toString());
				}
			}
			
		}
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 修改订单收货地址
	 */
	@RequestMapping(value="/edit_order_address",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "修改订单收货地址", notes = "修改订单收货地址")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String editOrderAddress(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		String order_id = jsonO.getString("order_id");
		String province = jsonO.getString("province");
		String city = jsonO.getString("city");
		String district = jsonO.getString("district");
		String address = jsonO.getString("address");
		if (null == order_id || "".equals(order_id) || null == province || "".equals(province)
				|| null == city || "".equals(city) || null == district || "".equals(district)
				|| null == address || "".equals(address)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
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
				jsonObj.put("status", 1);
				jsonObj.put("msg", "请求成功!");
				System.out.println("结果:" + jsonObj.toString());
			}
			
		}
		return jsonObj.toString();
	}
	//TODO  3. 申请退货
	//TODO  4. 物流信息
	//TODO  9. 退换货列表
	//TODO  10. 退货详情
	//TODO  11. 修改退货状态，发货
	//TODO  12. 退款完成
	//TODO  13. 取消售后服务
	//TODO  14. 换货商品确认收货
	//TODO  15. 待收货列表
	//TODO  16. 补运费下单
	//TODO  18. 纸巾部分确认收货
	//TODO  19. 撤销取消订单

}
