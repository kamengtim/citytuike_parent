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

	int getCount();

	List<TpGoods> selectByPage(@Param(value = "type") String type, @Param(value = "startPos") int startPos, @Param(value = "pageSize") int pageSize);

	List<TpOrderGoods> findAllGoodsByOrderId(@Param(value = "order_id") Integer order_id);

	TpOrder findOrderById(@Param(value = "id") Integer id);

	int updataOrderConfirm(TpOrder tpOrder);

	int updataRecordRefundOrder(TpOrder tpOrder);

	int updateOrderAddress(TpOrder tpOrder);


}
