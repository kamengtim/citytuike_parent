package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.interceptor.RedisConstant;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.Util;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("api/Fans")
public class FansController {
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
    public @ResponseBody String getConf(@RequestParam(required = true) String token,
                                        @RequestParam(required = false) String area_id,
                                        @RequestParam(required = false) String industry){
        JSONObject  jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject data = new JSONObject();

        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        PageInfo pageInfo = tpFansService.getLimtPageList(area_id,industry);
        List<TpFans>tpFans = pageInfo.getList();
        for (TpFans tpFan : tpFans) {
           JSONObject jsonObject = tpFansService.getJson(tpFan);
            jsonArray.add(jsonObject);
        }
        data.put("current_page", pageInfo.getPageNum());
        data.put("total", pageInfo.getTotal());
        data.put("per_page", pageInfo.getPrePage());
        data.put("last_page",pageInfo.getPages());
        data.put("",jsonArray);
        jsonObj.put("result",data);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 粉丝公众号列表
     */
    @RequestMapping(value = "fansDetails", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String fansDetails(@RequestParam(required = true) String token,
                                            @RequestParam(required = true) String id){
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpFans tpFans = tpFansService.fansDetails(id);
        if(tpFans != null){
            TpRegion tpRegion = tpRegionService.getNameByFanId(tpFans.getAddress());
            JSONObject  jsonObject = tpFansService.getJson(tpFans,tpRegion);
            jsonObj.put("result",jsonObject);
        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 粉丝公众号列表
     */
    @RequestMapping(value = "fansTypeList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String fansTypeList(){
        JSONObject jsonObj = new JSONObject();
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
        jsonObj.put("per_page", pageInfo.getPrePage());
        jsonObj.put("last_page",pageInfo.getPages());
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return我要出售
     */
    @RequestMapping(value = "fansSale", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String fansSale(@RequestParam(required = true) int user_id,
                                         @RequestParam(required = true) String token,
                                         @RequestParam(required = true) String industry,
                                         @RequestParam(required = true) String createtime,
                                         @RequestParam(required = true) String account,
                                         @RequestParam(required = true) String mobile,
                                         @RequestParam(required = true) String mobile_code,
                                         @RequestParam(required = true) String wechat){
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        int code = tpSmsLogService.selectCode(mobile_code);
        TpFansSale tpFansSale = new TpFansSale();
        tpFansSale.setUser_id(user_id);
        tpFansSale.setIndustry(industry);
        tpFansSale.setCreatetime(Integer.parseInt(createtime));
        tpFansSale.setAccount(account);
        tpFansSale.setMobile(mobile);
        tpFansSale.setWechat(wechat);
        if(code > 0){
            tpFansSaleService.fansSale(tpFansSale);
        }else{
            jsonObj.put("status", "0");
            jsonObj.put("msg", "验证码错误");
            return jsonObj.toString();
        }
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return估值
     */
    @RequestMapping(value = "saveFansList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveFansList(@RequestParam(required = true) String token,
                                         @RequestParam(required = true) int sel_class,
                                         @RequestParam(required = true) int tag,
                                         @RequestParam(required = true) String account,
                                         @RequestParam(required = true) String fan_num,
                                         @RequestParam(required = true) String scale_man,
                                         @RequestParam(required = true) String scale_women,
                                         @RequestParam(required = true) int auth_bodys,
                                         @RequestParam(required = true) int auth_body_change,
                                         @RequestParam(required = true) int original,
                                         @RequestParam(required = true) String monmoney,
                                         @RequestParam(required = true) String admoney,
                                         @RequestParam(required = true) int read_num,
                                         @RequestParam(required = true) boolean industry,
                                         @RequestParam(required = true) String wechat,
                                         @RequestParam(required = true) String mobile,
                                         @RequestParam(required = true) int mobile_code,
                                         @RequestParam(required = true) String title,
                                         @RequestParam(required = true) String logo,
                                         @RequestParam(required = true) int address){
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        String fan_dev = "";
        int code = tpSmsLogService.selectvalidateCode(String.valueOf(mobile_code),mobile);
        if(code <= 0){
            jsonObj.put("status", "0");
            jsonObj.put("msg", "验证码错误");
            return jsonObj.toString();
        }
        if (Integer.parseInt(scale_man) > Integer.parseInt(scale_women)){
            fan_dev =  "男粉丝多";
        }else{
            fan_dev =  "女粉丝多";
        }
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 获取行业信息
     */
    @RequestMapping(value = "getIndustry", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getIndustry(@RequestParam(required = true) String token){
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        JSONObject jsonArray = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        List<TpIndustry> tpIndustries = tpIndustryService.getIndustry();
        for (TpIndustry tpIndustry : tpIndustries) {
            jsonArray = tpIndustryService.getJson(tpIndustry);
            jsonArray1.add(jsonArray);
            List<TpIndustry> tpIndustriesSons = tpIndustryService.getIndustrySon(tpIndustry.getFid());
            if(tpIndustriesSons.size()==0){
                jsonArray.put("son","[]");
            }
            for (TpIndustry tpIndustriesSon : tpIndustriesSons) {
                JSONObject arry =  tpIndustryService.getSonJson(tpIndustriesSon);
                jsonArray.put("son",arry);
            }
        }
        jsonObj.put("result",jsonArray1);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 粉丝支付接口
     */
    @RequestMapping(value = "getFansOrderSn", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getFansOrderSn(HttpServletRequest request,
                                               @RequestParam(required = true) Integer id){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        String header = request.getHeader("p-token");
        String order_sn = "";
        if(header == null || header.trim().length() == 0){
            throw new RuntimeException("登录异常");
        }

        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = tpUsersService.getToken(token);
        if(tpUsers == null){
            throw new RuntimeException("登录超时");
        }
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 我要增粉
     */
    @RequestMapping(value = "addNeedFans", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String addNeedFans(@RequestParam(required = true) String nickname,
                                               @RequestParam(required = true) String token,
                                               @RequestParam(required = true) String uid,
                                               @RequestParam(required = true) String contacts,
                                               @RequestParam(required = true) String mobile,
                                               @RequestParam(required = true) String qq,
                                               @RequestParam(required = true) String wechat,
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
                                               @RequestParam(required = true) int type_sel,
                                               @RequestParam(required = true) int wechat_type,
                                               @RequestParam(required = true) String head_img){
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
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
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }
    /**
     * @return 我要增粉列表
     */
    @RequestMapping(value = "NeedFansList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String NeedFansList(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String header = request.getHeader("p-token");
        if(header == null || header.trim().length() == 0){
            throw new RuntimeException("登录异常");
        }

        String token = (String) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ header);
        TpUsers tpUsers = tpUsersService.getToken(token);
        if(tpUsers == null){
            throw new RuntimeException("登录超时");
        }
        List<TpFansNeed> tpFansNeeds = tpFansNeedService.NeedFansList(tpUsers.getUser_id());
        if(tpFansNeeds != null){
            for (TpFansNeed tpFansNeed : tpFansNeeds) {
                if(tpFansNeed.getIs_flag() == true){
                   int fans_count = tpFansNeedService.getFanNum(tpFansNeed.getId(),1,tpUsers.getUser_id());
                   int fans_single = tpFansNeedService.getFanNum(tpFansNeed.getId(),2,tpUsers.getUser_id());
                }
            JSONObject jsonObject = tpFansNeedService.getJson(tpFansNeed);
            jsonArray.add(jsonObject);
            }
        }else {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "没有订单");
            return jsonObj.toString();
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

    /**
     * @return 我要增粉详情
     */
    @RequestMapping(value = "NeedFansDetails", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String NeedFansDetails(HttpServletRequest request,
                                                @RequestParam(required = true) int order_id,
                                                @RequestParam(required = true) String token){
        JSONArray jsonArray = new JSONArray();
        TpUsers tpUsers = tpUsersService.findOneByToken(token);
        JSONObject  jsonObj = new JSONObject();
        if (null == tpUsers) {
            jsonObj.put("status", "0");
            jsonObj.put("msg", "请先登陆!");
            return jsonObj.toString();
        }
        TpFansNeed tpFansNeed = tpFansNeedService.getNeedByOrderId(tpUsers.getUser_id(),order_id);
        if(tpFansNeed != null){
            if(tpFansNeed.getFlag() == true){
                int fans_count = tpFansNeedService.getFanNum(order_id,1,tpUsers.getUser_id());
                int fans_single = tpFansNeedService.getFanNum(order_id,2,tpUsers.getUser_id());
            }
            JSONObject jsonObject = tpFansNeedService.getJson(tpFansNeed);
            jsonArray.add(jsonObject);
        }else{
            jsonObj.put("status", "0");
            jsonObj.put("msg", "不存在该订单");
            return jsonObj.toString();
        }
        jsonObj.put("result",jsonArray);
        jsonObj.put("status", "1");
        jsonObj.put("msg", "成功");
        return jsonObj.toString();
    }

}
