package com.citytuike.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpGoodsMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpGoods;
import com.citytuike.model.TpGoodsCollect;
import com.citytuike.model.TpGoodsImages;

@Service
public class TpGoodsServiceImpl implements TpGoodsService{

	@Autowired
	private TpGoodsMapper tpGoodsMapper;
	public LimitPageList getLimitPageList(Integer id, Integer p) {
		 LimitPageList LimitPageStuList = new LimitPageList();
		    int totalCount=tpGoodsMapper.getCount();//获取总的记录数
		    List<TpGoods> stuList=new ArrayList<TpGoods>();
		    Page page=null;
		    if(p!=null){
		        page=new Page(totalCount, p);
		        page.setPageSize(10);
		        stuList=tpGoodsMapper.selectByPage(page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
		    }else{
		        page=new Page(totalCount, 1);//初始化pageNow为1
		        page.setPageSize(10);
		        stuList=tpGoodsMapper.selectByPage(page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据
		    }
		    LimitPageStuList.setPage(page);
		    LimitPageStuList.setList(stuList);
		    return LimitPageStuList;
	}
	public JSONObject getGoodsJson(TpGoods tpGoods2) {
		JSONObject data = new JSONObject();
		data.put("goods_id", tpGoods2.getGoods_id());
		data.put("cat_id", tpGoods2.getCat_id());
		data.put("extend_cat_id", tpGoods2.getExtend_cat_id());
		data.put("goods_sn", tpGoods2.getGoods_sn());
		data.put("goods_name", tpGoods2.getGoods_name());
		data.put("click_count", tpGoods2.getClick_count());
		data.put("brand_id", tpGoods2.getBrand_id());
		data.put("store_count", tpGoods2.getStore_count());
		data.put("comment_count", tpGoods2.getComment_count());
		data.put("weight", tpGoods2.getWeight());
		data.put("volume", tpGoods2.getVolume());
		data.put("market_price", tpGoods2.getMarket_price());
		data.put("shop_price", tpGoods2.getShop_price());
		data.put("cost_price", tpGoods2.getCost_price());
		data.put("price_ladder", tpGoods2.getPrice_ladder());
		data.put("keywords", tpGoods2.getKeywords());
		data.put("goods_remark", tpGoods2.getGoods_remark());
		data.put("goods_content", tpGoods2.getGoods_content());
		data.put("mobile_content", tpGoods2.getMobile_content());
		data.put("original_img", tpGoods2.getOriginal_img());
		data.put("is_virtual", tpGoods2.getIs_virtual());
		data.put("virtual_indate", tpGoods2.getVirtual_indate());
		data.put("virtual_limit", tpGoods2.getVirtual_limit());
		data.put("virtual_refund", tpGoods2.getVirtual_refund());
		data.put("is_on_sale", tpGoods2.getIs_on_sale());
		data.put("is_free_shipping", tpGoods2.getIs_free_shipping());
		data.put("on_time", tpGoods2.getOn_time());
		data.put("sort", tpGoods2.getSort());
		data.put("is_recommend", tpGoods2.getIs_recommend());
		data.put("is_new", tpGoods2.getIs_new());
		data.put("is_hot", tpGoods2.getIs_hot());
		data.put("last_update", tpGoods2.getLast_update());
		data.put("goods_type", tpGoods2.getGoods_type());
		data.put("spec_type", tpGoods2.getSpec_type());
		data.put("give_integral", tpGoods2.getGive_integral());
		data.put("exchange_integral", tpGoods2.getExchange_integral());
		data.put("suppliers_id", tpGoods2.getSuppliers_id());
		data.put("sales_sum", tpGoods2.getSales_sum());
		data.put("prom_type", tpGoods2.getProm_type());
		data.put("prom_id", tpGoods2.getProm_id());
		data.put("commission", tpGoods2.getCommission());
		data.put("spu", tpGoods2.getSpu());
		data.put("sku", tpGoods2.getSku());
		data.put("template_id", tpGoods2.getTemplate_id());
		data.put("video", tpGoods2.getVideo());
		return data;
	}
	public TpGoods findById(Integer id) {
		return tpGoodsMapper.findById(id);
	}
	public int insertGoodsCollect(TpGoodsCollect tpGoodsCollect) {
		return tpGoodsMapper.insertGoodsCollect(tpGoodsCollect);
	}
	public List<TpGoodsImages> findByGoodsId(Integer goodsId) {
		return tpGoodsMapper.findByGoodsId(goodsId);
	}
	public JSONObject getGoodsImagesJson(TpGoodsImages tpGoodsImages2) {
		JSONObject data = new JSONObject();
		data.put("img_id", tpGoodsImages2.getImg_id());
		data.put("goods_id", tpGoodsImages2.getGoods_id());
		data.put("image_url", tpGoodsImages2.getImage_url());
		return data;
	}
	

}
