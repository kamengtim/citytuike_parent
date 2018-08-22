package com.citytuike.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citytuike.model.TpGoods;
import com.citytuike.model.TpGoodsCollect;
import com.citytuike.model.TpGoodsImages;

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

	TpGoods findById( @Param(value = "id")Integer id);

	int insertGoodsCollect(TpGoodsCollect tpGoodsCollect);

	List<TpGoodsImages> findByGoodsId(@Param(value = "goodsId")Integer goodsId);

}
