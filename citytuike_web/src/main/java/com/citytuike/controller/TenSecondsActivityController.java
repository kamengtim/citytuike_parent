package com.citytuike.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.*;
import com.citytuike.service.*;
import com.citytuike.util.MD5Utils;
import com.github.pagehelper.PageInfo;
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
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("api/TenSecondsActivity")
public class TenSecondsActivityController extends BaseController{
    @Autowired
    private TpUsersService tpUsersService;
    @Autowired
    private TpTenSecondsActivityConfigService tpTenSecondsActivityConfigService;
    @Autowired
    private TpTenSecondsActivityLogService tpTenSecondsActivityLogService;
    @Autowired
    private TpTenSecondsActivityRewardService tpTenSecondsActivityRewardService;
    @Autowired
    private TpTenSecondsActivityRewardLogService tpTenSecondsActivityRewardLogService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TpUserAddressService tpUserAddressService;
    @Autowired
    private TpRegionService tpRegionService;
    @Autowired
    private TpGoodsService tpGoodsService;
    /**
     * @param
     * @return
     * 活动初始化
     */
    @RequestMapping(value = "init_activity",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "活动初始化", notes = "活动初始化")
    public @ResponseBody String init_activity(HttpServletRequest request,
                                              @RequestParam(required = true)String activity_id,
                                              @RequestParam(required = false)String invite_code){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject jsonObject = this.getObj(activity_id,invite_code, String.valueOf(tpUsers.getUser_id()));
        JSONObject oldObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject object= new JSONObject();
        JSONObject userJson = new JSONObject();
        List<TpTenSecondsActivityReward> tpTenSecondsActivityRewards = tpTenSecondsActivityRewardService.getReward(activity_id);
        for (TpTenSecondsActivityReward tpTenSecondsActivityReward : tpTenSecondsActivityRewards) {
            tpTenSecondsActivityReward.setName(tpTenSecondsActivityReward.getAlias());
            oldObj = tpTenSecondsActivityRewardService.getJson(tpTenSecondsActivityReward);
            jsonArray.add(oldObj);
            //TODO 待处理
        }
        //用户剩余抽奖次数
        TpTenSecondsActivityConfig tpTenSecondsActivityConfig = (TpTenSecondsActivityConfig) jsonObject.get("tpTenSecondsActivityConfig");
        TpUsers userInfo = (TpUsers) jsonObject.get("userInfo");
        TpUsers share_user_info = (TpUsers) jsonObject.get("share_user_info");
        JSONObject newJsonObject = this.getUserNumber(tpTenSecondsActivityConfig.getActivity_num(),userInfo,share_user_info,activity_id);
        object.put("user",newJsonObject);
        userJson.put("title",jsonObject.get("title"));
        userJson.put("desc",jsonObject.get("desc"));
        object.put("reward",userJson);
        object.put("reward_list",jsonArray);
        jsonObj.put("result",object);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    private JSONObject getUserNumber(Integer activity_num, TpUsers userInfo, TpUsers share_user_info,String activity_id) {
        JSONObject jsonObj = new JSONObject();
        if(share_user_info != null){
            int Count = tpTenSecondsActivityRewardLogService.checkWeekShare(userInfo.getUser_id(),share_user_info.getUser_id());
            if(Count >= 1){
                jsonObj.put("is_get_reward",0);
            }else {
                jsonObj.put("is_get_reward", 1);
            }
        }
        int Count = tpTenSecondsActivityRewardLogService.getLogCount(userInfo.getUser_id(),activity_id);
        int number = activity_num - Count <= 0 ? 0 : activity_num - Count;
        jsonObj.put("luck_draw",number);
        return jsonObj;
    }


    //初始化
    private JSONObject getObj(String activity_id, String invite_code, String user_id) {
        JSONObject jsonObject = new JSONObject();
        TpUsers userInfo = new TpUsers();
        TpUsers share_user_info = new TpUsers();
        //如果invite_code不为空,那是分享给别人抽的
        if(invite_code != null){
            share_user_info = tpUsersService.getInviteCode(invite_code);
            if(share_user_info == null){
                jsonObject.put("status",0);
                jsonObject.put("msg","分享人不存在");
                jsonObject.put("result","[]");
            }
            jsonObject.put("share_user_info",share_user_info);
        }
        if (user_id + "" != null) {
            //没有分享人是自己抽奖
            userInfo = tpUsersService.getUserInfo(user_id);
            jsonObject.put("userInfo", userInfo);

        }
        TpTenSecondsActivityConfig tpTenSecondsActivityConfig = tpTenSecondsActivityConfigService.getConfig(activity_id);
        jsonObject.put("tpTenSecondsActivityConfig", tpTenSecondsActivityConfig);
        jsonObject.put("title", tpTenSecondsActivityConfig.getActivity_title());
        jsonObject.put("desc", tpTenSecondsActivityConfig.getActivity_desc());

        return jsonObject;
    }
    /**
     * @param
     * @return
     * 开始抽奖
     */
    @RequestMapping(value = "begin_luck_draw",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "开始抽奖", notes = "开始抽奖")
    public @ResponseBody String begin_luck_draw(HttpServletRequest request,
                                                @RequestParam(required = true)String activity_id,
                                                @RequestParam(required = false)String invite_code){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject newjsonObject = new JSONObject();
        String is_get_reward = "";
        List<TpTenSecondsActivityReward> tpTenSecondsActivityRewards = tpTenSecondsActivityRewardService.getReward(activity_id);
        for (TpTenSecondsActivityReward tpTenSecondsActivityReward : tpTenSecondsActivityRewards) {
            newjsonObject.put("stock",tpTenSecondsActivityRewardService.getJson(tpTenSecondsActivityReward));
            //TODO 待处理
        }

        is_get_reward = this.check_user_auth(activity_id,tpUsers.getUser_id());
        TpUsers share_user_info = tpUsersService.getInviteCode(invite_code);
        JSONObject jsonObject = this.getObj(activity_id,invite_code, String.valueOf(tpUsers.getUser_id()));
        TpTenSecondsActivityConfig tpTenSecondsActivityConfig = (TpTenSecondsActivityConfig) jsonObject.get("tpTenSecondsActivityConfig");
        if(share_user_info != null){
            Boolean dayShare = this.check_day_share(share_user_info);
            if(!dayShare){
                is_get_reward = "0";
            }
            //检查本周是否助战过
            Boolean week_share = this.check_week_share(tpUsers.getUser_id(),share_user_info);
            if(!week_share){
                jsonObj.put("status", 0);
                jsonObj.put("msg","本周你已帮助过"+share_user_info.getNickname()+"咯!,下周再试试吧");
                return jsonObj.toString();
            }
        }else{
            JSONObject userNumber = getUserNumber(tpTenSecondsActivityConfig.getActivity_num(), tpUsers, share_user_info, activity_id);
            int luck_draw = (int) userNumber.get("luck_draw");
            if(luck_draw <= 0){
                jsonObj.put("status",0);
                jsonObj.put("msg","抽奖次数已用完，明天再接再厉~");
                return jsonObj.toString();
            }
        }
        if(is_get_reward.equals("1")){
            //如果可以获奖查看查看中奖率
            BigDecimal probability = this.get_user_probability(share_user_info,tpUsers,tpTenSecondsActivityConfig);
            jsonObj.put("is_get_reward",probability);
        }else{
            jsonObj.put("is_get_reward",0);
        }
        newjsonObject.put("csrf_token", MD5Utils.md5("123"));
        redisTemplate.opsForValue().set("csrf_token",MD5Utils.md5("123"));
        jsonObj.put("result",newjsonObject);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }

    private BigDecimal get_user_probability(TpUsers share_user_info, TpUsers tpUsers, TpTenSecondsActivityConfig tpTenSecondsActivityConfig) {
        if(share_user_info == null || tpUsers.getRelation() == null || tpTenSecondsActivityConfig.getAgent_config() == null){
            double pro = (tpTenSecondsActivityConfig.getProbability())/100.00;
            DecimalFormat df = new DecimalFormat("#.00");
            return new BigDecimal(df.format(pro));
        }
        String[] arr = tpUsers.getRelation().split(",");
        int all_level = arr.length;
        //求全局概率
        //把字符串转成json
        JSONArray json = (JSONArray) JSONArray.parse(tpTenSecondsActivityConfig.getAgent_config());
        //遍历json
        for (Object obj : json) {
            JSONObject jo = (JSONObject)obj;
            //拿到user_info并判断json中的user_info在不在数组中
            int userInfo = jo.getInteger("user_info");
            int a = Arrays.binarySearch(arr, userInfo+"");
            if(a>=0){
                for (int i = 0; i < arr.length; i++) {
                    //如果在数组中判断是否为分享人的id
                    if (Integer.valueOf(arr[i]).intValue() == Integer.valueOf(String.valueOf(jo.get("user_info")))) {
                        int nowLevel = all_level - i;
                        if(nowLevel<=Integer.parseInt((String) jo.get("user_level"))){
                            return new BigDecimal((String)jo.get("user_prob"));
                        }
                    }
                }
            }
        }
        double pro = (tpTenSecondsActivityConfig.getProbability())/100.00;
        DecimalFormat df = new DecimalFormat("#.00");
        return new BigDecimal(df.format(pro));
    }

    private Boolean check_week_share(Integer user_id,TpUsers share_user_info) {
        int count = tpTenSecondsActivityRewardLogService.checkWeekShare(user_id,share_user_info.getUser_id());
        if(count >= 1 ){
            return false;
        }else{
            return true;
        }
    }

    private Boolean check_day_share(TpUsers share_user_info) {
        if(share_user_info == null){
            return true;
        }
        //一天只能两个好友助战
        int count = tpTenSecondsActivityRewardLogService.checkDayShare(share_user_info.getUser_id());
        if(count>2){
            return false;
        }else{
            return true;
        }
    }

    private String check_user_auth(String activity_id,Integer user_id) {
        //一个小时只能有一个中奖,一个人一周只能中奖一次
        TpTenSecondsActivityConfig tpTenSecondsActivityConfig = tpTenSecondsActivityConfigService.getConfig(activity_id);
        if(tpTenSecondsActivityConfig.getHour_num()>0){
            int count = tpTenSecondsActivityRewardLogService.selectCount(activity_id);
            if(count > tpTenSecondsActivityConfig.getHour_num() || count != 0){
                return "0";
            }
            if(tpTenSecondsActivityConfig.getWeek_num()>0){
                int WeekCount = tpTenSecondsActivityRewardLogService.selectWeekCount(activity_id,user_id);
                if(WeekCount > tpTenSecondsActivityConfig.getWeek_num() || WeekCount!= 0){
                    return "0";
                }else{
                    return "1";
                }
            }else{
                return "0";
            }
        }else{
            return "0";
        }
    }
    /**
     * @param
     * @return
     * 领取奖励
     */
    @RequestMapping(value = "draw_reward",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "领取奖励", notes = "领取奖励")
    public @ResponseBody String draw_reward(HttpServletRequest request,
                                            @RequestParam(required = true)String second,
                                            @RequestParam(required = true)String activity_id,
                                            @RequestParam(required = false)String invite_code){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject newjsonObject = new JSONObject();
        JSONObject jsonObject = this.getObj(activity_id,invite_code, String.valueOf(tpUsers.getUser_id()));
        TpUsers userInfo = (TpUsers) jsonObject.get("userInfo");
        TpUsers share_user_info = (TpUsers) jsonObject.get("share_user_info");
        TpTenSecondsActivityConfig tpTenSecondsActivityConfig = (TpTenSecondsActivityConfig) jsonObject.get("tpTenSecondsActivityConfig");
        String nickName = invite_code == null ? userInfo.getNickname() : share_user_info.getNickname();
        if(share_user_info == null && userInfo.getUser_id() != 2951){
            JSONObject userNumber = getUserNumber(tpTenSecondsActivityConfig.getActivity_num(), userInfo, share_user_info, activity_id);
            if(Integer.parseInt(String.valueOf(userNumber.get("luck_draw")))<=0){
                jsonObj.put("status",0);
                jsonObj.put("msg","抽奖次数已用完，明天再接再厉~");
                return jsonObj.toString();
            }
        }
        String s = begin_luck_draw(request, activity_id, invite_code);
        org.json.JSONObject object = new org.json.JSONObject(s);
        String status = (String) object.get("status");
        if(status.equals("0")){
            return object.toString();
        }
        Object result = object.get("is_get_reward");
        org.json.JSONObject pase = new org.json.JSONObject(result);
        BigDecimal bg = new BigDecimal(second);
        double newSecond =bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        TpTenSecondsActivityReward tpTenSecondsActivityReward = tpTenSecondsActivityRewardService.getRewardBySecond(String.valueOf(newSecond),activity_id);
        //String rewardGet = (String) pase.get("is_get_reward");
        //插入数据
        newjsonObject = tpTenSecondsActivityRewardLogService.insertLog(String.valueOf(result), activity_id, share_user_info, tpUsers, second, tpTenSecondsActivityReward,nickName);
        return newjsonObject.toString();
    }
    /**
     * @param
     * @return
     * 中奖记录列表
     */
    @RequestMapping(value = "reward_list",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "中奖记录列表", notes = "中奖记录列表")
    public @ResponseBody String reward_list(HttpServletRequest request){
        JSONObject jsonObj= new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject object = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PageInfo pageInfo = tpTenSecondsActivityRewardLogService.reward_list();
        List<TpTenSecondsActivityRewardLog> tpTenSecondsActivityRewardLogs = pageInfo.getList();
        for (TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog : tpTenSecondsActivityRewardLogs) {
            jsonObject=tpTenSecondsActivityRewardLogService.getJson(tpTenSecondsActivityRewardLog);
            jsonArray.add(jsonObject);
        }
        object.put("data",jsonArray);
        object.put("total",pageInfo.getTotal());
        object.put("per_page",pageInfo.getPrePage());
        object.put("current_page",pageInfo.getPageNum());
        object.put("last_page",pageInfo.getLastPage());
        jsonObj.put("result",object);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }
    /**
     * @param
     * @return
     * 填写地址(领奖)
     */
    @RequestMapping(value = "add_address",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "填写地址", notes = "填写地址")
    public @ResponseBody
    String add_address(HttpServletRequest request,
                       @RequestParam(required = true)String reward_id,
                       @RequestParam(required = true)String address_id){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog = tpTenSecondsActivityRewardLogService.getLogById(reward_id,tpUsers.getUser_id());
        if(tpTenSecondsActivityRewardLog == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "记录不存在!");
        }else if(tpTenSecondsActivityRewardLog.getAddress() != null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "该奖励已领取!");
        }
        TpUserAddress tpUserAddress = tpUserAddressService.getAddress(address_id,tpUsers.getUser_id());
        if(tpUserAddress == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "地址不存在!");
        }
        Integer provinceName = tpUserAddress.getProvince();
        Integer cityName = tpUserAddress.getCity();
        Integer districtName = tpUserAddress.getDistrict();
        Integer twonName = tpUserAddress.getTwon();
        String province = tpRegionService.getProvince(provinceName);
        String city = tpRegionService.getCityName(cityName);
        String district = tpRegionService.getDistrict(districtName);
        String twon = tpRegionService.getTwon(twonName);
        tpTenSecondsActivityRewardLog.setProvince(province);
        tpTenSecondsActivityRewardLog.setCity(city);
        tpTenSecondsActivityRewardLog.setDistrict(district);
        tpTenSecondsActivityRewardLog.setTwon(twon);
        tpTenSecondsActivityRewardLog.setMobile(tpUserAddress.getMobile());
        tpTenSecondsActivityRewardLog.setAddress(tpUserAddress.getAddress());
        int i = tpTenSecondsActivityRewardLogService.update(tpTenSecondsActivityRewardLog);
        if(i>0){
            jsonObj.put("status", 1);
            jsonObj.put("msg", "領取成功!");
        }
        return jsonObj.toString();
    }
    /**
     * @param
     * @return
     * 中奖记录详情
     */
    @RequestMapping(value = "reward_detail",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "中奖记录详情", notes = "中奖记录详情")
    public @ResponseBody String reward_detail(HttpServletRequest request,
                                              @RequestParam(required = true)String log_id){
        String image = "https://citycdn.citytuike.cn/assets/images/logo.ce2a6d9c.png";
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 0);
        jsonObj.put("msg", "失败!");
        TpUsers tpUsers = initUser(request);
        if (null == tpUsers) {
            jsonObj.put("status", -2);
            jsonObj.put("msg", "token失效!");
            return jsonObj.toString();
        }
        JSONObject goods = new JSONObject();
        TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog = tpTenSecondsActivityRewardLogService.getReward(log_id);
        TpTenSecondsActivityReward tpTenSecondsActivityReward = tpTenSecondsActivityRewardService.getRewardById(tpTenSecondsActivityRewardLog.getReward_id());
        JSONObject json = tpTenSecondsActivityRewardLogService.getJson(tpTenSecondsActivityRewardLog);
        if(tpTenSecondsActivityRewardLog == null){
            jsonObj.put("status", 0);
            jsonObj.put("msg", "記錄不存在!");
        }
        if(tpTenSecondsActivityReward.getGoods_id() == 144){
            Integer id = 144;
            TpGoods tpGoods = tpGoodsService.getGoodsById(id);
            goods.put("image",tpGoods.getOriginal_img() == null ? image : tpGoods.getOriginal_img());
            goods.put("goods_name",tpGoods.getGoods_name());
            goods.put("goods_desc","");
            goods.put("price",tpGoods.getShop_price());
            goods.put("org_price",tpGoods.getMarket_price());
            goods.put("number","1");
        }else if(tpTenSecondsActivityReward.getGoods_id() == 146){
            Integer id = 146;
            TpGoods tpGoods = tpGoodsService.getGoodsById(id);
            goods.put("image",tpGoods.getOriginal_img() == null ? image : tpGoods.getOriginal_img());
            goods.put("goods_name",tpTenSecondsActivityRewardLog.getReward_name());
            goods.put("goods_desc","");
            goods.put("price","0");
            goods.put("org_price","0");
            goods.put("number","1");
        }else{
            /*Integer id = 0;
            TpGoods tpGoods = tpGoodsService.getGoodsById(id);
            goods.put("image",tpGoods.getOriginal_img() == null ? image : tpGoods.getOriginal_img());*/
            goods.put("image",image);
            goods.put("goods_name",tpTenSecondsActivityRewardLog.getReward_name());
            goods.put("goods_desc","");
            goods.put("price","0");
            goods.put("org_price","0");
            goods.put("number","1");
        }
        json.put("goods",goods);
        jsonObj.put("result",json);
        jsonObj.put("status", 1);
        jsonObj.put("msg", "ok!");
        return jsonObj.toString();
    }
}
