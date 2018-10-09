package com.citytuike.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpOrderMapper;

@Service
public class TpOrderServiceImpl implements TpOrderService{
	@Autowired
	private TpOrderMapper tpOrderMapper;

	public TpFreightConfig findByTemId(Integer template_id) {
		return tpOrderMapper.findByTemId(template_id);
	}

	public int intsertOrder(TpOrder tpOrder) {
		return tpOrderMapper.intsertOrder(tpOrder);
	}

	public int insertOrderGoods(TpOrderGoods tpOrderGoods) {
		return tpOrderMapper.insertOrderGoods(tpOrderGoods);
	}

	public int insertOrderAction(TpOrderAction tpOrderAction) {
		return tpOrderMapper.insertOrderAction(tpOrderAction);
	}

	public int insertInvoic(TpInvoice invoice) {
		return tpOrderMapper.insertInvoic(invoice);
	}

	public LimitPageList getLimitPageList(String type, Integer p) {
		LimitPageList LimitPageStuList = new LimitPageList();
	    int totalCount=tpOrderMapper.getCount();//获取总的记录数
	    List<TpGoods> stuList=new ArrayList<TpGoods>();
	    Page page=null;
	    if(p!=null){
	        page=new Page(totalCount, p);
	        page.setPageSize(10);
	        stuList=tpOrderMapper.selectByPage(type, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
	    }else{
	        page=new Page(totalCount, 1);//初始化pageNow为1
	        page.setPageSize(10);
	        stuList=tpOrderMapper.selectByPage(type, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
	    }
	    LimitPageStuList.setPage(page);
	    LimitPageStuList.setList(stuList);
	    return LimitPageStuList;
	}

	public JSONObject getOrderJson(TpOrder tpOrder) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		int goods_num = 0;
		ArrayList<Integer> goodsType = new ArrayList<Integer>();
		List<TpOrderGoods> tpOrderGoods = tpOrderMapper.findAllGoodsByOrderId(tpOrder.getOrder_id());
		for (TpOrderGoods tpOrderGoods2 : tpOrderGoods) {
			goods_num += tpOrderGoods2.getGoods_num();
			goodsType.add(tpOrderGoods2.getGoods_type());
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("rec_id", tpOrderGoods2.getRec_id());
			jsonObject2.put("order_id", tpOrderGoods2.getOrder_id());
			jsonObject2.put("goods_id", tpOrderGoods2.getGoods_id());
			jsonObject2.put("goods_name", tpOrderGoods2.getGoods_name());
			jsonObject2.put("goods_sn", tpOrderGoods2.getGoods_sn());
			jsonObject2.put("goods_num", tpOrderGoods2.getGoods_num());
			jsonObject2.put("final_price", tpOrderGoods2.getFinal_price());
			jsonObject2.put("goods_price", tpOrderGoods2.getGoods_price());
			jsonObject2.put("cost_price", tpOrderGoods2.getCost_price());
			jsonObject2.put("member_goods_price", tpOrderGoods2.getMember_goods_price());
			jsonObject2.put("give_integral", tpOrderGoods2.getGive_integral());
			jsonObject2.put("spec_key", tpOrderGoods2.getSpec_key());
			jsonObject2.put("spec_key_name", tpOrderGoods2.getSpec_key_name());
			jsonObject2.put("is_comment", tpOrderGoods2.getIs_comment());
			jsonObject2.put("prom_type", tpOrderGoods2.getProm_type());
			jsonObject2.put("prom_id", tpOrderGoods2.getProm_id());
			jsonObject2.put("is_send", tpOrderGoods2.getIs_send());
			jsonObject2.put("delivery_id", tpOrderGoods2.getDelivery_id());
			jsonObject2.put("sku", tpOrderGoods2.getSku());
			jsonObject2.put("original_img", tpOrderGoods2.getOriginal_img());
			jsonObject2.put("goods_type", tpOrderGoods2.getGoods_type());
			jsonObject2.put("type", tpOrderGoods2.getType());
			jsonArray.add(jsonObject2);
		}
		jsonObject.put("goods_list", jsonArray);
		jsonObject.put("order_id", tpOrder.getOrder_id());
		jsonObject.put("order_sn", tpOrder.getOrder_sn());
		jsonObject.put("user_id", tpOrder.getUser_id());
		jsonObject.put("order_status", tpOrder.getOrder_status());
		jsonObject.put("shipping_status", tpOrder.getShipping_status());
		jsonObject.put("pay_status", tpOrder.getPay_status());
		jsonObject.put("consignee", tpOrder.getConsignee());
		jsonObject.put("country", tpOrder.getCountry());
		jsonObject.put("province", tpOrder.getProvince());
		jsonObject.put("city", tpOrder.getCity());
		jsonObject.put("district", tpOrder.getDistrict());
		jsonObject.put("twon", tpOrder.getTwon());
		jsonObject.put("address", tpOrder.getAddress());
		jsonObject.put("zipcode", tpOrder.getZipcode());
		jsonObject.put("mobile", tpOrder.getMobile());
		jsonObject.put("email", tpOrder.getEmail());
		jsonObject.put("shipping_code", tpOrder.getShipping_code());
		jsonObject.put("shipping_name", tpOrder.getShipping_name());
		jsonObject.put("pay_code", tpOrder.getPay_code());
		jsonObject.put("pay_name", tpOrder.getPay_name());
		jsonObject.put("invoice_title", tpOrder.getInvoice_title());
		jsonObject.put("taxpayer", tpOrder.getTaxpayer());
		jsonObject.put("goods_price", tpOrder.getGoods_price());
		jsonObject.put("shipping_price", tpOrder.getShipping_price());
		jsonObject.put("user_money", tpOrder.getUser_money());
		jsonObject.put("coupon_price", tpOrder.getCoupon_price());
		jsonObject.put("integral", tpOrder.getIntegral());
		jsonObject.put("integral_money", tpOrder.getIntegral_money());
		jsonObject.put("order_amount", tpOrder.getOrder_amount());
		jsonObject.put("total_amount", tpOrder.getTotal_amount());
		jsonObject.put("add_time", tpOrder.getAdd_time());
		jsonObject.put("confirm_time", tpOrder.getConfirm_time());
		jsonObject.put("pay_time", tpOrder.getPay_time());
		jsonObject.put("transaction_id", tpOrder.getTransaction_id());
		jsonObject.put("prom_id", tpOrder.getProm_id());
		jsonObject.put("prom_type", tpOrder.getProm_type());
		jsonObject.put("order_prom_id", tpOrder.getOrder_prom_id());
		jsonObject.put("order_prom_amount", tpOrder.getOrder_prom_amount());
		jsonObject.put("discount", tpOrder.getDiscount());
		jsonObject.put("user_note", tpOrder.getUser_note());
		jsonObject.put("admin_note", tpOrder.getAdmin_note());
		jsonObject.put("parent_sn", tpOrder.getParent_sn());
		jsonObject.put("is_distribut", tpOrder.getIs_distribut());
		jsonObject.put("paid_money", tpOrder.getPaid_money());
		jsonObject.put("deleted", tpOrder.getDeleted());
		jsonObject.put("settle_status", tpOrder.getSettle_status());
		jsonObject.put("all_income", tpOrder.getAll_income());
		jsonObject.put("invoice_email", tpOrder.getInvoice_email());
		jsonObject.put("invoice_contact", tpOrder.getInvoice_contact());
		jsonObject.put("invoice_fee", tpOrder.getInvoice_fee());
		jsonObject.put("is_return", tpOrder.getIs_return());
		
		 ArrayList<Integer> tempList = new ArrayList<Integer>();
	        for (Integer object : goodsType) {
	            if (!tempList.contains(object)) {
	                tempList.add(object);
	            }
	        }
		jsonObject.put("count_goods_type_num", tempList.size());
		jsonObject.put("count_goods_num", goods_num);
		
		
		return jsonObject;
	}

	public TpOrder findOrderById(Integer id) {
		return tpOrderMapper.findOrderById(id);
	}

	public int updataRecordRefundOrder(TpOrder tpOrder) {
		return tpOrderMapper.updataRecordRefundOrder(tpOrder);
	}

	public int updataOrderConfirm(TpOrder tpOrder) {
		return tpOrderMapper.updataOrderConfirm(tpOrder);
	}

	public int updateOrderAddress(TpOrder tpOrder) {
		return tpOrderMapper.updateOrderAddress(tpOrder);
	}

	public TpOrder findOrderByOrderSn(String out_trade_no) {
		return tpOrderMapper.findOrderByOrderSn(out_trade_no);
	}

	public List<TpOrderGoods> findAllGoodsByOrderId(Integer order_id) {
		return tpOrderMapper.findAllGoodsByOrderId(order_id);
	}

	public TpOrderAction getOrderAction(TpOrder tpOrder, int type) {
		TpOrderAction tpOrderAction = new TpOrderAction();

		tpOrderAction.setOrder_id(tpOrder.getOrder_id());
		tpOrderAction.setLog_time((int)new Date().getTime());
		tpOrderAction.setAction_user(0);
		tpOrderAction.setOrder_status(tpOrder.getOrder_status());
		tpOrderAction.setPay_status(tpOrder.getPay_status());
		tpOrderAction.setShipping_status(tpOrder.getShipping_status());
		if (type == 0){
			tpOrderAction.setOrder_status(3);
			tpOrderAction.setPay_status(tpOrder.getPay_status());
			tpOrderAction.setShipping_status(tpOrder.getShipping_status());
			tpOrderAction.setAction_note("您取消了订单，请等待系统确认");
			tpOrderAction.setStatus_desc("用户取消已付款订单");
		}else if (type == 1){
			tpOrderAction.setOrder_status(tpOrder.getOrder_status());
			tpOrderAction.setPay_status(1);
			tpOrderAction.setShipping_status(tpOrder.getShipping_status());
			tpOrderAction.setAction_note("您订单已支付成功，请等待系统确认");
			tpOrderAction.setStatus_desc("订单支付成功");
		}else if (type == 2){
			tpOrderAction.setOrder_status(tpOrder.getOrder_status());
			tpOrderAction.setPay_status(tpOrder.getPay_status());
			tpOrderAction.setShipping_status(1);
			tpOrderAction.setAction_note("您订单已发货，请等待系统确认");
			tpOrderAction.setStatus_desc("订单发货");
		}
		return tpOrderAction;
	}

	public int updateOrderByAlipay(TpOrder tpOrder) {
		return tpOrderMapper.updateOrderByAlipay(tpOrder);
	}

	public List<TpOrder> findAllOrderByUserId(Integer user_id) {
		return tpOrderMapper.findAllOrderByUserId(user_id);
	}

	@Override
	public List<TpPlugin> findAllPlugin() {
		return tpOrderMapper.findAllPlugin();
	}

	@Override
	public List<TpOrder> findAllOrderAndGoods(Integer user_id) {
		return tpOrderMapper.findAllOrderAndGoods(user_id);
	}

	@Override
	public void save(TpFansOrder tpFansOrder) {
		tpOrderMapper.save(tpFansOrder);
	}

	@Override
	public void updateOrder(Integer order_id) {
		tpOrderMapper.updateStatus(order_id);
	}


}
