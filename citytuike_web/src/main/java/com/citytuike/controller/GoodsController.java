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
import java.util.Calendar;
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
	@ApiOperation(value = "商品详情" , notes = "商品详情")
	public @ResponseBody String goodsInfo(HttpServletRequest request, @RequestParam(required=true) Integer id){
		JSONObject jsonObj = new JSONObject();
		JSONObject data1 = new JSONObject();
		JSONObject good = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
			return jsonObj.toString();
		}
		TpGoods tpGoods = tpGoodsService.findById(id);
		if(null != tpGoods){
			if (tpGoods.getIs_on_sale() == 0 || (tpGoods.getIs_virtual() == 1 && tpGoods.getVirtual_indate() <= Calendar.getInstance().getTimeInMillis())){
				jsonObj.put("status", 0);
				jsonObj.put("msg", "此商品不存在或者已下架");
				return jsonObj.toString();
			}
			good = tpGoodsService.getGoodsJson(tpGoods);
			List<TpGoodsImages> tpGoodsImagesList = tpGoodsService.findByGoodsId(tpGoods.getGoods_id());
			int i = 0;
			JSONArray jsonArray = new JSONArray();
			for (TpGoodsImages tpGoodsImages : tpGoodsImagesList) {
				JSONObject jsonObject = new JSONObject();
				JSONObject jsonObject1 = new JSONObject();
				jsonObject.put("goods_id", tpGoodsImages.getGoods_id());
				jsonObject.put("image_url", tpGoodsImages.getImage_url());
				jsonObject.put("img_id", tpGoodsImages.getImg_id());
				jsonObject1.put("\""+ i +"\"", jsonObject);
				jsonArray.add(jsonObject1);
				i++;
			}
			data1.put("goods_images_list", jsonArray);
			int collect = tpGoodsService.getCountByGoodsOrUser(tpGoods.getGoods_id(), tpUsers.getUser_id());
			data1.put("collect", collect);
			int goods_collect_count = tpGoodsService.getCountByGoodsOrUser(tpGoods.getGoods_id(), null);
			data1.put("goods_collect_count", goods_collect_count);
			data1.put("goods", good);
			jsonObj.put("result", data1);
			jsonObj.put("status", 1);
			jsonObj.put("msg", "请求成功!");
		}
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
			int num = tpGoodsService.getCountByGoodsOrUser(tpGoods.getGoods_id(), tpUsers.getUser_id());
			if (num >= 1){
				jsonObj.put("status", 3);
				jsonObj.put("msg", "商品已收藏");
				return jsonObj.toString();
			}
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
	public @ResponseBody String carousel(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		JSONObject data1 = new JSONObject();
		JSONObject good = new JSONObject();
		jsonObj.put("status", 0);
		jsonObj.put("msg", "请求失败，请稍后再试");
		TpUsers tpUsers = initUser(request);
		if (null == tpUsers) {
			jsonObj.put("status", -2);
			jsonObj.put("msg", "token失效");
			return jsonObj.toString();
		}
		String[] goodsId = {"144", "144", "144"};
		for (int i = 0; i < goodsId.length; i++) {
			TpGoods tpGoods = tpGoodsService.findById(Integer.parseInt(goodsId[i]));
			if (null != tpGoods) {
				if (tpGoods.getIs_on_sale() == 0 || (tpGoods.getIs_virtual() == 1 && tpGoods.getVirtual_indate() <= Calendar.getInstance().getTimeInMillis())) {
					jsonObj.put("status", 0);
					jsonObj.put("msg", "此商品不存在或者已下架");
					return jsonObj.toString();
				}
				good = tpGoodsService.getGoodsJson(tpGoods);
				List<TpGoodsImages> tpGoodsImagesList = tpGoodsService.findByGoodsId(tpGoods.getGoods_id());
				int j = 0;
				JSONArray jsonArray = new JSONArray();
				for (TpGoodsImages tpGoodsImages : tpGoodsImagesList) {
					JSONObject jsonObject = new JSONObject();
					JSONObject jsonObject1 = new JSONObject();
					jsonObject.put("goods_id", tpGoodsImages.getGoods_id());
					jsonObject.put("image_url", tpGoodsImages.getImage_url());
					jsonObject.put("img_id", tpGoodsImages.getImg_id());
					jsonObject1.put("\"" + j + "\"", jsonObject);
					jsonArray.add(jsonObject1);
					j++;
				}
				data1.put("goods_images_list", jsonArray);
				int collect = tpGoodsService.getCountByGoodsOrUser(tpGoods.getGoods_id(), tpUsers.getUser_id());
				data1.put("collect", collect);
				int goods_collect_count = tpGoodsService.getCountByGoodsOrUser(tpGoods.getGoods_id(), null);
				data1.put("goods_collect_count", goods_collect_count);
				data1.put("goods", good);
				jsonObj.put("result", data1);
				jsonObj.put("status", 1);
				jsonObj.put("msg", "请求成功!");
			}
		}
		return jsonObj.toString();
	}
	//TODO  商品规格
}
