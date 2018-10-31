package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.BusinessService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.GeoHashUtil;
import com.citytuike.util.Util;
import com.qiniu.util.Json;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/Business")
public class BusinessController extends BaseController{
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private BusinessService businessService;

    /**
     * @return
     * 添加商家
     */
    @RequestMapping(value="/businessUp",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "添加商家", notes = "添加商家")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String businessUp(HttpServletRequest request) {
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
        Integer business_type = jsonO.getInteger("business_type");
        String business_name = jsonO.getString("business_name");
        float cus_nums = jsonO.getFloat("cus_nums");
        String business_start_time = jsonO.getString("business_start_time");
        String business_end_time = jsonO.getString("business_end_time");
        String name = jsonO.getString("name");
        String mobile = jsonO.getString("mobile");
        String location_area = jsonO.getString("location_area");
        String address = jsonO.getString("address");
        String xpoint = jsonO.getString("xpoint");
        String ypoint = jsonO.getString("ypoint");
        String images = jsonO.getString("image");
        if (null == business_type || "".equals(business_type) || null == business_name || "".equals(business_name)
                || cus_nums > 0.00 || "".equals(cus_nums) || null == business_start_time || "".equals(business_start_time)
                || null == business_end_time || "".equals(business_end_time) || null == name || "".equals(name)
                || null == mobile || "".equals(mobile) || null == location_area || "".equals(location_area)
                || null == address || "".equals(address) || null == xpoint || "".equals(xpoint)
                || null == ypoint || "".equals(ypoint) || null == images || "".equals(images)){
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
        TpBusinessShare tpBusinessShare1 = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare1){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "您已经上传过了");
            return jsonObj.toString();
        }
        TpBusinessShare tpBusinessShare = new TpBusinessShare();
        tpBusinessShare.setBusinessType(business_type);
        tpBusinessShare.setBusinessName(business_name);
        tpBusinessShare.setName(name);
        tpBusinessShare.setCusNums(cus_nums);
        tpBusinessShare.setBusinessStartTime(Integer.parseInt(business_start_time));
        tpBusinessShare.setBusinessEndTime(Integer.parseInt(business_end_time));
        tpBusinessShare.setMobile(mobile);
        tpBusinessShare.setXpoint(xpoint);
        tpBusinessShare.setYpoint(ypoint);
        tpBusinessShare.setLocationArea(location_area);
        tpBusinessShare.setAddress(address);
        tpBusinessShare.setTag("");
        tpBusinessShare.setCreateTime((int)Calendar.getInstance().getTimeInMillis());
        String geohashStr = GeoHashUtil.encode(Double.parseDouble(xpoint), Double.parseDouble(ypoint));
        tpBusinessShare.setGeohash(geohashStr);
        tpBusinessShare.setTuijian(0);
        tpBusinessShare.setDongjie(0.00);
        tpBusinessShare.setFlag(0);
        tpBusinessShare.setUserId(tpUsers.getUser_id());
        int result = businessService.insertBusinessShare(tpBusinessShare);
        if (result > 0){
            String[] image = images.split(",");
            for (int i = 0; i< image.length; i++){
                TpBusinessImages tpBusinessImages = new TpBusinessImages();
                tpBusinessImages.setBusinessId(tpBusinessShare.getId());
                tpBusinessImages.setImage(image[i]);
                tpBusinessImages.setImgSort(i);
                int resultImg = businessService.insertBusinessImages(tpBusinessImages);
                if (resultImg <= 0){
                    jsonObj.put("status", 0);
                    jsonObj.put("msg", "请求失败，请稍后再试");
                    return jsonObj.toString();
                }
            }
            data.put("business_id", tpBusinessShare.getId());
            jsonObj.put("result", data);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 获取商家类型
     */
    @RequestMapping(value="/getBusinessType",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取商家类型", notes = "获取商家类型")
    public @ResponseBody String getBusinessType(HttpServletRequest request) {
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
        List<TpBusinessType> tpBusinessTypeList = businessService.findAllBusinessType();
        for (TpBusinessType tpbusinessTpye : tpBusinessTypeList) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", tpbusinessTpye.getId());
            jsonObject1.put("business_name", tpbusinessTpye.getBusinessName());
            jsonObject1.put("create_time", tpbusinessTpye.getCreateTime());
            data.add(jsonObject1);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 评论
     */
    @RequestMapping(value="/businessEva",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "评论", notes = "评论")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    public @ResponseBody String businessEva(HttpServletRequest request) {
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
        Integer business_id = jsonO.getInteger("business_id");
        Integer stars = jsonO.getInteger("stars");
        String message = jsonO.getString("message");
        String tag = jsonO.getString("tag");
        if (null == business_id || "".equals(business_id) || null == stars || "".equals(stars)
            || null == message || "".equals(message) || null == tag || "".equals(tag)){
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
        List<TpBusinessType> tpBusinessTypeList = businessService.findAllBusinessCommentByUserId(tpUsers.getUser_id(), business_id);
        if (tpBusinessTypeList.size() > 0){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "您已经评论过了");
            return jsonObj.toString();
        }
        TpBusinessComment tpBusinessComment = new TpBusinessComment();
        tpBusinessComment.setBusinessId(business_id);
        tpBusinessComment.setStars(stars);
        tpBusinessComment.setUserId(tpUsers.getUser_id());
        tpBusinessComment.setAddTime((int)Calendar.getInstance().getTimeInMillis());
        tpBusinessComment.setTag(tag);
        tpBusinessComment.setMessage(message);
        int result = businessService.insertBusinessComment(tpBusinessComment);
        if (result > 0){
            TpBusinessShare tpBusinessShare = businessService.findBusinessShareByTag(business_id, tag);
            if (null == tpBusinessShare){
                //更新门店标签tag
                String tags = tpBusinessShare.getTag() + "," + tag;
                int updataTage = businessService.updataShareByTag(business_id, tags);
                if (updataTage < 0){
                    jsonObj.put("status", 0);
                    jsonObj.put("msg", "评论失败，请稍后再试");
                    return jsonObj.toString();
                }
            }
            data.put("business_comment_id", tpBusinessComment.getId());
            jsonObj.put("result", data);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @param xpoint 用户经度
     * @param ypoint 用户纬度
     * @param business_type 商户类型1为美食2为酒店3为景点4为美容5为汽修6为KTV
     * @param num  距离精确度5大概1千米内 值越少范围越大精确度越小
     * @param tuijian 1为推荐 不传就是列表页
     * @return
     * 附近共享列表
     */
    @RequestMapping(value="/nearByBusiness",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "附近共享列表", notes = "附近共享列表")
    public @ResponseBody String nearByBusiness(HttpServletRequest request,
                                            @RequestParam(required=true) int xpoint,
                                            @RequestParam(required=true) int ypoint,
                                            @RequestParam(required=true) int business_type,
                                            @RequestParam(required=true) int num,
                                            @RequestParam(required=false) int tuijian) {
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
        String geohashStr = GeoHashUtil.encode(Double.parseDouble(xpoint+""), Double.parseDouble(ypoint+""));
        String geohash = geohashStr.substring(num);
        List<TpBusinessShare> tpBusinessShareList = businessService.findNearByBusiness(business_type, geohash, tuijian);
        for (TpBusinessShare tpbusinessShare : tpBusinessShareList) {
            JSONObject jsonObject1 = businessService.getBusinessShareJson(tpbusinessShare, xpoint, ypoint);
            data.add(jsonObject1);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param xpoint
     * @param ypoint
     * @param id
     * @return
     * 详情页
     */
    @RequestMapping(value="/businessDetails",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "详情页", notes = "详情页")
    public @ResponseBody String businessDetails(HttpServletRequest request,
                                            @RequestParam(required=true) int xpoint,
                                            @RequestParam(required=true) int ypoint,
                                            @RequestParam(required=true) int id) {
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
        TpBusinessShare tpbusinessShare = businessService.findBusinessShareById(id);
        if (null != tpbusinessShare){
            data = businessService.getBusinessShareJson(tpbusinessShare, xpoint, ypoint);
            List<TpBusinessComment> tpBusinessCommentList = businessService.findAllBusinessCommentByBusinessId(id);
            JSONObject commentObject = new JSONObject();
            int stars = 0;
            int count = tpBusinessCommentList.size();
            for (TpBusinessComment tpBusinessComment : tpBusinessCommentList) {
                stars += tpBusinessComment.getStars();
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id", tpBusinessComment.getId());
                jsonObject2.put("business_id", tpBusinessComment.getBusinessId());
                jsonObject2.put("stars", tpBusinessComment.getStars());
                jsonObject2.put("user_id", tpBusinessComment.getUserId());
                jsonObject2.put("parent_id", tpBusinessComment.getParentId());
                jsonObject2.put("add_time", tpBusinessComment.getAddTime());
                jsonObject2.put("tag", tpBusinessComment.getTag());
                jsonObject2.put("message", tpBusinessComment.getMessage());
                jsonObject2.put("image_desc", tpBusinessComment.getImageDesc());
                jsonObject2.put("reply_mess", tpBusinessComment.getReplyMess());
                TpUsers tpUsers1 = tpUsersService.findOneByUserId(tpBusinessComment.getUserId());
                if (null != tpUsers1) {
                    jsonObject2.put("head_pic", tpUsers1.getHead_pic());
                    jsonObject2.put("nickname", tpUsers1.getNickname());
                }
                commentObject.put(tpBusinessComment.getId()+"" , jsonObject2);
            }
            commentObject.put("stars", stars);
            commentObject.put("count", count);
            data.put("comment", commentObject);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param id 评论ID
     * @return
     * 评论标签列表接口
     */
    @RequestMapping(value="/commentTag",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "评论标签列表接口", notes = "评论标签列表接口")
    public @ResponseBody String commentTag(HttpServletRequest request,
                                            @RequestParam(required=true) int id) {
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
        TpBusinessComment tpBusinessComment = businessService.findOneCommentById(id);
        if (null != tpBusinessComment && null != tpBusinessComment.getTag()){
            String[] tags = tpBusinessComment.getTag().split(",");
            for (String str : tags){
                TpBusinessTag tpBusinessTag = businessService.findOneTagById(Integer.parseInt(str));
                if (null != tpBusinessTag){
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", tpBusinessTag.getId());
                    jsonObject2.put("comment_id", tpBusinessTag.getCommentId());
                    jsonObject2.put("comment_tag", tpBusinessTag.getCommentTag());
                    jsonObject2.put("add_time", tpBusinessTag.getAddTime());
                    data.add(jsonObject2);
                }
            }
        }

        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param id
     * @return
     * 支付返回订单
     */
    @RequestMapping(value="/paymentBusiness",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "支付返回订单", notes = "支付返回订单")
    public @ResponseBody String paymentBusiness(HttpServletRequest request,
                                            @RequestParam(required=true) int id) {
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
        TpBusinessCashFace tpBusinessCashFace = businessService.findBusinessCashFaceById(id);
        if (null != tpBusinessCashFace){
            TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
            if (null != tpBusinessShare){
                TpBusinessOrder tpBusinessOrder = new TpBusinessOrder();
                String ordersn = "business" + Util.CreateDate() + Util.numberString(4);
                tpBusinessOrder.setOrderSn(ordersn);
                tpBusinessOrder.setUserId(tpUsers.getUser_id());
                tpBusinessOrder.setPayStatus(0);
                tpBusinessOrder.setFlag(2);
                tpBusinessOrder.setBusinessId(tpBusinessShare.getId());
                tpBusinessOrder.setAddTime((int)Calendar.getInstance().getTimeInMillis());
                tpBusinessOrder.setTotalAmount(tpBusinessCashFace.getBond());
                tpBusinessOrder.setGoodsPrice(tpBusinessCashFace.getBond());
                tpBusinessOrder.setOrderAmount(tpBusinessCashFace.getBond());
                tpBusinessOrder.setOrderStatus(0);
                tpBusinessOrder.setFaceId(tpBusinessCashFace.getId());
                int resultOrder = businessService.insertBusinessOrder(tpBusinessOrder);
                if (resultOrder > 0){
                    data.put("order_sn", ordersn);
                    jsonObj.put("result", data);
                    jsonObj.put("status", 1);
                    jsonObj.put("msg", "请求成功!");
                }
            }
        }
        return jsonObj.toString();
    }

    /**
     * @param skin
     * @param price
     * @param fullsub_price
     * @param use_flag
     * @param price_date
     * @param name
     * @param launch_address
     * @param nums
     * @param launch_date_start
     * @param launch_date_end
     * @return
     * 制作现金券
     */
    @RequestMapping(value="/setBusinessCash",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "制作现金券", notes = "制作现金券")
    public @ResponseBody String setBusinessCash(HttpServletRequest request,
                                                @RequestParam(required=true) String skin,
                                                @RequestParam(required=true) int price,
                                                 @RequestParam(required=true) int fullsub_price,
                                                @RequestParam(required=true) String use_flag,
                                                @RequestParam(required=true) int price_date,
                                                @RequestParam(required=true) String name,
                                                @RequestParam(required=true) String launch_address,
                                                @RequestParam(required=true) int nums,
                                                @RequestParam(required=true) String launch_date_start,
                                                @RequestParam(required=true) String launch_date_end) {
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
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            TpBusinessCash tpBusinessCash = new TpBusinessCash();
            tpBusinessCash.setSkin(skin);
            tpBusinessCash.setBusinessId(tpBusinessShare.getId());
            tpBusinessCash.setBusinessName(name);
            tpBusinessCash.setPrice(price);
            tpBusinessCash.setFullsubPrice(fullsub_price);
            tpBusinessCash.setPriceDate(price_date);
            tpBusinessCash.setUseFlag(use_flag);
            tpBusinessCash.setLaunchAddress(launch_address);
            TpRegion tpRegion = tpUsersService.findRegionByName(launch_address);
            if (null != tpRegion){
                tpBusinessCash.setLaunchId(tpRegion.getId());
            }
            tpBusinessCash.setLaunchDateStart(Integer.parseInt(launch_date_start));
            tpBusinessCash.setNums(nums);
            tpBusinessCash.setLaunchDateEnd(Integer.parseInt(launch_date_end));
            tpBusinessCash.setJieTime((int)Calendar.getInstance().getTimeInMillis());
            int resultCash = businessService.insertBusinessCash(tpBusinessCash);
            if (resultCash > 0){
                for (int i=0; i<nums; i++){
                    TpBusinessUseCash tpBusinessUseCash = new TpBusinessUseCash();
                    String time = Calendar.getInstance().getTimeInMillis() + "";
                    String number = "YH" + tpBusinessShare.getId() + time.substring(0,time.length()-3) + Util.numberString(3);
                    String code = Util.generateString(10);
                    tpBusinessUseCash.setUserId(tpUsers.getUser_id());
                    tpBusinessUseCash.setCashId(tpBusinessCash.getId());
                    tpBusinessUseCash.setNumber(number);
                    tpBusinessUseCash.setBusinessId(tpBusinessShare.getId());
                    tpBusinessUseCash.setFlag(0);
                    tpBusinessUseCash.setAddTime((int)Calendar.getInstance().getTimeInMillis());
                    tpBusinessUseCash.setCodes(code);
                    tpBusinessUseCash.setFlag(0);
                    tpBusinessUseCash.setType(2);
                    int userCashResult = businessService.insertBUsinessUseCash(tpBusinessUseCash);
                    if (userCashResult <= 0){
                        jsonObj.put("result", data);
                        jsonObj.put("status", -1);
                        jsonObj.put("msg", "优惠券生成失败");
                        return jsonObj.toString();
                    }
                }
                jsonObj.put("result", data);
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
            }
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 商户能否制作现金券
     */
    @RequestMapping(value="/grantCash",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "商户能否制作现金券", notes = "商户能否制作现金券")
    public @ResponseBody String grantCash(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "未缴纳");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        List<TpBusinessOrder> tpBusinessOrderList = businessService.findAllBusinessOrderByPay(tpUsers.getUser_id(),1);
        if (tpBusinessOrderList.size() > 0){
            int i = 0;
            for (TpBusinessOrder tpBusinessOrder : tpBusinessOrderList) {
                List<TpBusinessSave> tpBusinessSaveList = businessService.findAllSaveByOrder(tpBusinessOrder.getOrderSn());
                if (tpBusinessSaveList.size() > 0){
                    i = 1;
                    break;
                }
            }
            if (i == 1){
                jsonObj.put("status", 1);
                jsonObj.put("msg", "已缴纳");
                return jsonObj.toString();
            }
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 商家名
     */
    @RequestMapping(value="/getBusinessName",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "商家名", notes = "商家名")
    public @ResponseBody String getBusinessName(HttpServletRequest request) {
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
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            data.put("name", tpBusinessShare.getName());
            jsonObj.put("result", data);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }

        return jsonObj.toString();
    }

    /**
     * @return
     * 面值现金券列表
     */
    @RequestMapping(value="/cashFaceLish",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "面值现金券列表", notes = "面值现金券列表")
    public @ResponseBody String cashFaceLish(HttpServletRequest request) {
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
        List<TpBusinessCashFace> tpBusinessCashFaceList = businessService.findAllBusinessCashFace();
        for (TpBusinessCashFace tpbusinessCashFace : tpBusinessCashFaceList) {
            JSONObject object1 = new JSONObject();
            object1.put("id", tpbusinessCashFace.getId());
            object1.put("bond", tpbusinessCashFace.getBond());
            object1.put("face_cash_start", tpbusinessCashFace.getFaceCashStart());
            object1.put("face_cash_end", tpbusinessCashFace.getFaceCashEnd());
            object1.put("add_time", tpbusinessCashFace.getAddTime());
            data.add(object1);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 现金券金额说明
     */
    @RequestMapping(value="/businessTips",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "现金券金额说明", notes = "现金券金额说明")
    public @ResponseBody String businessTips(HttpServletRequest request) {
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
       /* List<TpBusinessOrder> tpBusinessOrderList = businessService.findAllBusinessOrderByPay(tpUsers.getUser_id(),1);

        TpBusinessOrder tpBusinessOrder = tpBusinessOrderList.get(tpBusinessOrderList.size());
        if (null != tpBusinessOrder.getFaceId()){
            TpBusinessCashFace tpBusinessCashFace = businessService.findBusinessCashFaceById(tpBusinessOrder.getFaceId());
            if (null != tpBusinessCashFace){
                data.put("id", tpBusinessCashFace.getId());
                data.put("bond", tpBusinessCashFace.getBond());
                data.put("face_cash_start", tpBusinessCashFace.getFaceCashStart());
                data.put("face_cash_end", tpBusinessCashFace.getFaceCashEnd());
                data.put("add_time", tpBusinessCashFace.getAddTime());
                jsonObj.put("result", data);
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
            }
        }*/
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param request
     * @param flag
     * @param page
     * @param number
     * @return
     * 我的商铺
     */
    @RequestMapping(value="/myStore",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "我的商铺", notes = "我的商铺")
    public @ResponseBody String myStore(HttpServletRequest request,
                                               @RequestParam(required=true) int flag,
                                               @RequestParam(required=true) int page,
                                               @RequestParam(required=true) String number) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");

        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }

        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            JSONObject object = new JSONObject();
//            JSONObject data = new JSONObject();
            object.put("id", tpBusinessShare.getId());
            object.put("name", tpBusinessShare.getName());
            object.put("business_name", tpBusinessShare.getBusinessName());
            object.put("location_area", tpBusinessShare.getLocationArea());
            object.put("address", tpBusinessShare.getAddress());
            List<TpBusinessImages> tpBusinessImagesList = businessService.findAllImagesByShare(tpBusinessShare.getId());
            JSONArray images = new JSONArray();
            JSONObject img = null;
            JSONObject imgAll = null;
            for (int i=0; i<tpBusinessImagesList.size(); i++){
                img = new JSONObject();
                imgAll = new JSONObject();
                img.put("image", tpBusinessImagesList.get(i).getImage());
                imgAll.put("\"" + i + "\"", img);
                images.add(img);
            }
            object.put("images", images);

            JSONArray search = new JSONArray();
            JSONArray cash = new JSONArray();
            JSONArray use = new JSONArray();
            double baozhengjin = 0.00;
            if (!"".equals(number)){
                //搜索
                LimitPageList limitPageList = businessService.getLimitPageUseCashList(null, number, page, tpBusinessShare.getId(), tpUsers.getUser_id());
                object.put("count", limitPageList.getPage().getTotalPageCount());
                object.put("page", page);
                object.put("totalPages", limitPageList.getPage().getTotalPageCount()/10);
                JSONObject userCashObj = null;
                JSONObject searchAll = null;
                JSONObject cashObj = null;
                int i = 0;
                for (TpBusinessUseCash tpbusinessUseCash : (List<TpBusinessUseCash>)limitPageList.getList()) {
                    userCashObj = new JSONObject();
                    searchAll = new JSONObject();
                    userCashObj = businessService.getBusinessUserCashJson(tpbusinessUseCash);
                    TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(tpbusinessUseCash.getCashId());
                    if(null != tpBusinessCash){
                        cashObj = businessService.getBUsinessCashJson(tpBusinessCash);
                    }
                    userCashObj.put("cash", cashObj);
                    searchAll.put("\"" + i + "\"", userCashObj);
                    search.add(searchAll);
                    i++;
                }
            }else{
                if (flag == 1){
                    //全部
                    List<TpBusinessCash> tpBusinessCashList = businessService.findAllCashByShare(tpBusinessShare.getId());
                    TpBusinessSave tpBusinessSave = null;
                    int userCount = 0;
                    int price_date = 0;
                    int miao_date = 0;
                    JSONObject cashObj = null;
                    JSONObject cashAll = null;
                    int i = 0;
                    for (TpBusinessCash tpCash : tpBusinessCashList) {
                        cashObj = new JSONObject();
                        cashAll = new JSONObject();
                        cashObj = businessService.getBUsinessCashJson(tpCash);
                        userCount = businessService.getUserCashCountByCash(tpCash.getId());
                        cashObj.put("counts", userCount);
                        price_date = tpCash.getPriceDate() * 24 * 60 * 60;
                        tpBusinessSave = businessService.findSavaByCashId(tpCash.getId());
                        if (null != tpBusinessSave){
                            miao_date = tpBusinessSave.getCreateTime() + price_date;
                            if (miao_date >= Calendar.getInstance().getTimeInMillis()){
                                //未过期
                                cashObj.put("guoqi", 1);
                            }else {
                                //已过期
                                cashObj.put("guoqi", 2);
                            }
                        }
                        baozhengjin += tpCash.getBaozhengjin();
                        cashAll.put("\"" + i + "\"", cashObj);
                        cash.add(cashAll);
                        i++;
                    }

                }else{
                    LimitPageList limitPageList = businessService.getLimitPageUseCashList(1, "", page, tpBusinessShare.getId(), tpUsers.getUser_id());
                    object.put("count", limitPageList.getPage().getTotalPageCount());
                    object.put("page", page);
                    object.put("totalPages", limitPageList.getPage().getTotalPageCount()/10);
                    JSONObject userCashObj = null;
                    JSONObject cashObj = null;
                    JSONObject useAll = null;
                    int i = 0;
                    for (TpBusinessUseCash tpbusinessUseCash : (List<TpBusinessUseCash>)limitPageList.getList()) {
                        userCashObj = new JSONObject();
                        useAll = new JSONObject();
                        userCashObj = businessService.getBusinessUserCashJson(tpbusinessUseCash);
                        TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(tpbusinessUseCash.getCashId());
                        if(null != tpBusinessCash){
                            cashObj = businessService.getBUsinessCashJson(tpBusinessCash);
                        }
                        userCashObj.put("cash", cashObj);
                    }
                    useAll.put("\"" + i + "\"", userCashObj);
                    use.add(useAll);
                    i++;
                }
            }
            object.put("baozhengjin", baozhengjin);
            object.put("use", use);
            object.put("cash", cash);
            object.put("search", search);
            jsonObj.put("result", object);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }

        return jsonObj.toString();
    }

    /**
     * @param cash_id
     * @param thaw_mess
     * @return
     * 申请解冻
     */
    @RequestMapping(value="/cashThaw",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "申请解冻", notes = "申请解冻")
    public @ResponseBody String cashThaw(HttpServletRequest request,
                                               @RequestParam(required=true) int cash_id,
                                               @RequestParam(required=true) String thaw_mess) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(cash_id);
        if (null != tpBusinessCash){
            TpBusinessSave tpBusinessSave = businessService.findSavaByCashId(cash_id);
            if (null != tpBusinessSave){
                int nowDate = (int)Calendar.getInstance().getTimeInMillis();
                if (nowDate <= tpBusinessSave.getCreateTime()){
                    int remainDate = tpBusinessSave.getCreateTime() - nowDate;
                    jsonObj.put("status", -1);
                    jsonObj.put("msg", "抱歉,暂时无法解冻还剩" + remainDate/1000 * 24 * 60 * 60 + "天可解冻");
                    return jsonObj.toString();
                }else{
                    TpBusinessCash tpBusinessCash1 = new TpBusinessCash();
                    tpBusinessCash1.setId(tpBusinessCash.getId());
                    tpBusinessCash1.setThawFlag(2);
                    tpBusinessCash1.setThawMess(thaw_mess);
                    tpBusinessCash1.setJieTime((int)Calendar.getInstance().getTimeInMillis());
                    int resultCash = businessService.updataCashByThaw(tpBusinessCash1);
                    if (resultCash > 0){
                        int userCash = businessService.updataUserCashFlagByCashId(2, tpBusinessCash.getId());
                        if (userCash > 0){
                            jsonObj.put("status", 1);
                            jsonObj.put("msg", "解冻成功！申请解冻后我们会在1-7个工作日之内退还所冻结金额!");
                            return jsonObj.toString();
                        }
                    }
                }
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/getUseCash",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "用户优惠券详情页", notes = "用户优惠券详情页")
    public @ResponseBody String getUseCash(HttpServletRequest request,
                                               @RequestParam(required=true) int id) {
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
        TpBusinessUseCash tpBusinessUseCash = businessService.findUseCashById(id);
        if (null != tpBusinessUseCash){
            data = businessService.getBusinessUserCashJson(tpBusinessUseCash);
            jsonObj.put("result", data);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功!");
        }

        return jsonObj.toString();
    }

    /**
     * @param number
     * @return
     * 商家优惠券详情页
     */
    @RequestMapping(value="/getStoreUseCash",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "商家优惠券详情页", notes = "商家优惠券详情页")
    public @ResponseBody String getStoreUseCash(HttpServletRequest request,
                                               @RequestParam(required=true) String number) {
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
        TpBusinessUseCash tpBusinessUseCash = businessService.findBusinessUseCashByNumber(number);
        if (null != tpBusinessUseCash){
            TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(tpBusinessUseCash.getCashId());
            if (null != tpBusinessCash){
                TpBusinessShare tpBusinessShare = businessService.findBusinessShareById(tpBusinessCash.getBusinessId());
                if (null != tpBusinessShare){
                    data.put("share", businessService.getBusinessShareJson(tpBusinessShare,0,0));
                }
                TpBusinessSave tpBusinessSave = businessService.findSavaByCashId(tpBusinessCash.getId());
                if (null != tpBusinessSave){
                    data.put("add_time", tpBusinessSave.getAddTime());
                }
                data.put("flag", tpBusinessUseCash.getFlag());
                data.put("use_status", tpBusinessUseCash.getUseStatus());
                data.put("number", tpBusinessUseCash.getNumber());
                data.put("codes", tpBusinessUseCash.getCodes());
                jsonObj.put("result", data);
                jsonObj.put("status", 1);
                jsonObj.put("msg", "请求成功!");
            }

        }
        return jsonObj.toString();
    }

    /**
     * @param status
     * @return
     *
     */
    @RequestMapping(value="/useUserCashList",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = " 用户优惠券列表", notes = " 用户优惠券列表")
    public @ResponseBody String useUserCashList(HttpServletRequest request,
                                                @RequestParam(required=true) String type,
                                               @RequestParam(required=false) int status) {
        //status 1已使用 0未使用   	type 1优惠券2折扣券
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
        List<TpBusinessUseCash> tpBusinessUseCashList = businessService.findBusinessUseCashByStatus(tpUsers.getUser_id(), type, status);
        for (TpBusinessUseCash tpbusinessUseCash : tpBusinessUseCashList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tpbusinessUseCash.getId());
            jsonObject.put("user_id", tpbusinessUseCash.getUserId());
            jsonObject.put("cash_id", tpbusinessUseCash.getCashId());
            jsonObject.put("number", tpbusinessUseCash.getNumber());
            jsonObject.put("business_id", tpbusinessUseCash.getBusinessId());
            jsonObject.put("flag", tpbusinessUseCash.getFlag());
            jsonObject.put("add_time", tpbusinessUseCash.getAddTime());
            jsonObject.put("codes", tpbusinessUseCash.getCodes());
            jsonObject.put("user_use_id", tpbusinessUseCash.getUserUseId());
            jsonObject.put("use_status", tpbusinessUseCash.getUseStatus());
            TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(tpbusinessUseCash.getCashId());
            if (null != tpBusinessCash){
                JSONObject data1 = businessService.getBUsinessCashJson(tpBusinessCash);
                jsonObject.put("cash", data1);
            }
            data.add(jsonObject);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 商城上传判断
     */
    @RequestMapping(value="/upStoreFlag",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = " 商城上传判断", notes = " 商城上传判断")
    public @ResponseBody String upStoreFlag(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 1);
        jsonObj.put("msg", "未上传过!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "您已经上传过了!");
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/getStoreDetailed",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = " 商家资金冻结详细", notes = " 商家资金冻结详细")
    public @ResponseBody String getStoreDetailed(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status",0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            double yibaozhengjin = 0.00;
            //已解冻金额
            List<TpBusinessCash> tpBusinessCashList = businessService.findAllCashByShareAndThawflag(tpBusinessShare.getId(), 1);
            for (TpBusinessCash tpCash : tpBusinessCashList) {
                TpBusinessSave tpBusinessSave = businessService.findSaveByBUsinessIdAndCashId(tpBusinessShare.getId(), tpCash.getId());
                if (null != tpBusinessSave && "".equals(tpBusinessSave.getFaceId())){
                    yibaozhengjin += tpBusinessSave.getBaozhengjin();
                }
            }
            data.put("yi", yibaozhengjin);
            double kebaozhengjin = 0.00;
            //已解冻金额
            List<TpBusinessCash> tpBusinessCashList2 = businessService.findAllCashByShareAndThawflag(tpBusinessShare.getId(), 0);
            for (TpBusinessCash tpCash : tpBusinessCashList2) {
                TpBusinessSave tpBusinessSave = businessService.findSaveByBUsinessIdAndCashId(tpBusinessShare.getId(), tpCash.getId());
                if (null != tpBusinessSave && "".equals(tpBusinessSave.getFaceId())){
                    kebaozhengjin += tpBusinessSave.getBaozhengjin();
                }
            }
            data.put("ke", kebaozhengjin);
            double dongbaozhengjin = 0.00;
            JSONArray cashArray = new JSONArray();
            List<TpBusinessCash> tpBusinessCashList1 = businessService.findAllCashByShare(tpBusinessShare.getId());
            List<String> datesTime = new ArrayList<>();
            for (TpBusinessCash tpCash : tpBusinessCashList1) {
                JSONObject cashObj = new JSONObject();
                TpBusinessSave tpBusinessSave = businessService.findSaveByBUsinessIdAndCashId(tpBusinessShare.getId(), tpCash.getId());
                if (null != tpBusinessSave && !"".equals(tpBusinessSave.getFaceId())){
                    dongbaozhengjin += tpBusinessSave.getBaozhengjin();
                }
                if (tpCash.getThawFlag() == 1){
                    //已解冻
                    if (!datesTime.contains(Util.stampToDate2(tpBusinessSave.getCreateTime() + ""))){
                        datesTime.add(Util.stampToDate2(tpBusinessSave.getCreateTime() + ""));
                    }

                }else{
                    if (!datesTime.contains(Util.stampToDate2(tpBusinessSave.getCreateTime() + ""))){
                        datesTime.add(Util.stampToDate2(tpBusinessSave.getCreateTime() + ""));
                    }
                }
            }
            data.put("dong", dongbaozhengjin);
            JSONObject timeObj = new JSONObject();
            for (int i=0; i < datesTime.size(); i++){
                for (TpBusinessCash tpCash : tpBusinessCashList1) {
                    int num = 0;
                    JSONObject cashObj = new JSONObject();
                    TpBusinessSave tpBusinessSave = businessService.findSaveByBUsinessIdAndCashId(tpBusinessShare.getId(), tpCash.getId());
                    if (tpCash.getThawFlag() == 1){
                        //已解冻
                        if (datesTime.get(i).equals(Util.stampToDate2(tpBusinessSave.getCreateTime() + ""))){
                            num = 1;
                        }
                        cashObj.put("time", Util.stampToDate2(tpBusinessSave.getCreateTime() + ""));
                        cashObj.put("wtime", Util.stampToDate3(tpBusinessSave.getCreateTime() + ""));
                    if (null != tpBusinessSave && !"".equals(tpBusinessSave.getFaceId())){
                        cashObj.put("money", "-" + tpBusinessSave.getBaozhengjin());
                    }
                    cashObj.put("msg", "申请解冻");
                    }else{
                        if (datesTime.get(i).equals(Util.stampToDate2(tpBusinessSave.getCreateTime() + ""))){
                            num = 1;
                        }
                        cashObj.put("time", Util.stampToDate2(tpBusinessSave.getCreateTime() + ""));
                        cashObj.put("wtime", Util.stampToDate3(tpBusinessSave.getCreateTime() + ""));
                    }
                    if (null != tpBusinessSave && !"".equals(tpBusinessSave.getFaceId())){
                        cashObj.put("money", "+" + tpBusinessSave.getBaozhengjin());
                    }
                    cashObj.put("msg", "充值");
                    if (num == 1){
                        cashArray.add(cashObj);
                    }
                }
                timeObj.put("\"" + datesTime.get(i) + "\"", cashArray);
            }
            data.put("list", timeObj);
            jsonObj.put("status",1);
            jsonObj.put("msg", "请求成功");
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/discount_add",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "制作折扣图", notes = "制作折扣图")
    public @ResponseBody String discountAdd(HttpServletRequest request){
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
        String business_address = jsonO.getString("business_address");
        String price = jsonO.getString("price");
        String discount_price = jsonO.getString("discount_price");
        String valid_date = jsonO.getString("valid_date");
        String launch_address = jsonO.getString("launch_address");
        String launch_id_str = jsonO.getString("launch_id_str");
        String launch_date_start = jsonO.getString("launch_date_start");
        String launch_date_end = jsonO.getString("launch_date_end");
        String nums = jsonO.getString("nums");
        String image = jsonO.getString("image");
        if (null == business_address || "".equals(business_address) || null == price || "".equals(price)
                || null == discount_price || "".equals(discount_price) || null == valid_date || "".equals(valid_date)
                || null == launch_address || "".equals(launch_address) || null == launch_id_str || "".equals(launch_id_str)
                || null == launch_date_start || "".equals(launch_date_start) || null == launch_date_end || "".equals(launch_date_end)
                || null == nums || "".equals(nums) || null == image || "".equals(image)){
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
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null == tpBusinessShare){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "您还不是商家哦");
            return jsonObj.toString();
        }
        TpBusinessDiscount tpBusinessDiscount = new TpBusinessDiscount();
        tpBusinessDiscount.setBusinessId(tpBusinessShare.getId());
        tpBusinessDiscount.setBusinessName(tpBusinessShare.getBusinessName());
        tpBusinessDiscount.setBusinessAddress(tpBusinessShare.getAddress());
        tpBusinessDiscount.setPrice(Double.parseDouble(price));
        tpBusinessDiscount.setDiscountPrice(Double.parseDouble(discount_price));
        tpBusinessDiscount.setValidDate(Integer.parseInt(valid_date));
        tpBusinessDiscount.setLaunchAddress(launch_address);
        tpBusinessDiscount.setLaunchId(launch_id_str);
        tpBusinessDiscount.setLaunchDateStart(Integer.parseInt(launch_date_start));
        tpBusinessDiscount.setLaunchDateEnd(Integer.parseInt(launch_date_end));
        tpBusinessDiscount.setNums(Integer.parseInt(nums));
        tpBusinessDiscount.setAddTime(new Timestamp(new Date().getTime()));
        tpBusinessDiscount.setImage(image);
        tpBusinessDiscount.setStatus(1);
        int discountResult = businessService.insertBusinessDiscount(tpBusinessDiscount);
        if (discountResult > 0){
            int num = Integer.parseInt(nums);
            for (int i=0; i<num; i++){
                String time = Calendar.getInstance().getTimeInMillis() + "";
                TpBusinessUseCash tpBusinessUseCash = new TpBusinessUseCash();
                String number = "ZK" + tpBusinessShare.getId() + time.substring(0,time.length()-3) + Util.numberString(3);
                String code = Util.generateString(10);
                tpBusinessUseCash.setUserId(tpUsers.getUser_id());
                tpBusinessUseCash.setCashId(tpBusinessDiscount.getId());
                tpBusinessUseCash.setNumber(number);
                tpBusinessUseCash.setBusinessId(tpBusinessShare.getId());
                tpBusinessUseCash.setFlag(0);
                tpBusinessUseCash.setAddTime((int)Calendar.getInstance().getTimeInMillis());
                tpBusinessUseCash.setCodes(code);
                tpBusinessUseCash.setFlag(0);
                tpBusinessUseCash.setType(2);
                int userCashResult = businessService.insertBUsinessUseCash(tpBusinessUseCash);
                if (userCashResult <= 0){
                    jsonObj.put("result", data);
                    jsonObj.put("status", -1);
                    jsonObj.put("msg", "优惠券生成失败");
                    return jsonObj.toString();
                }
            }
            jsonObj.put("result", data);
            jsonObj.put("status", 1);
            jsonObj.put("msg", "请求成功");
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/discount_list",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "折扣图列表", notes = "折扣图列表")
    public @ResponseBody String discountList(HttpServletRequest request,
                                                @RequestParam(required=true) String page) {
        JSONObject jsonObj = new JSONObject();
        JSONObject dataObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            LimitPageList limitPageList = businessService.getLimitPageDiscountListByBusinessId(tpBusinessShare.getId(), Integer.parseInt(page));
            JSONObject discountJson = null;
            for (TpBusinessDiscount tpDisCount : (List<TpBusinessDiscount>)limitPageList.getList()) {
                discountJson = businessService.getBusinessDiscountJson(tpDisCount);
                data.add(discountJson);
            }
            dataObj.put("total", limitPageList.getPage().getTotalCount());
            dataObj.put("per_page", limitPageList.getPage().getPageSize());
            dataObj.put("current_page", limitPageList.getPage().getPageNow());
            dataObj.put("last_page", limitPageList.getPage().getTotalPageCount());
            dataObj.put("data", data);
        }

        jsonObj.put("result", dataObj);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }
    @RequestMapping(value="/useCashShop",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "使用优惠券", notes = "使用优惠券")
    public @ResponseBody String useCashShop(HttpServletRequest request,
                                            @RequestParam(required=true) String number,
                                            @RequestParam(required=true) String code) {
        JSONObject jsonObj = new JSONObject();
        JSONObject dataObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效");
            return jsonObj.toString();
        }
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            TpBusinessUseCash tpBusinessUseCash = businessService.findUseByUserAndNumberAndUseStatus(number, tpUsers.getUser_id(), 1);
            if (null != tpBusinessUseCash){
//                TpBusinessSave tpBusinessSave = businessService.findSavaByCashId(tpBusinessUseCash.getCashId());
                TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(tpBusinessUseCash.getCashId());
                if (null != tpBusinessCash){
                    int miao = 0;
                    if (null != tpBusinessCash && null != tpBusinessCash.getPriceDate()){
                        miao = tpBusinessUseCash.getAddTime() + tpBusinessCash.getPriceDate()*24*60*60;
                    }
                    int nowTime = (int)Calendar.getInstance().getTimeInMillis();
                    if (miao < nowTime){
                        int useStatus = businessService.updataUseCashForUseStatus(number, tpBusinessCash.getId(), code, 2);
                        if (useStatus > 0){
                            jsonObj.put("status", -1001);
                            jsonObj.put("msg", "优惠券已过期");
                        }
                        return jsonObj.toString();
                    }else{
                        if (code.equals(tpBusinessUseCash.getCodes())){
                            int updataFalg = businessService.updataUseCashForFlag(number, tpUsers.getUser_id(), 1);
                            if (updataFalg > 0){
                                jsonObj.put("status", 1);
                                jsonObj.put("msg", "使用成功");
                                return jsonObj.toString();
                            }
                        }else{
                            jsonObj.put("status", -1000);
                            jsonObj.put("msg", "激活码错误");
                            return jsonObj.toString();
                        }
                    }
                }
            }
        }

        jsonObj.put("status", -1001);
        jsonObj.put("msg", "优惠券已过期");
        return jsonObj.toString();
    }
    @RequestMapping(value="/edit_desc",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "修改店铺描述", notes = "修改店铺描述")
    public @ResponseBody String editDesc(HttpServletRequest request,
                                            @RequestParam(required=true) String number,
                                            @RequestParam(required=true) String code) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        JSONObject jsonO = getRequestJson(request);
        if (null == jsonO){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数有误");
            return jsonObj.toString();
        }
        String shop_id = jsonO.getString("shop_id");
        String desc = jsonO.getString("desc");
        if (null == shop_id || "".equals(shop_id) || null == desc || "".equals(desc)){
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
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByIdAndUserId(Integer.parseInt(shop_id), tpUsers.getUser_id());
        if (null != tpBusinessShare){
            TpBusinessShare tpBusinessShare1 = new TpBusinessShare();
            tpBusinessShare1.setId(tpBusinessShare.getId());
            tpBusinessShare1.setUserId(tpBusinessShare.getUserId());
            tpBusinessShare1.setDesc(desc);
            int updataShareDesc = businessService.updataShareByDesc(tpBusinessShare1);
            if (updataShareDesc > 0){
                jsonObj.put("status", 1);
                jsonObj.put("msg", "修改成功");
                return jsonObj.toString();
            }
        }else {
            jsonObj.put("status", -1);
            jsonObj.put("msg", "店铺不存在");
            return jsonObj.toString();
        }

        return jsonObj.toString();
    }

    /**
     * @param request
     * @param type 	1.人气排行 2.附近店铺 3.领券排行
     * @param location_x
     * @param location_y
     * @return
     */
    @RequestMapping(value="/hot_shop_list",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "热门商家", notes = "热门商家")
    public @ResponseBody String hotShopList(HttpServletRequest request,
                                            @RequestParam(required=true) String type,
                                            @RequestParam(required=true) Integer page,
                                            @RequestParam(required=false) String location_x,
                                            @RequestParam(required=false) String location_y) {
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
        String geohash = null;
        if (null != location_x && !"".equals(location_x) && null != location_y && !"".equals(location_y)){
            String geohashStr = GeoHashUtil.encode(Double.parseDouble(location_x), Double.parseDouble(location_y));
            geohash = geohashStr.substring(5);
        }
        LimitPageList limitPageList = businessService.getLimitPageShareByType(type, geohash, page);
        JSONArray jsonArray = new JSONArray();
        for (TpBusinessShare tpBusinessShare : (List<TpBusinessShare>)limitPageList.getList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tpBusinessShare.getId());
            jsonObject.put("name", tpBusinessShare.getName());
            jsonObject.put("location_area", tpBusinessShare.getLocationArea());
            jsonObject.put("address", tpBusinessShare.getAddress());
            jsonObject.put("logo", tpBusinessShare.getLogo());
            jsonObject.put("pv", tpBusinessShare.getPv());
            jsonObject.put("grade", tpBusinessShare.getSum_star()/tpBusinessShare.getStar_number());
            int quan_count = businessService.getCashCountByBusinessShareId(tpBusinessShare.getId());
            jsonObject.put("quan_count", quan_count);
            int shop_count = businessService.getUserGoodsCountByUserId(tpUsers.getUser_id());
            jsonObject.put("shop_count", shop_count);
            List<TpBusinessCash> tpBusinessCashList = businessService.findAllCashByShare(tpBusinessShare.getId());
            JSONArray first_quan = new JSONArray();
            for (TpBusinessCash tpBusinessCash : tpBusinessCashList) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", tpBusinessCash.getId());
                jsonObject1.put("price", tpBusinessCash.getPrice());
                jsonObject1.put("fullsub_price", tpBusinessCash.getFullsubPrice());
                first_quan.add(jsonObject1);
            }
            jsonObject.put("first_quan", first_quan);
            jsonObject.put("distance", Util.getDistance(Double.valueOf(location_x), Double.valueOf(location_y), Double.parseDouble(tpBusinessShare.getXpoint()),
                    Double.parseDouble(tpBusinessShare.getYpoint())));
            jsonArray.add(jsonObject);
        }
        data.put("data", jsonArray);
        data.put("total", limitPageList.getPage().getTotalCount());
        data.put("per_page", limitPageList.getPage().getPageNow());
        data.put("current_page", limitPageList.getPage().getPageSize());
        data.put("last_page", limitPageList.getPage().getTotalCount());
        jsonObj.put("result", data);
        return jsonObj.toString();
    }

    /**
     * @param request
     * @param cash_id
     * @param type 1 优惠券 2 折扣券
     * @return
     */
    @RequestMapping(value="/give_cash",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "领取优惠券", notes = "领取优惠券")
    public @ResponseBody String giveCash(HttpServletRequest request,
                                            @RequestParam(required=true) String cash_id,
                                            @RequestParam(required=true) String type) {
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
        TpBusinessCash tpBusinessCash = null;
        TpBusinessDiscount tpBusinessDiscount = null;
        if (type.equals("1")){
            tpBusinessCash = businessService.findBusinessCashById(Integer.parseInt(cash_id));
        }else if (type.equals("2")){
            tpBusinessDiscount = businessService.findBusinessDiscountById(Integer.parseInt(cash_id));
        }
        TpBusinessUseCash tpBusinessUseCash = businessService.findUseCashByCashIdAndUseIdAndFalg(Integer.parseInt(cash_id), tpUsers.getUser_id(), null);
        if (null != tpBusinessUseCash){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "您已领取过该优惠券了~");
            return jsonObj.toString();
        }
        tpBusinessUseCash = businessService.findUseCashByCashIdAndUseIdAndFalg(Integer.parseInt(cash_id), 0, 0);
        if (null == tpBusinessUseCash){
            jsonObj.put("status", -1);
            jsonObj.put("msg", "下手慢啦~");
            return jsonObj.toString();
        }
        int updataUseCash = businessService.updataUseCashByUser(tpBusinessUseCash.getId(), tpUsers.getUser_id(), 0);
        if (updataUseCash > 0){
            int updataShare = businessService.updataShareByGivequan(tpBusinessUseCash.getBusinessId());
            if (updataShare > 0){
                jsonObj.put("status", -1);
                jsonObj.put("msg", "领取成功");
                return jsonObj.toString();
            }
        }
        return jsonObj.toString();
    }
}

