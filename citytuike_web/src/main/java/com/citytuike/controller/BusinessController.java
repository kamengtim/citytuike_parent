package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.BusinessService;
import com.citytuike.service.TpUsersService;
import com.citytuike.util.GeoHashUtil;
import com.citytuike.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("api/Business")
public class BusinessController extends BaseController{
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private BusinessService businessService;

    /**
     * @param business_type
     * @param business_name
     * @param cus_nums
     * @param business_start_time
     * @param business_end_time
     * @param name
     * @param mobile
     * @param location_area
     * @param address
     * @param xpoint
     * @param ypoint
     * @param image
     * @return
     * 添加商家
     */
    @RequestMapping(value="/businessUp",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String businessUp(HttpServletRequest request,
                                           @RequestParam(required=true) int business_type,
                                           @RequestParam(required=true) String business_name,
                                           @RequestParam(required=true) float cus_nums,
                                           @RequestParam(required=true) String business_start_time,
                                           @RequestParam(required=true) String business_end_time,
                                           @RequestParam(required=true) String name,
                                           @RequestParam(required=true) String mobile,
                                           @RequestParam(required=true) String location_area,
                                           @RequestParam(required=true) String address,
                                           @RequestParam(required=true) String xpoint,
                                           @RequestParam(required=true) String ypoint,
                                           @RequestParam(required=true) String[] image) {
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
            for (int i = 0; i< image.length; i++){
                TpBusinessImages tpBusinessImages = new TpBusinessImages();
                tpBusinessImages.setBusinessId(tpBusinessShare.getId());
                tpBusinessImages.setImage(image[i]);
                tpBusinessImages.setImgSort(i);
                int resultImg = businessService.insertBusinessImages(tpBusinessImages);
                if (resultImg <= 0){
                    jsonObj.put("status", "0");
                    jsonObj.put("msg", "请求失败，请稍后再试");
                    return jsonObj.toString();
                }
            }
            data.put("business_id", tpBusinessShare.getId());
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "请求成功!");
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 获取商家类型
     */
    @RequestMapping(value="/getBusinessType",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getBusinessType(HttpServletRequest request) {
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
        List<TpBusinessType> tpBusinessTypeList = businessService.findAllBusinessType();
        for (TpBusinessType tpbusinessTpye : tpBusinessTypeList) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", tpbusinessTpye.getId());
            jsonObject1.put("business_name", tpbusinessTpye.getBusinessName());
            jsonObject1.put("create_time", tpbusinessTpye.getCreateTime());
            data.add(jsonObject1);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param business_id  	商家id
     * @param stars 1星2星....
     * @param message 内容
     * @param tag  	逗号分割传id
     * @return
     * 评论
     */
    @RequestMapping(value="/businessEva",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String businessEva(HttpServletRequest request,
                                            @RequestParam(required=true) int business_id,
                                            @RequestParam(required=true) int stars,
                                            @RequestParam(required=true) String message,
                                            @RequestParam(required=true) String tag) {
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
        TpBusinessComment tpBusinessComment = new TpBusinessComment();
        tpBusinessComment.setBusinessId(business_id);
        tpBusinessComment.setStars(stars);
        tpBusinessComment.setUserId(tpUsers.getUser_id());
        tpBusinessComment.setAddTime((int)Calendar.getInstance().getTimeInMillis());
        tpBusinessComment.setTag(tag);
        tpBusinessComment.setMessage(message);
        int result = businessService.insertBusinessComment(tpBusinessComment);
        if (result > 0){
            data.put("business_comment_id", tpBusinessComment.getId());
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
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
    public @ResponseBody String nearByBusiness(HttpServletRequest request,
                                            @RequestParam(required=true) int xpoint,
                                            @RequestParam(required=true) int ypoint,
                                            @RequestParam(required=true) int business_type,
                                            @RequestParam(required=true) int num,
                                            @RequestParam(required=false) int tuijian) {
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
        String geohashStr = GeoHashUtil.encode(Double.parseDouble(xpoint+""), Double.parseDouble(ypoint+""));
        String geohash = geohashStr.substring(num);
        List<TpBusinessShare> tpBusinessShareList = businessService.findNearByBusiness(business_type, geohash, tuijian);
        for (TpBusinessShare tpbusinessShare : tpBusinessShareList) {
            JSONObject jsonObject1 = businessService.getBusinessShareJson(tpbusinessShare, xpoint, ypoint);
            data.add(jsonObject1);
        }
        jsonObj.put("result", data);
        jsonObj.put("status", "1");
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
    public @ResponseBody String businessDetails(HttpServletRequest request,
                                            @RequestParam(required=true) int xpoint,
                                            @RequestParam(required=true) int ypoint,
                                            @RequestParam(required=true) int id) {
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param id 评论ID
     * @return
     * 评论标签列表接口
     */
    @RequestMapping(value="/commentTag",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String commentTag(HttpServletRequest request,
                                            @RequestParam(required=true) int id) {
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
        TpBusinessComment tpBusinessComment = businessService.findOneCommentById(id);
        if (null != tpBusinessComment){
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @param id
     * @return
     * 支付返回订单
     */
    @RequestMapping(value="/paymentBusiness",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String paymentBusiness(HttpServletRequest request,
                                            @RequestParam(required=true) int id) {
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
                    jsonObj.put("status", "1");
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
    @RequestMapping(value="/setBusinessCash",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
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
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
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
                jsonObj.put("result", data);
                jsonObj.put("status", "1");
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
    public @ResponseBody String grantCash(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "未缴纳");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        List<TpBusinessOrder> tpBusinessOrderList = businessService.findAllBusinessOrderByPay(tpUsers.getUser_id(),1);
        if (tpBusinessOrderList.size() > 0){
            jsonObj.put("status", "1");
            jsonObj.put("msg", "已缴纳");
        }
        return jsonObj.toString();
    }

    /**
     * @return
     * 商家名
     */
    @RequestMapping(value="/getBusinessName",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getBusinessName(HttpServletRequest request) {
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
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            data.put("name", tpBusinessShare.getName());
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
            jsonObj.put("msg", "请求成功!");
        }

        return jsonObj.toString();
    }

    /**
     * @return
     * 面值现金券列表
     */
    @RequestMapping(value="/cashFaceLish",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String cashFaceLish(HttpServletRequest request) {
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 现金券金额说明
     */
    @RequestMapping(value="/businessTips",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String businessTips(HttpServletRequest request) {
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
        List<TpBusinessOrder> tpBusinessOrderList = businessService.findAllBusinessOrderByPay(tpUsers.getUser_id(),1);

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
                jsonObj.put("status", "1");
                jsonObj.put("msg", "请求成功!");
            }
        }
        return jsonObj.toString();
    }

    /**
     * @param request
     * @param flag
     * @param p
     * @param number
     * @return
     * 我的商铺
     */
    @RequestMapping(value="/myStore",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String myStore(HttpServletRequest request,
                                               @RequestParam(required=true) int flag,
                                               @RequestParam(required=true) int p,
                                               @RequestParam(required=true) String number) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");

        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }

        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            JSONObject object = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id", tpBusinessShare.getId());
            data.put("name", tpBusinessShare.getName());
            data.put("business_name", tpBusinessShare.getBusinessName());
            data.put("location_area", tpBusinessShare.getLocationArea());
            data.put("address", tpBusinessShare.getAddress());
            JSONArray jsonArray1 = new JSONArray();
            JSONArray jsonArray2 = new JSONArray();
            if (!"".equals(number)){
                //搜索


            }else{
                //全部或者已使用

            }
            LimitPageList limitPageList = businessService.getLimitPageUseCashList(flag, number, p, tpBusinessShare.getId(), tpUsers.getUser_id());
            data.put("count", limitPageList.getPage().getTotalPageCount());
            data.put("page", p);
            data.put("totalPages", limitPageList.getPage().getTotalPageCount()/10);
            for (TpBusinessUseCash tpbusinessUseCash : (List<TpBusinessUseCash>)limitPageList.getList()) {


            }
            data.put("search", jsonArray1);
            data.put("cash", jsonArray2);
            object.put("cash", data);
            jsonObj.put("result", object);
            jsonObj.put("status", "1");
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
    @RequestMapping(value="/cashThaw",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String cashThaw(HttpServletRequest request,
                                               @RequestParam(required=true) int cash_id,
                                               @RequestParam(required=true) String thaw_mess) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "0");
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(cash_id);
        if (null != tpBusinessCash){

            TpBusinessCash tpBusinessCash1 = new TpBusinessCash();
            tpBusinessCash1.setId(tpBusinessCash.getId());
            tpBusinessCash1.setThawFlag(2);
            tpBusinessCash1.setThawMess(thaw_mess);
            int resultCash = businessService.updataCashByThaw(tpBusinessCash1);
            if (resultCash > 0){
                jsonObj.put("status", "1");
                jsonObj.put("msg", "解冻成功！申请解冻后我们会在1-7个工作日之内退还所冻结金额!");
            }
        }
        return jsonObj.toString();
    }
    @RequestMapping(value="/getUseCash",method= RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getUseCash(HttpServletRequest request,
                                               @RequestParam(required=true) int id) {
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
        TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(id);
        if (null != tpBusinessCash){
            data = businessService.getBUsinessCashJson(tpBusinessCash);
            jsonObj.put("result", data);
            jsonObj.put("status", "1");
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
    public @ResponseBody String getStoreUseCash(HttpServletRequest request,
                                               @RequestParam(required=true) String number) {
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
        TpBusinessUseCash tpBusinessUseCash = businessService.findBusinessUseCashByNumber(number);
        if (null != tpBusinessUseCash){
            TpBusinessCash tpBusinessCash = businessService.findBusinessCashById(tpBusinessUseCash.getCashId());
            if (null != tpBusinessCash){
                data = businessService.getBUsinessCashJson(tpBusinessCash);
                data.put("codes", tpBusinessUseCash.getCodes());
                jsonObj.put("result", data);
                jsonObj.put("status", "1");
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
    public @ResponseBody String useUserCashList(HttpServletRequest request,
                                               @RequestParam(required=true) String status) {
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
        List<TpBusinessUseCash> tpBusinessUseCashList = businessService.findBusinessUseCashByStatus(tpUsers.getUser_id(), status);
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "请求成功!");
        return jsonObj.toString();
    }

    /**
     * @return
     * 商城上传判断
     */
    @RequestMapping(value="/upStoreFlag",method= RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String upStoreFlag(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObj.put("status", "1");
        jsonObj.put("msg", "未上传过!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpBusinessShare tpBusinessShare = businessService.findBusinessShareByUserId(tpUsers.getUser_id());
        if (null != tpBusinessShare){
            jsonObj.put("status", "-1");
            jsonObj.put("msg", "您已经上传过了!");
        }
        return jsonObj.toString();
    }
}

