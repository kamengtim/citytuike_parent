package com.citytuike.mapper;

import com.citytuike.model.TpOrderGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpOrderGoodsMapper {
    int deleteByPrimaryKey(Integer rec_id);

    int insert(TpOrderGoods record);

    TpOrderGoods selectByPrimaryKey(Integer rec_id);

    List<TpOrderGoods> selectAll();

    int updateByPrimaryKey(TpOrderGoods record);

    String getGoodsUrl(@Param("order_id") Integer order_id, @Param("goods_id") Integer goods_id);

    TpOrderGoods selectGoodsAndOrder(String order_id);
}