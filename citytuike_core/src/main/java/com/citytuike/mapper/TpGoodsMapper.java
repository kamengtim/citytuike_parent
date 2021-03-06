package com.citytuike.mapper;

import com.citytuike.model.TpGoods;
import com.citytuike.model.TpGoodsCollect;
import com.citytuike.model.TpGoodsImages;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpGoodsMapper {

	 /**
     * 获取分页记录
     * @param startPos:从数据库中第几行开始获取
     * @param pageSize:获取的条数
     * @return 返回pageSize条数据的集合，数据足够多
     */
    List<TpGoods> selectByPage(@Param(value = "startPos") Integer startPos,
                               @Param(value = "pageSize") Integer pageSize);

    /**
     * 获取数据库总的记录数
     * @return 返回数据库表的总条数
     */
    int getCount();

	TpGoods findById(@Param(value = "id") Integer id);

	int insertGoodsCollect(TpGoodsCollect tpGoodsCollect);

	List<TpGoodsImages> findByGoodsId(@Param(value = "goodsId") Integer goodsId);
    /*
    * 查询图片路劲
    * */
    String getGoodsUrl(Integer order_id);

    TpGoods getGoodsById(Integer id);

    TpGoods selectPrice();

    String getGoodName(String goods_id);

    int getCountByGoodsOrUser(@Param("goods_id") Integer goods_id, @Param("user_id") Integer user_id);
}
