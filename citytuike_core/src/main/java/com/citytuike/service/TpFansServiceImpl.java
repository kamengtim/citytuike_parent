package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpFansMapper;
import com.citytuike.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TpFansServiceImpl implements TpFansService {
    @Autowired
    private TpFansMapper tpFansMapper;

    @Override
    public PageInfo getLimtPageList(String area_id, String industry) {
        Page page = new Page();
        PageHelper.startPage(page.getPageNow(),page.getPageSize());
        List<TpFans> list = tpFansMapper.selectByPage(page.getPageNow(),page.getPageSize());
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public JSONObject getJson(TpFans tpFan) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpFan.getId());
        jsonObject.put("logo",tpFan.getLogo());
        jsonObject.put("tag",tpFan.getTag());
        jsonObject.put("title",tpFan.getTitle());
        jsonObject.put("fan_num",tpFan.getFan_num());
        jsonObject.put("fan_dev",tpFan.getFan_dev());
        jsonObject.put("createtime",tpFan.getCreatetime());
        jsonObject.put("shop_price",tpFan.getShop_price());
        jsonObject.put("address",tpFan.getAddress());
        jsonObject.put("catename",tpFan.getCatename());
        jsonObject.put("auth",tpFan.getAuth());
        jsonObject.put("auth_body",tpFan.getAuth_body());
        jsonObject.put("original",tpFan.getOriginal());
        jsonObject.put("read_num",tpFan.getRead_num());
        return jsonObject;
    }

    @Override
    public TpFans fansDetails(String id) {
        TpFans tpFans = tpFansMapper.fansDetails(id);
        return tpFans;
    }

    @Override
    public JSONObject getJson(TpFans tpFans, TpRegion tpRegion) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpFans.getId());
        jsonObject.put("logo",tpFans.getLogo());
        jsonObject.put("tag",tpFans.getTag());
        jsonObject.put("title",tpFans.getTitle());
        jsonObject.put("fan_num",tpFans.getFan_num());
        jsonObject.put("fan_dev",tpFans.getFan_dev());
        jsonObject.put("createtime",tpFans.getCreatetime());
        jsonObject.put("shop_price",tpFans.getShop_price());
        jsonObject.put("catename",tpFans.getCatename());
        jsonObject.put("auth",tpFans.getAuth());
        jsonObject.put("auth_body",tpFans.getAuth_body());
        jsonObject.put("original",tpFans.getOriginal());
        jsonObject.put("read_num",tpFans.getRead_num());
        jsonObject.put("address",tpRegion.getName());
        return jsonObject;
    }

    @Override
    public PageInfo fansTypeList() {
        Page page = new Page();
        PageHelper.startPage(page.getPageNow(),page.getPageSize());
        List list = tpFansMapper.fansTypeList();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void saveFansList(TpFans tpFans) {
        tpFansMapper.saveFansList(tpFans);
    }

}
