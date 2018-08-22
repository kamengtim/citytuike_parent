package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpFreightConfig;
import com.citytuike.model.TpInvoice;
import com.citytuike.model.TpOrder;
import com.citytuike.model.TpOrderAction;
import com.citytuike.model.TpOrderGoods;

public interface TpOrderService {

	TpFreightConfig findByTemId(Integer template_id);

	int intsertOrder(TpOrder tpOrder);

	int insertOrderGoods(TpOrderGoods tpOrderGoods);

	int insertOrderAction(TpOrderAction tpOrderAction);

	int insertInvoic(TpInvoice invoice);

	LimitPageList getLimitPageList(String type, Integer p);

	JSONObject getOrderJson(TpOrder tpOrder);

	TpOrder findOrderById(Integer id);

	int updataRecordRefundOrder(TpOrder tpOrder);

	int updataOrderConfirm(TpOrder tpOrder);

	int updateOrderAddress(TpOrder tpOrder);

	

}
