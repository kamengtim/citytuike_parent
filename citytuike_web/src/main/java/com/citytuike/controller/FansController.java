package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.Util;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("api/Fans")
public class FansController extends BaseController{
    @Autowired
    private TpFansService tpFansService;
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpRegionService tpRegionService;
    @Autowired
    private TpSmsLogService tpSmsLogService;
    @Autowired
    private TpFansSaleService tpFansSaleService;
    @Autowired
    private TpIndustryService tpIndustryService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpOrderService tpOrderService;
    @Autowired
    private TpFansNeedService tpFansNeedService;
    /**
     * @return 粉丝公众号列表
     */
    @RequestMapping(value = "fansList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "粉丝公众号列表", notes = "粉丝公众号列表")
    public @ResponseBody String getConf(HttpServletRequest request,
                                        @RequestParam(required = false) String area_id,
                                        @RequestParam(required = false) String industry){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        PageInfo pageInfo = tpFansService.getLimtPageList(area_id,industry);
        List<TpFans>tpFans = pageInfo.getList();
        for (TpFans tpFan : tpFans) {
           JSONObject jsonObject = tpFansService.getJson(tpFan);
            jsonArray.add(jsonObject);
        }
        data.put("current_page", pageInfo.getPageNum());
        data.put("total", pageInfo.getTotal());
        data.put("per_page", pageInfo.getPageSize());
        data.put("last_page",pageInfo.getLastPage());
        data.put("data",jsonArray);
        jsonObj.put("result",data);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 粉丝公众号详情
     */
    @RequestMapping(value = "fansDetails", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "粉丝公众号详情", notes = "粉丝公众号详情")
    public @ResponseBody String fansDetails(HttpServletRequest request){
        JSONObject  jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        String id = jsonRequest.getString("id");
        String[] s = new String[]{id};
        if (notEmply(s)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpFans tpFans = tpFansService.fansDetails(id);
        if(tpFans != null){
            TpRegion tpRegion = tpRegionService.getNameByFanId(tpFans.getAddress());
            JSONObject  jsonObject = tpFansService.getJson(tpFans,tpRegion);
            jsonObj.put("result",jsonObject);
        }
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 粉丝公众号行情示例
     */
    @RequestMapping(value = "fansTypeList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "粉丝公众号行情示例", notes = "粉丝公众号行情示例")
    public @ResponseBody String fansTypeList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONArray jsonArray = new JSONArray();
        PageInfo pageInfo = tpFansService.fansTypeList();
        List <TpFans> tpFans = pageInfo.getList();
        for (TpFans tpFan : tpFans) {
            JSONObject jsonObject = tpFansService.getJson(tpFan);
            jsonArray.add(jsonObject);
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("current_page", pageInfo.getPageNum());
        jsonObj.put("total", pageInfo.getTotal());
        jsonObj.put("per_page", pageInfo.getPageSize());
        jsonObj.put("last_page",pageInfo.getLastPage());
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return我要出售
     */
    @RequestMapping(value = "fansSale", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "我要出售", notes = "我要出售")
    public @ResponseBody String fansSale(HttpServletRequest request){
        JSONObject  jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonRequest = getRequestJson(request);
        Integer sel_class = jsonRequest.getInteger("sel_class");
        Integer tag = jsonRequest.getInteger("tag");
        Integer auth_bodys = jsonRequest.getInteger("auth_bodys");
        Integer auth_body_change = jsonRequest.getInteger("auth_body_change");
        Integer original = jsonRequest.getInteger("original");
        Integer read_num = jsonRequest.getInteger("read_num");
        Integer mobile_code = jsonRequest.getInteger("mobile_code");
        Integer address = jsonRequest.getInteger("address");
        String fan_num = jsonRequest.getString("fan_num");
        String scale_man = jsonRequest.getString("scale_man");
        String scale_women = jsonRequest.getString("scale_women");
        String monmoney = jsonRequest.getString("monmoney");
        String admoney = jsonRequest.getString("admoney");
        String wechat = jsonRequest.getString("wechat");
        String mobile = jsonRequest.getString("mobile");
        String title = jsonRequest.getString("title");
        String logo = jsonRequest.getString("logo");
        Float price = jsonRequest.getFloat("price");
        Boolean industry = jsonRequest.getBoolean("industry");
        String[] s = new String[]{String.valueOf(sel_class),String.valueOf(auth_bodys),String.valueOf(auth_body_change),String.valueOf(original),
                String.valueOf(read_num),String.valueOf(mobile_code),String.valueOf(address),fan_num,scale_man,scale_women,monmoney,admoney,wechat,mobile,title,logo, String.valueOf(industry), String.valueOf(price)};
        if (notEmply(s)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        TpSmsLog tpSmsLog = tpSmsLogService.selectvalidateCode(String.valueOf(mobile_code), mobile);
        if(tpSmsLog == null || tpSmsLog.getStatus() != 1){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "验证码错误");
            return jsonObj.toString();
        }
        String[] selClassArr = new String[]{"1","2","3"};
        Integer[] authBodysArr = new Integer[]{1,2};
        Integer[] authBodyChangeArr = new Integer[]{1,2};
        int a = Arrays.binarySearch(selClassArr, sel_class);
        int b = Arrays.binarySearch(authBodysArr, auth_bodys);
        int c = Arrays.binarySearch(authBodyChangeArr, auth_body_change);
        if(a<0){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "类型错误");
            return jsonObj.toString();
        }else if(b<0){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "主体信息错误");
            return jsonObj.toString();
        }else if(c<0){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "主体变更设置错误");
            return jsonObj.toString();
        }
        String selClass = "";
        String fan_dev = "";
        if (Integer.parseInt(scale_man) > Integer.parseInt(scale_women)){
            fan_dev =  "男粉丝多";
        }else if(Integer.parseInt(scale_man) < Integer.parseInt(scale_women)){
            fan_dev =  "女粉丝多";
        }else{
            fan_dev =  "一样多";
        }
        if(auth_bodys == 1){
            selClass = "个人认证";
        }else{
            selClass = "公司认证";
        }
        TpFansSale tpFansSale = new TpFansSale();
        tpFansSale.setSel_class(sel_class);
        tpFansSale.setTag(tag == null ? 0 : tag);
        tpFansSale.setIndustry(String.valueOf(industry));
        tpFansSale.setFan_num(fan_num);
        tpFansSale.setScale_man(scale_man);
        tpFansSale.setScale_women(scale_women);
        tpFansSale.setAuth(1);
        tpFansSale.setAuth_body(selClass);
        tpFansSale.setAuth_bodys(auth_bodys);
        tpFansSale.setAuth_body_change(auth_body_change);
        tpFansSale.setOriginal(original);
        tpFansSale.setMon_money(new BigDecimal(monmoney));
        tpFansSale.setAd_money(new BigDecimal(admoney));
        tpFansSale.setRead_num(String.valueOf(read_num));
        tpFansSale.setMobile(mobile);
        tpFansSale.setWechat(wechat);
        tpFansSale.setUser_id(tpUsers.getUser_id());
        tpFansSale.setUsed_existx(4);
        tpFansSale.setType(2);
        tpFansSale.setCatename("其他");
        tpFansSale.setAddress(address);
        tpFansSale.setTitle(title);
        tpFansSale.setLogo(logo);
        tpFansSale.setFan_dev(fan_dev);
        tpFansSale.setPrice(BigDecimal.valueOf(price));
        tpFansSale.setCreated_at(new Date());
        tpFansSale.setUpdated_at(new Date());
        int i = tpFansSaleService.fansSale(tpFansSale);
        if(i < 0 ){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "操作失败");
            return jsonObj.toString();
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok");
        return jsonObj.toString();
    }
    /**
     * @return估值
     */
    @RequestMapping(value = "saveFans", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "MessageParam", name = "param", value = "信息参数", required = true) })
    @ApiOperation(value = "估值", notes = "估值")
    public @ResponseBody String saveFans(HttpServletRequest request){
        JSONObject  jsonObj = new JSONObject();
        JSONObject jsonRequest = getRequestJson(request);
        Integer sel_class = jsonRequest.getInteger("sel_class");
        Integer tag = jsonRequest.getInteger("tag");
        Integer auth_bodys = jsonRequest.getInteger("auth_bodys");
        Integer auth_body_change = jsonRequest.getInteger("auth_body_change");
        Integer original = jsonRequest.getInteger("original");
        Integer read_num = jsonRequest.getInteger("read_num");
        Integer mobile_code = jsonRequest.getInteger("mobile_code");
        Integer address = jsonRequest.getInteger("address");
        String account = jsonRequest.getString("account");
        String fan_num = jsonRequest.getString("fan_num");
        String scale_man = jsonRequest.getString("scale_man");
        String scale_women = jsonRequest.getString("scale_women");
        String monmoney = jsonRequest.getString("monmoney");
        String admoney = jsonRequest.getString("admoney");
        String wechat = jsonRequest.getString("wechat");
        String mobile = jsonRequest.getString("mobile");
        String title = jsonRequest.getString("title");
        String logo = jsonRequest.getString("logo");
        Boolean industry = jsonRequest.getBoolean("industry");
        String[] s = new String[]{String.valueOf(sel_class),String.valueOf(auth_bodys),String.valueOf(auth_body_change),String.valueOf(original),
                String.valueOf(read_num),String.valueOf(mobile_code),String.valueOf(address),fan_num,scale_man,scale_women,monmoney,admoney,wechat,mobile,title,logo, String.valueOf(industry)};
        if (notEmply(s)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        String fan_dev = "";
        TpSmsLog tpSmsLog = tpSmsLogService.selectvalidateCode(String.valueOf(mobile_code), mobile);
        if(tpSmsLog == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "验证码错误");
            return jsonObj.toString();
        }
        if (Integer.parseInt(scale_man) > Integer.parseInt(scale_women)){
            fan_dev =  "男粉丝多";
        }else{
            fan_dev =  "女粉丝多";
        }
        
        TpFans tpFans = new TpFans();
        tpFans.setSel_class(sel_class);
        tpFans.setTag(tag);
        tpFans.setTag(Integer.valueOf(account));
        tpFans.setIndustry(String.valueOf(industry));
        tpFans.setFan_num(fan_num);
        tpFans.setAuth_bodys(auth_bodys);
        tpFans.setScale_man(scale_man);
        tpFans.setAuth_body_change(auth_body_change);
        tpFans.setScale_women(scale_women);
        tpFans.setOriginal(original);
        tpFans.setMonmoney(monmoney);
        tpFans.setAdmoney(admoney);
        tpFans.setRead_num(String.valueOf(read_num));
        tpFans.setWechat(wechat);
        tpFans.setUser_id(tpUsers.getUser_id());
        tpFans.setCreatetime((int)(new Date().getTime()/1000));
        tpFans.setUsed_existx(4);
        tpFans.setType(2);
        tpFans.setCatename("其他");
        tpFans.setAddress(address);
        tpFans.setTitle(title);
        tpFans.setLogo(logo);
        tpFans.setFan_dev(fan_dev);
        tpFans.setShop_price("");
        tpFans.setUsed_existx(4);
        tpFans.setAuth(false);
        tpFans.setAccount("");
        tpFansService.saveFansList(tpFans);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 获取行业信息
     */
    @RequestMapping(value = "getIndustry", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "获取行业信息", notes = "获取行业信息")
    public @ResponseBody String getIndustry(HttpServletRequest request){
        
        JSONObject  jsonObj = new JSONObject();
        JSONObject object = new JSONObject();
        JSONObject arry = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        List<TpIndustry> tpIndustries = tpIndustryService.getIndustry();
        for (TpIndustry tpIndustry : tpIndustries) {
            object = tpIndustryService.getJson(tpIndustry);
            jsonArray1.add(object);
            List<TpIndustry> tpIndustriesSons = tpIndustryService.getIndustrySon(tpIndustry.getFid());
            for (TpIndustry tpIndustriesSon : tpIndustriesSons) {
                arry =  tpIndustryService.getSonJson(tpIndustriesSon);
                jsonArray.add(arry);
                object.put("son",jsonArray);
            }
        }
        jsonObj.put("result",jsonArray1);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 粉丝支付接口
     */
    @RequestMapping(value = "getFansOrderSn", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "粉丝支付接口", notes = "粉丝支付接口")
    public @ResponseBody String getFansOrderSn(HttpServletRequest request,
                                               @RequestParam(required = true) Integer id){
        JSONObject jsonObj = new JSONObject();
        if(id == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = new JSONObject();
        String order_sn = "";
        TpFans tpFans = tpFansService.getFansOrderSn(id);
        if(tpFans != null){
            order_sn =  "fans"+ Util.transferLongToDate("yyyyMMdd HHmm",new Date().getTime()/1000)+""+ new Random().nextInt(1000)+8999;
        }
        TpFansOrder tpFansOrder = new TpFansOrder();

        tpFansOrder.setGoods_id(id);
        tpFansOrder.setOrder_sn(order_sn);
        tpFansOrder.setUser_id(tpFans.getUser_id());
        tpFansOrder.setOrder_status(false);
        tpFansOrder.setShipping_status(false);
        tpFansOrder.setPay_status(false);
        tpFansOrder.setConsignee("");
        tpFansOrder.setCountry(0);
        tpFansOrder.setProvince(0);
        tpFansOrder.setCity(0);
        tpFansOrder.setDiscount(new BigDecimal(0.00));
        tpFansOrder.setAddress("");
        tpFansOrder.setZipcode("");
        tpFansOrder.setMobile("");
        tpFansOrder.setEmail("");
        tpFansOrder.setShipping_code("");
        tpFansOrder.setShipping_name("");
        tpFansOrder.setPay_code("");
        tpFansOrder.setPay_name("");
        tpFansOrder.setInvoice_title("");
        tpFansOrder.setTaxpayer("");
        tpFansOrder.setShipping_price(new BigDecimal(0.00));
        tpFansOrder.setUser_money(new BigDecimal(0.00));
        tpFansOrder.setCoupon_price(new BigDecimal(0.00));
        tpFansOrder.setIntegral(0);
        tpFansOrder.setIntegral_money(new BigDecimal(0.00));
        tpFansOrder.setShipping_time(0);
        tpFansOrder.setConfirm_time(0);
        tpFansOrder.setPay_time(0);
        tpFansOrder.setTransaction_id("");
        tpFansOrder.setProm_id(0);
        tpFansOrder.setProm_type(Byte.valueOf("0"));
        tpFansOrder.setOrder_prom_id((short) 0);
        tpFansOrder.setOrder_amount(new BigDecimal(0.00));
        tpFansOrder.setDiscount(new BigDecimal(0.00));
        tpFansOrder.setUser_note("");
        tpFansOrder.setAdmin_note("");
        tpFansOrder.setParent_sn("");
        tpFansOrder.setIs_distribut(false);
        tpFansOrder.setPaid_money(new BigDecimal(0.00));
        tpFansOrder.setDeleted(false);
        tpFansOrder.setSettle_status(Byte.valueOf("0"));
        tpFansOrder.setAll_income(new BigDecimal(0.00));
        tpFansOrder.setInvoice_email("");
        tpFansOrder.setInvoice_contact("");
        tpFansOrder.setInvoice_fee(new BigDecimal(0.00));
        tpFansOrder.setDistrict(0);
        String str = tpFans.getShop_price();
        tpFansOrder.setGoods_price(new BigDecimal(str));
        tpFansOrder.setOrder_amount(new BigDecimal(str));
        tpFansOrder.setTotal_amount(new BigDecimal(str));
        tpFansOrder.setOrder_status(false);
        tpFansOrder.setAdd_time((int)(new Date().getTime()/1000));
        tpFansOrder.setOrder_prom_amount(new BigDecimal(0.00));
        tpOrderService.save(tpFansOrder);
        jsonObject.put("order_sn",order_sn);
        jsonObj.put("result",jsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 我要增粉
     */
    @RequestMapping(value = "addNeedFans", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "我要增粉", notes = "我要增粉")
    public @ResponseBody String addNeedFans(HttpServletRequest request,
                                            @RequestParam(required = true) String nickname,
                                               @RequestParam(required = true) String uid,
                                               @RequestParam(required = true) String contacts,
                                               @RequestParam(required = true) String mobile,
                                               @RequestParam(required = false) String qq,
                                               @RequestParam(required = false) String wechat,
                                               @RequestParam(required = true) String email,
                                               @RequestParam(required = true) String add_time,
                                               @RequestParam(required = true) String industry,
                                               @RequestParam(required = true) String product,
                                               @RequestParam(required = true) String message,
                                               @RequestParam(required = true) int number,
                                               @RequestParam(required = true) String fans_time,
                                               @RequestParam(required = true) int fans_sex,
                                               @RequestParam(required = true) String area,
                                               @RequestParam(required = true) String day_add_number,
                                               @RequestParam(required = true) String company,
                                               @RequestParam(required = false) int type_sel,
                                               @RequestParam(required = false) int wechat_type,
                                               @RequestParam(required = false) String head_img){
        
        JSONObject  jsonObj = new JSONObject();
        String[] s = new String[]{nickname,contacts,mobile,email,add_time,industry,product,message,fans_time,area,day_add_number,company,String.valueOf(number),String.valueOf(fans_sex)};
        if (notEmply(s)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpFansNeed tpFansNeed = new TpFansNeed();
        tpFansNeed.setUser_id(tpUsers.getUser_id());
        tpFansNeed.setNickname(nickname);
        tpFansNeed.setUid(uid);
        tpFansNeed.setContacts(contacts);
        tpFansNeed.setMobile(mobile);
        tpFansNeed.setQq(qq);
        tpFansNeed.setWechat(wechat);
        tpFansNeed.setEmail(email);
        tpFansNeed.setAdd_time(add_time);
        tpFansNeed.setIndustry(Integer.valueOf(industry));
        tpFansNeed.setProduct(product);
        tpFansNeed.setMessage(message);
        tpFansNeed.setNumber(number);
        tpFansNeed.setFans_time(fans_time);
        tpFansNeed.setFans_sex(Boolean.valueOf(String.valueOf(fans_sex)));
        tpFansNeed.setArea(area);
        tpFansNeed.setIs_flag(false);
        tpFansNeed.setCompany(company);
        tpFansNeed.setDay_add_number(Integer.valueOf(day_add_number));
        tpFansNeed.setType_sel(Boolean.valueOf(String.valueOf(type_sel)));
        tpFansNeed.setWechat_type(Boolean.valueOf(String.valueOf(wechat_type)));
        tpFansNeed.setHead_img(head_img);
        tpFansNeedService.save(tpFansNeed);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 我要增粉列表
     */
    @RequestMapping(value = "NeedFansList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "我要增粉列表", notes = "我要增粉列表")
    public @ResponseBody String NeedFansList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        
        List<TpFansNeed> tpFansNeeds = tpFansNeedService.NeedFansList(tpUsers.getUser_id());
        if(tpFansNeeds != null){
            for (TpFansNeed tpFansNeed : tpFansNeeds) {
                if(tpFansNeed.getIs_flag() == true){
                   int fans_count = tpFansNeedService.getFanNum(tpFansNeed.getId(),1,tpUsers.getUser_id());
                   int fans_single = tpFansNeedService.getFanNum(tpFansNeed.getId(),2,tpUsers.getUser_id());
                   jsonObject = tpFansNeedService.getJson(tpFansNeed);
                   jsonObject.put("fans_count",fans_count);
                   jsonObject.put("fans_single",fans_single);
                }else{
                    jsonObject = tpFansNeedService.getJson(tpFansNeed);
                    jsonObject.put("fans_count",0);
                    jsonObject.put("fans_single",0);
                }
            jsonArray.add(jsonObject);
            }
        }else {
            jsonObj.put("status", 0);
            jsonObj.put("msg", "没有订单");
            return jsonObj.toString();
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * @return 我要增粉详情
     */
    @RequestMapping(value = "NeedFansDetails", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "我要增粉详情", notes = "我要增粉详情")
    public @ResponseBody String NeedFansDetails(HttpServletRequest request,
                                                @RequestParam(required = true) int order_id){
        JSONArray jsonArray = new JSONArray();
        JSONObject  jsonObj = new JSONObject();
        String[] s = new String[]{String.valueOf(order_id)};
        if (notEmply(s)){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "参数错误");
            return jsonObj.toString();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "请求失败，请稍后再试");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpFansNeed tpFansNeed = tpFansNeedService.getNeedByOrderId(tpUsers.getUser_id(),order_id);
        if(tpFansNeed != null){
            if(tpFansNeed.getFlag() == true){
                int fans_count = tpFansNeedService.getFanNum(order_id,1,tpUsers.getUser_id());
                int fans_single = tpFansNeedService.getFanNum(order_id,2,tpUsers.getUser_id());
                jsonObject = tpFansNeedService.getJson(tpFansNeed);
                jsonObject.put("fans_count",fans_count);
                jsonObject.put("fans_single",fans_single);
            }
            jsonArray.add(jsonObject);
        }else{
            jsonObj.put("status", 0);
            jsonObj.put("msg", "不存在该订单");
            return jsonObj.toString();
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    public Boolean notEmply(String[] s){
        for (int i = 0; i <s.length ; i++) {
            if(s[i] == null || s[i].equals("")){
                return true;
            }
        }
        return false;
    }
    //TODO
    /**
     * @return 公众号出售列表
     */
    //TODO
    /**
     * @return 出售公众号详情
     */
}
