package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.TpAdService;
import com.citytuike.service.TpSmsLogService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Ad")
public class AdController extends BaseController{

    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpAdService tpAdService;
    @Autowired
    private TpSmsLogService tpSmsLogService;


    /**
     * @param regions_id
     * @return
     * 获取广告的地区和设备数
     */
    @RequestMapping(value="/regionData",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取广告的地区和设备数", notes = "获取广告的地区和设备数")
    public @ResponseBody String uploadMaterial(HttpServletRequest request,
                                               @RequestParam(required=true) String regions_id){
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        String[] ids = regions_id.split(",");
        for (String id: ids) {
            TpRegion tpregin = tpUsersService.findRegionById(Integer.parseInt(id));
            if (null != tpregin) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                jsonObject.put("id", tpregin.getId());
                jsonObject.put("name", tpregin.getName());
                jsonObject.put("initials", tpregin.getInitials());
                jsonObject.put("num", tpregin.getNum());
                List<TpRegion> tpRegionList1 = tpUsersService.findRegionByParentId(tpregin.getId());
                for (TpRegion tpRegion2: tpRegionList1) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", tpRegion2.getId());
                    jsonObject2.put("name", tpRegion2.getName());
                    jsonObject2.put("initials", tpRegion2.getInitials());
                    jsonObject2.put("num", tpRegion2.getNum());
                    jsonObject2.put("img", "");
                    int pageAvg = tpAdService.getPageAvg(tpRegion2);
                    jsonObject2.put("page_avg", pageAvg);
                    jsonArray1.add(jsonObject2);
                }
                jsonObject.put("children", jsonArray1);
                data.add(jsonObject);
            }
        }

        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 热门城市
     */
    @RequestMapping(value="/getHotCities",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "热门城市", notes = "热门城市")
    public @ResponseBody String getHotCities(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        String[] ids = {"1", "338", "10543", "28241", "28558", "31929"};
        for (String id: ids) {
            TpRegion tpregin = tpUsersService.findRegionById(Integer.parseInt(id));
            if (null != tpregin) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                jsonObject.put("area_id", tpregin.getId());
                jsonObject.put("area_name", tpregin.getName());
                jsonObject.put("initials", tpregin.getInitials());
                jsonObject.put("area_num", tpregin.getNum());
                List<TpRegion> tpRegionList1 = tpUsersService.findRegionByParentId(tpregin.getId());
                for (TpRegion tpRegion2: tpRegionList1) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", tpregin.getId());
                    jsonObject2.put("name", tpregin.getName());
                    jsonObject2.put("initials", tpregin.getInitials());
                    jsonObject2.put("num", tpregin.getNum());
                    jsonObject2.put("parent_id", tpregin.getParent_id());
                    jsonObject2.put("level", tpregin.getLevel());
                    jsonArray1.add(jsonObject2);
                }
                jsonObject.put("area_value", jsonArray1);
                data.put(id, jsonObject);
            }
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param type
     * @param trade
     * @param days
     * @param url
     * @param desc
     * @param regions
     * @param materials
     * @param side
     * @param launch_num
     * @return
     * 广告申请接口
     */
    @RequestMapping(value="/apply",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请接口", notes = "广告申请接口")
    public @ResponseBody String apply(HttpServletRequest request,
                                      @RequestParam(required=true) Integer type,
                                      @RequestParam(required=true) Integer trade,
                                      @RequestParam(required=true) Integer days,
                                      @RequestParam(required=true) String url,
                                      @RequestParam(required=true) String desc,
                                      @RequestParam(required=true) String regions,
                                      @RequestParam(required=true) String materials,
                                      @RequestParam(required=true) Integer side,
                                      @RequestParam(required=false) Integer launch_num){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpAdApply tpAdApply = new TpAdApply();
        tpAdApply.setOrder_sn(Util.getDateAndNumber(16));
        tpAdApply.setUser_id(tpUsers.getUser_id());
        tpAdApply.setCate(type);
        tpAdApply.setTrade_id(trade);
        tpAdApply.setDays(days);
        tpAdApply.setUrl(url);
        tpAdApply.setDescribe(desc);
        tpAdApply.setSide(side);
        tpAdApply.setLaunch_num(launch_num);
        //纸巾计算价格单价是一面0.1两面0.2，乘于数量
        double amount = 0.00;
        if (type==3){
            if (side==1){
                amount = launch_num * 0.1;
            }else if (side==2){
                amount = launch_num * 0.2;
            }
        }else{
            amount = 0.01;
        }
        tpAdApply.setAmount(amount);
        tpAdApply.setState("apply");
        tpAdApply.setPay_status(0);
        tpAdApply.setCreated_at(new Date());
        tpAdApply.setUpdated_at(new Date());
        int resultAdApply = tpAdService.insertAdApply(tpAdApply);
        if (resultAdApply > 0){
            TpAdApply adApply = tpAdService.findAdApplyByOrderSn(tpAdApply.getOrder_sn());
            if (null != adApply){
                String[] regionsArray = regions.split("|");
                for (String region : regionsArray) {
                    String[] regionList = region.split(",");
                    if (regionList.length > 2){
                        String regionId = regionList[0];
                        String num = regionList[1];
                        TpAdApplyRegion tpAdApplyRegion = new TpAdApplyRegion();
                        tpAdApplyRegion.setApply_id(adApply.getId());
                        tpAdApplyRegion.setRegion_id(Integer.parseInt(regionId));
                        tpAdApplyRegion.setNum(Integer.parseInt(num));
                        int resultRegion = tpAdService.insertAdApplyRegion(tpAdApplyRegion);
                        if (resultRegion <= 0){
                            jsonObj.put("status", "0");
                            jsonObj.put("msg", "请求失败，请稍后再试");
                            return jsonObj.toString();
                        }
                    }else{
                        jsonObj.put("status", "0");
                        jsonObj.put("msg", "请求失败，请稍后再试");
                        return jsonObj.toString();
                    }
                }
                String[] materialsArray = materials.split(",");
                for (String material : materialsArray) {
                    TpAdApplyMaterial tpAdApplyMaterial = new TpAdApplyMaterial();
                    tpAdApplyMaterial.setApply_id(adApply.getId());
                    tpAdApplyMaterial.setDisplay(1);
                    tpAdApplyMaterial.setUrl(material);
                    int resultMaterial = tpAdService.insertAdApplyMaterial(tpAdApplyMaterial);
                    if (resultMaterial <= 0){
                        jsonObj.put("status", "0");
                        jsonObj.put("msg", "请求失败，请稍后再试");
                        return jsonObj.toString();
                    }
                }
            }
            jsonObj.put("status", "1");
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 广告行业数据接口
     */
    @RequestMapping(value="/trade",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告行业数据接口", notes = "广告行业数据接口")
    public @ResponseBody String trade(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        List<TpAdTrade> tpAdTradeList =  tpAdService.findAllAdTrade();
        for (TpAdTrade tpAdtrade : tpAdTradeList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tpAdtrade.getTrade_id());
            jsonObject.put("name", tpAdtrade.getName());
            JSONArray jsonArray = new JSONArray();
            List<TpAdTrade> tpAdTradeList1 =  tpAdService.findAllAdTradeByParentId(tpAdtrade.getTrade_id());
            for (TpAdTrade tpadtrade : tpAdTradeList1) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", tpadtrade.getTrade_id());
                jsonObject1.put("name", tpadtrade.getName());
                JSONArray jsonArray1 = new JSONArray();
                List<TpAdTrade> tpAdTradeList2 =  tpAdService.findAllAdTradeByParentId(tpadtrade.getTrade_id());
                for (TpAdTrade tpadtrade1 : tpAdTradeList2) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", tpadtrade1.getTrade_id());
                    jsonObject2.put("name", tpadtrade1.getName());
                    jsonObject2.put("children", "");
                    jsonArray1.add(jsonObject2);
                }
                jsonObject1.put("children", jsonArray1);
                jsonArray.add(jsonObject1);
            }
            jsonObject.put("children", jsonArray);
            data.add(jsonObject);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param amount
     * @return
     * 广告余额充值
     */
    @RequestMapping(value="/topUp",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告余额充值", notes = "广告余额充值")
    public @ResponseBody String topUp(HttpServletRequest request,
                                      @RequestParam(required=true) float amount){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpAdTopUp tpAdTopUp = new TpAdTopUp();
        tpAdTopUp.setUser_id(tpUsers.getUser_id());
        tpAdTopUp.setOrder_sn(Util.getDateAndNumber(16));
        tpAdTopUp.setOrder_amount(amount);
        tpAdTopUp.setPay_status(0);
        tpAdTopUp.setCreated_at(new Date());
        tpAdTopUp.setUpdated_at(new Date());
        int resultTopUp = tpAdService.insertAdTopUp(tpAdTopUp);
        if (resultTopUp > 0){
            data.put("order_sn", tpAdTopUp.getOrder_sn());
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "请求成功!");
        }

        return jsonObj.toString();
    }
    @RequestMapping(value="/topUpList",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告充值订单列表", notes = "广告充值订单列表")
    public @ResponseBody String topUpList(HttpServletRequest request,
                                      @RequestParam(required=true) int page,@RequestParam(required=true) int size){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = tpAdService.getTopUpLimitPageList(page, size);
        JSONArray jsonArray = new JSONArray();
        for (TpAdTopUp tpAdTopUp : (List<TpAdTopUp>)limitPageList.getList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_sn", tpAdTopUp.getOrder_sn());
            jsonObject.put("amount", tpAdTopUp.getOrder_amount());
            jsonObject.put("remark", tpAdTopUp.getRemark());
            jsonObject.put("created_at", tpAdTopUp.getCreated_at());
            jsonArray.add(jsonObject);
        }
        data.put("list", jsonArray);
        data.put("cur", page);
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @param page
     * @param size
     * @return
     * 广告申请订单列表（消费列表）
     */
    @RequestMapping(value="/applyList",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请订单列表（消费列表）", notes = "广告申请订单列表（消费列表）")
    public @ResponseBody String applyList(HttpServletRequest request,
                                      @RequestParam(required=true) int page,@RequestParam(required=true) int size){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        LimitPageList limitPageList = tpAdService.getApplyLimitPageList(page, size);
        JSONArray jsonArray = new JSONArray();
        for (TpAdApply tpAdApply : (List<TpAdApply>)limitPageList.getList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apply_sn", tpAdApply.getOrder_sn());
            jsonObject.put("amount", tpAdApply.getAmount());
            jsonObject.put("created_at", tpAdApply.getCreated_at());
            jsonObject.put("cate", tpAdApply.getCate());
            //1屏幕广告|2二维码|3纸巾|4APP广告
            switch (tpAdApply.getCate()){
                case 1:
                    jsonObject.put("cate_str", "屏幕广告");
                    break;
                case 2:
                    jsonObject.put("cate_str", "二维码广告");
                    break;
                case 3:
                    jsonObject.put("cate_str", "纸巾广告");
                    break;
                case 4:
                    jsonObject.put("cate_str", "APP广告");
                    break;
            }
            jsonArray.add(jsonObject);
        }
        data.put("list", jsonArray);
        data.put("cur", page);
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @return
     * 广告用户可用余额
     */
    @RequestMapping(value="/userBalance",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告用户可用余额", notes = "广告用户可用余额")
    public @ResponseBody String userBalance(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpUserWallet tpUserWallet = tpUsersService.findWalletByUserId(tpUsers.getUser_id());
        if (null != tpUserWallet){
            data.put("balance", tpUserWallet.getBalance());
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @param order_sn
     * @return
     */
    @RequestMapping(value="/applyDetail",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请详情", notes = "广告申请详情")
    public @ResponseBody String applyDetail(HttpServletRequest request, @RequestParam(required=true) String order_sn){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpAdApply tpAdApply = tpAdService.findAdApplyByOrderSn(order_sn);
        if (null != tpAdApply){
            data.put("order_sn",tpAdApply.getOrder_sn());
            data.put("cate",tpAdApply.getCate());
            switch (tpAdApply.getCate()){
                case 1:
                    data.put("cate_str", "屏幕广告");
                    break;
                case 2:
                    data.put("cate_str", "二维码广告");
                    break;
                case 3:
                    data.put("cate_str", "纸巾广告");
                    break;
                case 4:
                    data.put("cate_str", "APP广告");
                    break;
            }
            data.put("trade_id",tpAdApply.getTrade_id());
            TpAdTrade tpAdTrade = tpAdService.findTradeById(tpAdApply.getTrade_id());
            if (null != tpAdTrade){
                data.put("trade_str",tpAdTrade.getName());
            }else {
                data.put("trade_str","未知行业");
            }
            data.put("days",tpAdApply.getDays());
            data.put("url",tpAdApply.getUrl());
            data.put("describe",tpAdApply.getDescribe());
            data.put("side",tpAdApply.getSide());
            data.put("launch_num",tpAdApply.getLaunch_num());
            data.put("amount",tpAdApply.getAmount());
            List<TpAdApplyMaterial> tpAdApplyMaterialList = tpAdService.findAdApplyMaterialByApplyId(tpAdApply.getId());
            List<String> materials = new ArrayList<>();
            for (TpAdApplyMaterial tp : tpAdApplyMaterialList) {
                materials.add(tp.getUrl());
            }
            data.put("materials",materials);
            JSONArray jsonArray = new JSONArray();
            List<TpAdApplyRegion> tpAdApplyRegionList = tpAdService.findAdApplyRegionByApplyId(tpAdApply.getId());
            for (TpAdApplyRegion tpr : tpAdApplyRegionList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("region_id", tpr.getRegion_id());
                jsonObject.put("num", tpr.getNum());
                TpRegion tpRegion = tpUsersService.findRegionById(tpr.getRegion_id());
                if (null != tpRegion){
                    TpRegion tpRegion1 = tpUsersService.findRegionById(tpRegion.getParent_id());
                    if (null != tpRegion1){
                        jsonObject.put("region_name", tpRegion1.getName() + " " + tpRegion.getName());
                    }
                }
                jsonArray.add(jsonObject);
            }
            data.put("regions",jsonArray);
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @param order_sn
     * @param launch_date
     * @param verify_code
     * @return
     * 广告申请订单结算
     */
    @RequestMapping(value="/applySettle",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请订单结算", notes = "广告申请订单结算")
    public @ResponseBody String applySettle(HttpServletRequest request,
                                            @RequestParam(required=true) String order_sn,
                                            @RequestParam(required=true) String launch_date,
                                            @RequestParam(required=true) String verify_code){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpSmsLog tpSmsLog = tpSmsLogService.findOneByMobileAndCode(tpUsers.getMobile(), verify_code);
		/*if (null == tpSmsLog) {
			jsonObj.put("status", "3");
			jsonObj.put("msg", "验证码错误!");
			return jsonObj.toString();
		}else {
			if (tpSmsLog.getStatus() == 1) {
				jsonObj.put("status", "4");
				jsonObj.put("msg", "验证码已使用!");
				return jsonObj.toString();
			}
		}*/
        TpAdApply tpAdApply = tpAdService.findAdApplyByOrderSn(order_sn);
        if (null != tpAdApply){
            //扣钱
            TpUserWallet tpUserWallet = tpUsersService.findWalletByUserId(tpUsers.getUser_id());
            if (null != tpUserWallet){
                TpUserWallet tpUserWallet1 = new TpUserWallet();
                tpUserWallet1.setId(tpUserWallet.getId());
                tpUserWallet1.setBalance(tpUserWallet.getBalance()-tpAdApply.getAmount());
                tpUserWallet1.setUpdated_at(new Date());
                int updateWalletResult = tpUsersService.updateUserWalletBalance(tpUserWallet1);
                if (updateWalletResult > 0){
                    TpUserFinance tpUserFinance = new TpUserFinance();
                    tpUserFinance.setUser_id(tpUsers.getUser_id());
                    tpUserFinance.setChange_type(2);
                    tpUserFinance.setAmount(tpAdApply.getAmount());
                    tpUserFinance.setUser_balance(tpUserWallet.getBalance()-tpAdApply.getAmount());
                    tpUserFinance.setBiz_sign("ad_apply");
                    tpUserFinance.setBiz_sn(tpAdApply.getOrder_sn());
                    tpUserFinance.setRemark("广告申请");
                    tpUserFinance.setCreated_at(new Date());
                    int resultFinance = tpUsersService.insertUserFinance(tpUserFinance);
                    if (resultFinance <= 0){
                        jsonObj.put("status", "0");
                        jsonObj.put("msg", "请求失败，请稍后再试");
                        return jsonObj.toString();
                    }
                }
            }
            //更新订单
            TpAdApply tpAdApply1 = new TpAdApply();
            tpAdApply1.setOrder_sn(tpAdApply.getOrder_sn());
            tpAdApply1.setLaunch_at(Util.strToDateLong(launch_date));
            tpAdApply1.setPay_status(1);
            tpAdApply1.setPaid_at(new Date());
            tpAdApply1.setUpdated_at(new Date());
            int updateAdApply = tpAdService.updateAdApply(tpAdApply1);
            if (updateAdApply > 0){
                jsonObj.put("status", "1");
                jsonObj.put("msg", "请求成功!");
                return jsonObj.toString();
            }
        }
        return jsonObj.toString();
    }

}
