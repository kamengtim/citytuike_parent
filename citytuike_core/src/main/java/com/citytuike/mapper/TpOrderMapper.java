package com.citytuike.mapper;

import com.citytuike.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpOrderMapper {

	TpFreightConfig findByTemId(@Param(value = "template_id") Integer template_id);

	int intsertOrder(TpOrder tpOrder);

	int insertOrderGoods(TpOrderGoods tpOrderGoods);

	int insertOrderAction(TpOrderAction tpOrderAction);

	int insertInvoic(TpInvoice invoice);

	int getCount(@Param(value = "user_id") int user_id, @Param(value = "type") String type);

	List<TpGoods> selectByPage(@Param(value = "user_id") int user_id, @Param(value = "type") String type, @Param(value = "startPos") int startPos, @Param(value = "pageSize") int pageSize);

	List<TpOrderGoods> findAllGoodsByOrderId(@Param(value = "order_id") Integer order_id);

	TpOrder findOrderById(@Param(value = "id") Integer id);

	int updataOrderConfirm(TpOrder tpOrder);

	int updataRecordRefundOrder(TpOrder tpOrder);

	int updateOrderAddress(TpOrder tpOrder);

    TpOrder findOrderByOrderSn(@Param(value = "out_trade_no")String out_trade_no);

	int updateOrderByAlipay(TpOrder tpOrder);

    List<TpOrder> findAllOrderByUserId(@Param(value = "user_id")Integer user_id);

    List<TpPlugin> findAllPlugin();

    List<TpOrder> findAllOrderAndGoods(@Param(value = "user_id")Integer user_id);

	TpOrder getConsigneeByMobile(Integer user_id);

    void save(TpFansOrder tpFansOrder);

    void updateStatus(Integer order_id);

    TpOrder selectOrder(String order_id);
}
