package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.mapper.TpCardListMapper;
import com.citytuike.mapper.TpCartGiftMapper;
import com.citytuike.mapper.TpUserLevelMapper;
import com.citytuike.model.*;
import com.citytuike.util.HttpUtils;
import com.citytuike.util.MD5Utils;
import com.citytuike.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TpCartGiftServiceImpl implements TpCartGiftService {
    String orderid = Util.transferLongToDate("yyyyMMdd",new Date().getTime() / 1000) + "" + new Random().nextInt(1000) + 8999;
    @Autowired
    private TpCartGiftMapper tpCartGiftMapper;
    @Autowired
    private TpCardListMapper tpCardListMapper;
    @Autowired
    private TpUserLevelMapper tpUserLevelMapper;

    @Override
    public int getCount(Integer user_id) {
        int count = tpCartGiftMapper.getCount(user_id);
        return count;
    }

    @Override
    public PageInfo query(int count, Integer user_id) {
        Page page = new Page();
        page.setTotalCount(count);
        PageHelper.startPage(page.getPageNow()+1, page.getPageSize());
        List<TpCartGift> list = tpCartGiftMapper.selectByPage(page.getPageNow(), 10, user_id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public JSONObject getJson(TpUsers tpUsers, TpCartGift tpCartGift, Integer level, String nickname) {
        JSONObject jsonObject = new JSONObject();
        String level_name = tpUserLevelMapper.selectLevelName(tpUsers.getLevel());
        jsonObject.put("id", tpCartGift.getId());
        jsonObject.put("gift_name", tpCartGift.getGift_name());
        jsonObject.put("create_time", tpCartGift.getCreate_time());
        jsonObject.put("add_time", tpCartGift.getAdd_time());
        jsonObject.put("gift_type", tpCartGift.getGift_type());
        jsonObject.put("status", tpCartGift.getStatus());
        jsonObject.put("money", tpCartGift.getMoney());
        jsonObject.put("user_id", tpCartGift.getUser_id());
        jsonObject.put("card_list_id", tpCartGift.getCard_list_id());
        jsonObject.put("report_id", tpCartGift.getReport_id());
        jsonObject.put("mess", tpCartGift.getMess());
        jsonObject.put("orderid", tpCartGift.getOrderid());
        jsonObject.put("mobile", tpCartGift.getMobile());
        jsonObject.put("taskid", tpCartGift.getTaskid());
        jsonObject.put("obtain", tpCartGift.getObtain());
        jsonObject.put("user_time", tpCartGift.getUser_time());
        TpCardList tpCardList = tpCardListMapper.getCard(tpCartGift.getCard_list_id());

        jsonObject.put("level", tpUsers.getLevel());
        jsonObject.put("nickname", nickname);
        jsonObject.put("level_name", level_name);
        jsonObject.put("bank_name", tpCardList.getTitle());
        jsonObject.put("logo", tpCardList.getLogo());
        return jsonObject;
    }

    @Override
    public TpCartGift getCartGift(int id, Integer user_id) {
        TpCartGift tpCartGift = tpCartGiftMapper.getCartGift(id, user_id);
        return tpCartGift;
    }

    @Override
    public Map<String, Object> refill(String mobile, BigDecimal money) {
        Map<String, Object> res = null;
        Integer packge = money.intValue();
        String data = Util.transferLongToDate("yyyyMMddHHmmss", new Date().getTime()/1000);
        String sign = MD5Utils.md5("apikey=" + Constant.MOBILE_APIKEY + "&mobile=" + mobile + "&orderid=" + orderid + "&package=" + packge + "&timestamp=" + data + "&username=" + Constant.MOBILE_USERNAME);
        String url = "https://api.ihuyi.com/f/phone";
        String param = "action=recharge&username=" + Constant.MOBILE_USERNAME + "&mobile=" + mobile + "&package=" + packge + "&orderid=" + orderid + "&timestamp=" + data + "&sign=" + sign;
        String str = HttpUtils.sendGet(url, param);
        try {
            Gson gson = new Gson();
            res = gson.fromJson(str, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
        }
        return res;
    }

    @Override
    public int update(int id, Integer user_id, String mobile, String taskid) {
        int time = (int)new Date().getTime()/1000;
        int i = tpCartGiftMapper.update(id,user_id,orderid,taskid,time,mobile);
        return i;
    }

    @Override
    public TpCartGift getCartGiftByUserId(Integer user_id) {
        TpCartGift tpCartGift = tpCartGiftMapper.getCartGiftByUserId(user_id);
        return tpCartGift;
    }

    @Override
    public int getGifts(Integer user_id, int id, int gift_type, String gift_name, int date) {
       int a =  tpCartGiftMapper.getGifts(user_id,id,gift_type,gift_name,date);
       return a;
    }

    @Override
    public PageInfo queryList(Integer user_id) {
        Page page = new Page();
        PageHelper.startPage(page.getPageNow()+1, page.getPageSize());
        List<TpCartGift> list = tpCartGiftMapper.queryList(user_id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public JSONObject getJsonCartGift(TpCartGift tpCartGift) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", tpCartGift.getId());
        jsonObject.put("gift_name", tpCartGift.getGift_name());
        jsonObject.put("create_time", tpCartGift.getCreate_time());
        jsonObject.put("add_time", tpCartGift.getAdd_time());
        jsonObject.put("gift_type", tpCartGift.getGift_type());
        jsonObject.put("status", tpCartGift.getStatus());
        jsonObject.put("money", tpCartGift.getMoney());
        jsonObject.put("user_id", tpCartGift.getUser_id());
        jsonObject.put("card_list_id", tpCartGift.getCard_list_id());
        jsonObject.put("report_id", tpCartGift.getReport_id());
        jsonObject.put("mess", tpCartGift.getMess());
        jsonObject.put("orderid", tpCartGift.getOrderid());
        jsonObject.put("mobile", tpCartGift.getMobile());
        jsonObject.put("taskid", tpCartGift.getTaskid());
        jsonObject.put("obtain", tpCartGift.getObtain());
        jsonObject.put("user_time", tpCartGift.getUser_time());
        return jsonObject;
    }

    @Override
    public TpCartGift useGifts(Integer user_id, int id) {
        TpCartGift tpCartGift = tpCartGiftMapper.useGifts(user_id,id);
        return tpCartGift;
    }
}