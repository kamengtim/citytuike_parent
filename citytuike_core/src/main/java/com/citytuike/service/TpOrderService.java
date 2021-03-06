package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;

import java.util.List;

public interface TpOrderService {

	TpFreightConfig findByTemId(Integer template_id);

	int intsertOrder(TpOrder tpOrder);

	int insertOrderGoods(TpOrderGoods tpOrderGoods);

	int insertOrderAction(TpOrderAction tpOrderAction);

	int insertInvoic(TpInvoice invoice);

	LimitPageList getLimitPageList(Integer user_id, String type, Integer p);

	JSONObject getOrderJson(TpOrder tpOrder);

	TpOrder findOrderById(Integer id);

	int updataRecordRefundOrder(TpOrder tpOrder);

	int updataOrderConfirm(TpOrder tpOrder);

	int updateOrderAddress(TpOrder tpOrder);

    TpOrder findOrderByOrderSn(String out_trade_no);

	List<TpOrderGoods> findAllGoodsByOrderId(Integer order_id);

	TpOrderAction getOrderAction(TpOrder tpOrder, int i);

	int updateOrderByAlipay(TpOrder tpOrder);

    List<TpOrder> findAllOrderByUserId(Integer user_id);

    List<TpPlugin> findAllPlugin();

    List<TpOrder> findAllOrderAndGoods(Integer user_id);

    void save(TpFansOrder tpFansOrder);

    void updateOrder(Integer order_id);
}
