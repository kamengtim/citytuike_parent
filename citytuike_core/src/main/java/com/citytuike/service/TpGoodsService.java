package com.citytuike.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpGoods;
import com.citytuike.model.TpGoodsCollect;
import com.citytuike.model.TpGoodsImages;

public interface TpGoodsService {

	LimitPageList getLimitPageList(Integer id, Integer p);

	JSONObject getGoodsJson(TpGoods tpGoods2);

	TpGoods findById(Integer id);

	int insertGoodsCollect(TpGoodsCollect tpGoodsCollect);

	List<TpGoodsImages> findByGoodsId(Integer goodsId);

	JSONObject getGoodsImagesJson(TpGoodsImages tpGoodsImages2);

	

}
