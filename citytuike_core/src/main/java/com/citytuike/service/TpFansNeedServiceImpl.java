package com.citytuike.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.exception.WeixinApiException;
import com.citytuike.mapper.TpFansNeedMapper;
import com.citytuike.mapper.TpWxFansMapper;
import com.citytuike.mapper.TpWxUserMapper;
import com.citytuike.model.TpFansNeed;
import com.citytuike.model.TpWxFans;
import com.citytuike.model.TpWxUser;
import com.citytuike.util.HttpUtils;
import com.citytuike.util.Util;
import com.citytuike.util.WeixinAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TpFansNeedServiceImpl implements TpFansNeedService {
    // 访问每天变化的url
    private static final String USERSUBDATAURL = "https://api.weixin.qq.com/datacube/getusersummary";
    // 访问每天总数量的url
    private static final String USERALLDATAURL = "https://api.weixin.qq.com/datacube/getusercumulate";

    @Autowired
    private TpFansNeedMapper tpFansNeedMapper;
    @Autowired
    private TpWxUserMapper tpWxUserMapper;
    @Autowired
    private TpWxFansMapper tpWxFansMapper;

    @Override
    public void save(TpFansNeed tpFansNeed) {
        tpFansNeedMapper.save(tpFansNeed);
    }

    @Override
    public List<TpFansNeed> NeedFansList(Integer user_id) {
        List<TpFansNeed> tpFansNeeds = tpFansNeedMapper.selectByPrimaryKey(user_id);
        return tpFansNeeds;
    }

    @Override
    public int getFanNum(Integer id, int status,Integer user_id) {
        Long begin = (long) 2 * 24 * 60 * 60;
        Long end = (long) 1 * 24 * 60 * 60;
        Long begin_time = new Date().getTime() / 1000 - begin;
        Long end_time = new Date().getTime() / 1000 - end;
        int a = 0;
        String begin_date = Util.transferLongToDate("yyyy-MM-dd", begin_time);
        String end_date = Util.transferLongToDate("yyyy-MM-dd", end_time);
        String ref_date = Util.transferLongToDate("yyyy-MM-dd", new Date().getTime() / 1000 - end);
        if (status == 1) {
            List<TpWxFans> tpWxFans = tpWxFansMapper.getWxFans(user_id, id);
            if (tpWxFans.size() != 0) {
                int size = tpWxFans.size();
                return size;
            } else {
                JSONObject jsonobject = new JSONObject();
                jsonobject.put("begin_date", begin_date);
                jsonobject.put("end_date", end_date);
                //获得每天新增关注的人数

                Integer canceluser = Integer.parseInt(getKv(jsonobject, "cancel_user",
                        USERSUBDATAURL));
                //获得总的人数
                Integer cumulateuser = Integer.parseInt(getKv(jsonobject,
                        "cumulate_user", USERALLDATAURL));
                //表关联对象
                TpWxFans tpWxFans1 = new TpWxFans();
                tpWxFans1.setCount_ref_date(ref_date);
                tpWxFans1.setFlag_date(ref_date);
                tpWxFans1.setAdd_time((int) (new Date().getTime() / 1000));
                tpWxFans1.setCumulate_user(cumulateuser);
                tpWxFans1.setUser_id(user_id);
                tpWxFans1.setNeed_fans_id(id);

                List<TpWxFans> tpWxFanses = tpWxFansMapper.getWxFansByWx(user_id, id, ref_date);
                if (tpWxFanses.size() !=  0) {
                    for (TpWxFans wxFans : tpWxFanses) {
                        tpWxFans1.setId(wxFans.getId());
                        a = tpWxFansMapper.updateWx(id, ref_date, wxFans.getAdd_time(), wxFans.getCount_ref_date(),cumulateuser, wxFans.getUser_id());
                        break;
                    }
                } else {
                    a = tpWxFansMapper.insert(tpWxFans1);
                }
            return cumulateuser;
            }
        } else {
            List<TpWxFans> tpWxFans = tpWxFansMapper.addWx(user_id, id, ref_date);
            if (tpWxFans.size() != 0) {
                int size = tpWxFans.size();
                return size;
            } else {
                JSONObject jsonobject = new JSONObject();
                jsonobject.put("begin_date", begin_date);
                jsonobject.put("end_date", end_date);
                //获得每天新增关注的人数
                Integer newuser = Integer.parseInt(getKv(jsonobject, "new_user",
                        USERSUBDATAURL));
                //获得昨天取消关注的人数
                Integer canceluser = Integer.parseInt(getKv(jsonobject, "cancel_user",
                        USERSUBDATAURL));
                //表关联对象
                TpWxFans tpWxFans1 = new TpWxFans();
                tpWxFans1.setAdd_ref_date(ref_date);
                tpWxFans1.setFlag_date(ref_date);
                tpWxFans1.setCancel_user(canceluser);
                tpWxFans1.setAdd_time((int) (new Date().getTime() / 1000));
                tpWxFans1.setUser_id(user_id);
                tpWxFans1.setNeed_fans_id(id);
                tpWxFans1.setNew_user(newuser);
                List<TpWxFans> tpWxFanses = tpWxFansMapper.getWxFansByWx(user_id, id, ref_date);
                if (tpWxFanses.size() !=  0) {
                    for (TpWxFans wxFans : tpWxFanses) {
                        a = tpWxFansMapper.updateWxNew(wxFans.getId(),ref_date,canceluser, wxFans.getAdd_time(), wxFans.getCount_ref_date(), wxFans.getCumulate_user(), wxFans.getUser_id(), newuser,id);

                    }
                } else {
                    a = tpWxFansMapper.insert(tpWxFans1);
                }
            return newuser;
            }
        }
    }


    private String getKv(JSONObject jsonobject, String key, String url) {
        //发送请求,携带json参数
        try {
            url=url+"?access_token="+WeixinAPI.getAccessToken();
        } catch (WeixinApiException e) {
            e.printStackTrace();
        }
        JSONObject result = HttpUtils.httpPost(url, jsonobject);
        JSONObject jsonObject;
        String value = null;
        try {
            jsonObject = new JSONObject(result);
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            //如果里面没有,那么新增和取关都设置为0
            if (jsonArray == null || jsonArray.size() <= 0) {
                return "0";
            }
            jsonObject = (JSONObject) jsonArray.get(0);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }
    @Override
    public JSONObject getJson(TpFansNeed tpFansNeed) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpFansNeed.getId());
        jsonObject.put("user_id",tpFansNeed.getUser_id());
        jsonObject.put("nickname",tpFansNeed.getNickname());
        jsonObject.put("uid",tpFansNeed.getUid());
        jsonObject.put("contacts",tpFansNeed.getContacts());
        jsonObject.put("mobile",tpFansNeed.getMobile());
        jsonObject.put("qq",tpFansNeed.getQq());
        jsonObject.put("wechat",tpFansNeed.getWechat());
        jsonObject.put("email",tpFansNeed.getEmail());
        jsonObject.put("add_time",tpFansNeed.getAdd_time());
        jsonObject.put("industry",tpFansNeed.getIndustry());
        jsonObject.put("product",tpFansNeed.getProduct());
        jsonObject.put("message",tpFansNeed.getMessage());
        jsonObject.put("number",tpFansNeed.getNumber());
        jsonObject.put("fans_time",tpFansNeed.getFans_time());
        jsonObject.put("fans_sex",tpFansNeed.getFans_sex());
        jsonObject.put("area",tpFansNeed.getArea());
        jsonObject.put("company",tpFansNeed.getCompany());
        jsonObject.put("day_add_number",tpFansNeed.getDay_add_number());
        jsonObject.put("type_sel",tpFansNeed.getType_sel());
        jsonObject.put("wechat_type",tpFansNeed.getWechat_type());
        jsonObject.put("head_img",tpFansNeed.getHead_img());
        jsonObject.put("flag",tpFansNeed.getFlag());
        jsonObject.put("appid",tpFansNeed.getAppid());
        jsonObject.put("appsecret",tpFansNeed.getAppsecret());
        jsonObject.put("is_flag",tpFansNeed.getIs_flag());
        jsonObject.put("ip_flag",tpFansNeed.getIp_flag());
        return null;
    }

    @Override
    public TpFansNeed getNeedByOrderId(Integer user_id, int order_id) {
        TpFansNeed tpFansNeed = tpFansNeedMapper.getNeedByOrderId(user_id,order_id);
        return tpFansNeed;
    }
}
