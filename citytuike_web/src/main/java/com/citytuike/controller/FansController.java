package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.interceptor.RedisConstant;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
            return "验证码错误";
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
        int code = tpSmsLogService.selectCode(String.valueOf(mobile_code));
        if(code <= 0){
         return "验证码错误";
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
        //Todo 这里需要做登录校验
        String token = request.getHeader("p-token");
        if(token == null || token.trim().length() == 0){
            throw new RuntimeException("登录异常");
        }
        TpUsers tpUsers = (TpUsers) redisTemplate.opsForValue().get(RedisConstant.CURRENT_USER+ token);
        if(tpUsers == null){
            throw new RuntimeException("登录超时");
        }

        return null;
    }
}
