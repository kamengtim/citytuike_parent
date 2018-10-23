package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.TpAdService;
import com.citytuike.service.TpSmsLogService;
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
import java.sql.Timestamp;
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

//TODO  1. 材料上传
//TODO 2. 获取纸巾广告弹出
//TODO 3. 获取广告列表
//TODO 4. 物料下载百度云
    /**
     * @return
     * 获取广告的地区和设备数
     */
    @RequestMapping(value="/regionData",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取广告的地区和设备数", notes = "获取广告的地区和设备数")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String regionData(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String regions_id = jsonO.getString("regions_id");
        if (null == regions_id || "".equals(regions_id)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 热门城市
     */
    @RequestMapping(value="/getHotCities",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取广告热门城市", notes = "获取广告热门城市")
    public @ResponseBody String getHotCities(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 广告申请接口
     */
    @RequestMapping(value="/apply",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请接口", notes = "广告申请接口")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String apply(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer type = jsonO.getInteger("type");
        Integer trade = jsonO.getInteger("trade");
        Integer days = jsonO.getInteger("days");
        String url = jsonO.getString("url");
        String desc = jsonO.getString("desc");
        String regions = jsonO.getString("regions");
        String materials = jsonO.getString("materials");
        Integer side = jsonO.getInteger("side");
        Integer launch_num = jsonO.getInteger("launch_num");
        String launch_date = jsonO.getString("launch_date");
        if (null == type || "".equals(type) || null == days || "".equals(days)
                || null == regions || "".equals(regions)|| null == materials || "".equals(materials)){
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
        TpAdApply tpAdApply = new TpAdApply();
        tpAdApply.setOrder_sn(Util.getDateAndNumber(16));
        tpAdApply.setUser_id(tpUsers.getUser_id());
        tpAdApply.setCate(type);
        tpAdApply.setTrade_id(trade);
        tpAdApply.setLaunch_at(Util.strToDateLong(launch_date));
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
        tpAdApply.setOrder_amount(amount);
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
                            jsonObj.put("status", 0);
                            jsonObj.put("msg", "请求失败，请稍后再试");
                            return jsonObj.toString();
                        }
                    }else{
                        jsonObj.put("status", 0);
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
                        jsonObj.put("status", 0);
                        jsonObj.put("msg", "请求失败，请稍后再试");
                        return jsonObj.toString();
                    }
                }
            }
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }
    /**
     * @return
     * 取消广告申请
     */
    @RequestMapping(value="/cancelApply",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "取消广告申请", notes = "取消广告申请")
    public @ResponseBody String cancelApply(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String apply_sn = jsonO.getString("apply_sn");
        if (null == apply_sn || "".equals(apply_sn)){
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
        TpAdApply tpAdApply = tpAdService.findAdApplyByOrderSnAndStatus(apply_sn, tpUsers.getUser_id(), "apply");
        if (null != tpAdApply){
            if (tpAdApply.getPay_status() == 1){
                TpUserWallet tpUserWallet = tpUsersService.findWalletByUserId(tpUsers.getUser_id());
                if (null != tpUserWallet){
                    double balance = tpUserWallet.getBalance();
                    double paidAmount = tpUserWallet.getPaid_amount();
                    TpUserFinance tpUserFinance = new TpUserFinance();
                    tpUserFinance.setUser_id(tpUsers.getUser_id());
                    tpUserFinance.setChange_type(1);
                    tpUserFinance.setAmount(tpAdApply.getOrder_amount());
                    tpUserFinance.setUser_balance(balance+tpAdApply.getOrder_amount());
                    tpUserFinance.setBiz_sign("ad_apply_cancel");
                    tpUserFinance.setBiz_sn(tpAdApply.getOrder_sn());
                    tpUserFinance.setRemark("用户取消广告申请，退还至余额");
                    tpUserFinance.setCreated_at(new Timestamp(new Date().getTime()));
                    int updataApplyBystate = tpAdService.updataApplyBystate(tpAdApply.getId(), "cancel");
                    if (updataApplyBystate > 0){
                        int insertUserFinance = tpUsersService.insertUserFinance(tpUserFinance);
                        if (insertUserFinance > 0){
                            int updataUserWallet = tpUsersService.updateUserWalletBalanceAndOrderAmount(balance+tpAdApply.getOrder_amount(),
                                    paidAmount-tpAdApply.getOrder_amount(), tpUsers.getUser_id());
                            if (updataUserWallet > 0){
                                jsonObj.put("result", data);
                                jsonObj.put("status", 1);
                                jsonObj.put("msg", "取消成功");
                            }
                        }
                    }
                }
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/trade",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告行业数据接口", notes = "广告行业数据接口")
    public @ResponseBody String trade(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
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
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 广告余额充值
     */
    @RequestMapping(value="/topUp",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告余额充值", notes = "广告余额充值")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String topUp(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        float amount = jsonO.getFloat("amount");
        if (amount > 0.0 || "".equals(amount)){
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
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }

        return jsonObj.toString();
    }
    @RequestMapping(value="/topUpList",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告充值订单列表", notes = "广告充值订单列表")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String topUpList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer page = jsonO.getInteger("page");
        Integer size = jsonO.getInteger("size");
        if (null == page|| "".equals(page) || null == size|| "".equals(size)){
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
        LimitPageList limitPageList = tpAdService.getTopUpLimitPageList(tpUsers.getUser_id(), page, size);
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
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");

        return jsonObj.toString();
    }

    /**
     * @return
     * 广告申请订单列表（消费列表）
     */
    @RequestMapping(value="/applyList",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请订单列表（消费列表）", notes = "广告申请订单列表（消费列表）")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String applyList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        Integer page = jsonO.getInteger("page");
        Integer size = jsonO.getInteger("size");
        if (null == page|| "".equals(page) || null == size|| "".equals(size)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            return jsonObj.toString();
        }
        LimitPageList limitPageList = tpAdService.getApplyLimitPageList(tpUsers.getUser_id(), page, size);
        JSONArray jsonArray = new JSONArray();
        for (TpAdApply tpAdApply : (List<TpAdApply>)limitPageList.getList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apply_sn", tpAdApply.getOrder_sn());
            jsonObject.put("amount", tpAdApply.getOrder_amount());
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
        jsonObj.put("status", 1);
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
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpUserWallet tpUserWallet = tpUsersService.findWalletByUserId(tpUsers.getUser_id());
        if (null != tpUserWallet){
            data.put("balance", tpUserWallet.getBalance());
            jsonObj.put("result", data);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @return
     */
    @RequestMapping(value="/applyDetail",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请详情", notes = "广告申请详情")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String applyDetail(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String order_sn = jsonO.getString("order_sn");
        if (null == order_sn || "".equals(order_sn)){
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
            data.put("amount",tpAdApply.getOrder_amount());
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
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 广告申请订单结算
     */
    @RequestMapping(value="/applySettle",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "广告申请订单余额结算", notes = "广告申请订单余额结算")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String applySettle(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String order_sn = jsonO.getString("order_sn");
        String verify_code = jsonO.getString("verify_code");
        if (null == order_sn || "".equals(order_sn) || null == verify_code || "".equals(verify_code)){
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
                tpUserWallet1.setBalance(tpUserWallet.getBalance()-tpAdApply.getOrder_amount());
                tpUserWallet1.setUpdated_at(new Date());
                int updateWalletResult = tpUsersService.updateUserWalletBalance(tpUserWallet1);
                if (updateWalletResult > 0){
                    TpUserFinance tpUserFinance = new TpUserFinance();
                    tpUserFinance.setUser_id(tpUsers.getUser_id());
                    tpUserFinance.setChange_type(2);
                    tpUserFinance.setAmount(tpAdApply.getOrder_amount());
                    tpUserFinance.setUser_balance(tpUserWallet.getBalance()-tpAdApply.getOrder_amount());
                    tpUserFinance.setBiz_sign("ad_apply");
                    tpUserFinance.setBiz_sn(tpAdApply.getOrder_sn());
                    tpUserFinance.setRemark("广告申请");
                    tpUserFinance.setCreated_at(new Date());
                    int resultFinance = tpUsersService.insertUserFinance(tpUserFinance);
                    if (resultFinance <= 0){
                        jsonObj.put("status", 0);
                        jsonObj.put("msg", "请求失败，请稍后再试");
                        return jsonObj.toString();
                    }
                }
            }
            //更新订单
            TpAdApply tpAdApply1 = new TpAdApply();
            tpAdApply1.setOrder_sn(tpAdApply.getOrder_sn());
            tpAdApply1.setPay_status(1);
            tpAdApply1.setPaid_at(new Date());
            tpAdApply1.setUpdated_at(new Date());
            int updateAdApply = tpAdService.updateAdApply(tpAdApply1);
            if (updateAdApply > 0){
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
                return jsonObj.toString();
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/deviceAdList",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "设备广告列表（新）", notes = "设备广告列表（新）")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String deviceAdList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String imei = jsonO.getString("imei");
        String end_date = jsonO.getString("end_date");
        if (null == imei || "".equals(imei) || null == end_date || "".equals(end_date)){
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

        return jsonObj.toString();
    }

    //TODO 16. 设备广告列表（新）
    //TODO 17. 广告钱包提现
    //TODO 18. 订单改价
    //TODO 19. 广告订单列表

}
