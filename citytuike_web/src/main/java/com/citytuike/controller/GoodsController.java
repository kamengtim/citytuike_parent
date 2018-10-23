package com.citytuike.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.TpGoodsService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Util;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("api/Goods")
public class GoodsController extends BaseController{
	
	@Autowired
	private TpGoodsService tpGoodsService;
	@Autowired
	private TpUsersService tpUsersService;
	/**
	 * @return
	 * 商品列表页
	 */
	@RequestMapping(value="/ajaxGoodsList",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	@ApiOperation(value = "商品列表页", notes = "商品列表页")
	public @ResponseBody String ajaxGoodsList(HttpServletRequest request){

		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if (null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		Integer id = jsonO.getInteger("id");
		Integer page = jsonO.getInteger("page");
		if (null == id || "".equals(id) || null == page || "".equals(page)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		LimitPageList limitPageList = tpGoodsService.getLimitPageList(id, page);
//		data.put("return", limitPageList.getList());
		List<TpGoods> tpGoods = (List<TpGoods>) limitPageList.getList();
		for (TpGoods tpGoods2 : tpGoods) {
			JSONObject goods = tpGoodsService.getGoodsJson(tpGoods2);
			jsonArray.add(goods);
		}
		data.put("list", jsonArray);
		data.put("count", limitPageList.getPage().getTotalCount());
		jsonObj.put("result", data);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		System.out.println("结果:" + jsonObj.toString());
		return jsonObj.toString();
	}
	/**
	 * @param id
	 * @return
	 * 商品详情
	 */
	@RequestMapping(value="/goodsInfo",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "商品详情", notes = "商品详情")
	public @ResponseBody String goodsInfo(@RequestParam(required=true) Integer id){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		JSONObject good = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpGoods tpGoods = tpGoodsService.findById(id);
		good = tpGoodsService.getGoodsJson(tpGoods);
		data1.put("goods", good);
		jsonObj.put("result", data1);
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}
	/**
	 * @return
	 * 收藏商品
	 */
	@RequestMapping(value="/collect_goods",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = "收藏商品", notes = "收藏商品")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
	public @ResponseBody String collectGoods(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		JSONObject jsonO = getRequestJson(request);
		if(null == jsonO){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		Integer goods_id = jsonO.getInteger("goods_id");
		if (null == goods_id || "".equals(goods_id)){
			jsonObj.put("status", 0);
			jsonObj.put("msg", "参数有误");
			return jsonObj.toString();
		}
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
			return jsonObj.toString();
		}
		TpGoods tpGoods = tpGoodsService.findById(goods_id);
		if (null != tpGoods) {
			TpGoodsCollect tpGoodsCollect = new TpGoodsCollect();
			tpGoodsCollect.setGoods_id(tpGoods.getGoods_id());
			tpGoodsCollect.setUser_id(tpUsers.getUser_id());
			tpGoodsCollect.setAdd_time(Integer.parseInt(Util.CreateDate()));
			int status = tpGoodsService.insertGoodsCollect(tpGoodsCollect);
			if (status > 0) {
				jsonObj.put("status", 1);
				jsonObj.put("msg", "收藏成功!请到个人中心查看");
			}
		}
		return jsonObj.toString();
	}
	
	/**
	 * @return
	 *  轮播商品
	 */
	@RequestMapping(value="/carousel",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiOperation(value = " 轮播商品", notes = " 轮播商品")
	public @ResponseBody String carousel(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONArray data = new JSONArray();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
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
		jsonObj.put("status", 1);
		jsonObj.put("msg", "请求成功!");
		return jsonObj.toString();
	}
	//TODO  商品规格
}
