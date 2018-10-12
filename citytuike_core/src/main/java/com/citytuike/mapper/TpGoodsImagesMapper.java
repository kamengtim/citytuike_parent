package com.citytuike.mapper;

import com.citytuike.model.TpGoods;
import com.citytuike.model.TpGoodsImages;

import java.util.List;

public interface TpGoodsImagesMapper {
    int deleteByPrimaryKey(Integer img_id);

    int insert(TpGoodsImages record);

    TpGoodsImages selectByPrimaryKey(Integer img_id);

    List<TpGoodsImages> selectAll();

    int updateByPrimaryKey(TpGoodsImages record);

    TpGoodsImages selectURL(Integer goods_id);
}