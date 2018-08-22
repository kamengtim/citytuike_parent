package com.citytuike.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpGoods;
import com.citytuike.model.TpGoodsCollect;
import com.citytuike.model.TpGoodsImages;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpGoodsService;
import com.citytuike.service.TpUsersService;

@Controller
@RequestMapping("api/Goods")
public class GoodsController {
	
	@Autowired
	private TpGoodsService tpGoodsService;
	@Autowired
	private TpUsersService tpUsersService;
	/**
	 * @param model
	 * @param id
	 * @param p
	 * @return
	 * 商品列表
	 */
	@RequestMapping(value="/ajaxGoodsList",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String ajaxGoodsList(Model model,@RequestParam(required=true) Integer id,
			@RequestParam(required=true) Integer p){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		
		LimitPageList limitPageList = tpGoodsService.getLimitPageList(id, p);
		data.put("return", limitPageList.getList());
		List<TpGoods> tpGoods = (List<TpGoods>) limitPageList.getList();
		for (TpGoods tpGoods2 : tpGoods) {
			JSONObject goods = tpGoodsService.getGoodsJson(tpGoods2);
			jsonArray.add(goods);
		}
		data.put("list", jsonArray);
		data.put("count", limitPageList.getPage().getTotalCount());
		jsonObj.put("return", data);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "OK!");
		System.out.println("结果:" + jsonObj.toString());
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param id
	 * @return
	 * 商品详情
	 */
	@RequestMapping(value="/goodsInfo",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String goodsInfo(Model model,@RequestParam(required=true) Integer id){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpGoods tpGoods = tpGoodsService.findById(id);
		data = tpGoodsService.getGoodsJson(tpGoods);
		jsonObj.put("return", data);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "OK!");
		return jsonObj.toString();
	}
	/**
	 * @param model
	 * @param id
	 * @return
	 * 收藏商品
	 */
	@RequestMapping(value="/collect_goods",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String collectGoods(Model model, @RequestParam(required=true) String goods_id,
			@RequestParam(required=true) String token){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "收藏失败!");
		
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpGoods tpGoods = tpGoodsService.findById(Integer.valueOf(goods_id).intValue());
		if (null != tpGoods) {
			TpGoodsCollect tpGoodsCollect = new TpGoodsCollect();
			tpGoodsCollect.setGoods_id(tpGoods.getGoods_id());
			tpGoodsCollect.setUser_id(tpUsers.getUser_id());
			tpGoodsCollect.setAdd_time((int)new Date().getTime());
			int status = tpGoodsService.insertGoodsCollect(tpGoodsCollect);
			if (status > 0) {
				jsonObj.put("status", "1");
				jsonObj.put("msg", "收藏成功!请到个人中心查看");
			}
		}
		return jsonObj.toString();
	}
	
	/**
	 * @param model
	 * @param token
	 * @return
	 * 商品轮播
	 */
	@RequestMapping(value="/carousel",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String carousel(Model model,@RequestParam(required=true) String token){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONArray data = new JSONArray();
		
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		String[] goodsId = {"144","144","144"};
		for (int i = 0; i < goodsId.length; i++) {
			JSONObject data1 = new JSONObject();
			JSONArray jsonArray1 = new JSONArray();
			JSONArray jsonArray = new JSONArray();
			TpGoods tpGoods = tpGoodsService.findById(Integer.parseInt(goodsId[i]));
			if (null != tpGoods) {
				List<TpGoodsImages> tpGoodsImages = tpGoodsService.findByGoodsId(tpGoods.getGoods_id());
				for (TpGoodsImages tpGoodsImages2 : tpGoodsImages) {
					JSONObject jsonObject1 = new JSONObject();
					jsonObject1 = tpGoodsService.getGoodsImagesJson(tpGoodsImages2);
					jsonArray1.add(jsonObject1);
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject = tpGoodsService.getGoodsJson(tpGoods);
				jsonArray.add(jsonObject);
				data1.put("goods_images_list", jsonArray1);
				data1.put("goods", jsonArray);
			}
			data.add(data1);
		}
		jsonObj2.put("list", data);
		jsonObj.put("return", jsonObj2);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "OK!");
		return jsonObj.toString();
	}
	
}
