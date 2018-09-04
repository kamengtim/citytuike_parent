package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpDeviceMapper;
import com.citytuike.mapper.TpRegionMapper;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service@Transactional
public class ITpDeviceServiceImpl implements ITpDeviceService {
    @Autowired
    private TpDeviceMapper tpDeviceMapper;
    @Autowired
    private TpRegionMapper tpRegionMapper;

    public int selectCountDevice(Integer user_id) {
        return tpDeviceMapper.selectCountDevice(user_id);
    }

    public List<TpDevice> selectAll() {
        return tpDeviceMapper.selectAll();
    }

    public LimitPageList getLimtPageList(Integer user_id, String page) {
        LimitPageList limitPageList = new LimitPageList();
        int totalCount = tpDeviceMapper.selectCountDevice(user_id);
        List<TpDevice> stuList = new ArrayList<TpDevice>();
        Page PageSize = null;
        if(page != null){
            PageSize=new Page(totalCount,Integer.valueOf(page));
            PageSize.setPageSize(10);
            stuList = tpDeviceMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
        }else{
            PageSize = new Page(totalCount,1);
            PageSize.setPageSize(10);
            stuList = tpDeviceMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
        }
        limitPageList.setPage(PageSize);
        limitPageList.setList(stuList);
        return limitPageList;
    }

    public JSONObject getDeviceJson(TpDevice tpDevice) {
        JSONObject data = new JSONObject();
        data.put("id", tpDevice.getId());
        data.put("device_sn", tpDevice.getDevice_sn());
        data.put("ad_position_id", tpDevice.getAd_position_id());
        data.put("qrscene", tpDevice.getQrscene());
        data.put("iot_id", tpDevice.getIot_id());
        data.put("product_key", tpDevice.getProduct_key());
        data.put("device_name", tpDevice.getDevice_name());
        data.put("device_secret", tpDevice.getDevice_secret());
        data.put("add_time", tpDevice.getAdd_time());
        data.put("user_id", tpDevice.getUser_id());
        data.put("paper_number", tpDevice.getPaper_number());
        data.put("paper_send", tpDevice.getPaper_send());
        data.put("paper_inventory", tpDevice.getPaper_inventory());
        data.put("income", tpDevice.getIncome());
        data.put("one_income", tpDevice.getOne_income());
        data.put("capacity", tpDevice.getCapacity());
        data.put("active_time", tpDevice.getActive_time());
        data.put("no_send", tpDevice.getNo_send());
        data.put("open_status", tpDevice.getOpen_status());
        data.put("online", tpDevice.getOnline());
        data.put("landmark_picture", tpDevice.getLandmark_picture());
        data.put("province", tpDevice.getProvince());
        data.put("city", tpDevice.getCity());
        data.put("district", tpDevice.getDistrict());
        data.put("is_active", tpDevice.getIs_active());
        data.put("order_id", tpDevice.getOrder_id());
        data.put("goods_id", tpDevice.getGoods_id());
        data.put("distribution_time", tpDevice.getDistribution_time());
        data.put("address", tpDevice.getAddress());
        data.put("longitude", tpDevice.getLongitude());
        data.put("latitude", tpDevice.getLatitude());
        data.put("active_code", tpDevice.getActive_code());
        data.put("shipping_sn", tpDevice.getShipping_sn());
        data.put("shipping_name", tpDevice.getShipping_name());
        data.put("today_num", tpDevice.getToday_num());
        data.put("today_time", tpDevice.getToday_time());
        return data;
    }

    public BigDecimal getSumMoneyDevice(Integer user_id) {
        double SumMoney = 0;
        List<TpDevice> tpDevices = tpDeviceMapper.selectIncome(user_id);
        for (TpDevice tpDevice : tpDevices) {
           double income = tpDevice.getIncome().doubleValue();
            SumMoney = SumMoney + income;
        }
        return new BigDecimal(SumMoney);
    }

    public List<TpDevice> getParentId(Integer user_id) {
       return tpDeviceMapper.selectParent(user_id);

    }

    @Override
    public JSONObject statistics(Integer user_id) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
         List<TpDevice>tpDevices =  tpDeviceMapper.statistics(user_id);
        double SumMoney = 0;
        int PaperNumber = 0;
        int PaperSend = 0;
        int PaperInventory = 0;
        for (TpDevice tpDevice : tpDevices) {
            double income = tpDevice.getIncome().doubleValue();
            SumMoney = SumMoney + income;
            int paper_number = tpDevice.getPaper_number();
            PaperNumber = PaperNumber + paper_number;
            int paper_send = tpDevice.getPaper_send();
            PaperSend = PaperSend +paper_send;
            int paper_inventory = tpDevice.getPaper_inventory();
            PaperInventory = PaperInventory + paper_inventory;
            jsonObject.put("paper_number",PaperNumber);
            jsonObject.put("paper_send",PaperSend);
            jsonObject.put("paper_inventory",PaperInventory);
            jsonObject.put("income",SumMoney);
        }
        data.put("status", "1");
        data.put("msg", "ok!");
        data.put("result",jsonObject);
        return data;
    }

    @Override
    public LimitPageList selectDeviceList(Integer user_id,String page) {
        LimitPageList limitPageList = new LimitPageList();
        int totalCount = tpDeviceMapper.selectCountDevice(user_id);
        List<TpDevice> stuList = new ArrayList<TpDevice>();
        Page PageSize = null;
        if(page != null){
            PageSize=new Page(totalCount,Integer.valueOf(page));
            PageSize.setPageSize(10);
            stuList = tpDeviceMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
        }else{
            PageSize = new Page(totalCount,1);
            PageSize.setPageSize(10);
            stuList = tpDeviceMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
        }
        limitPageList.setPage(PageSize);
        limitPageList.setList(stuList);
        return limitPageList;
    }

    @Override
    public JSONObject getNewDeviceJson(TpDevice tpDevice) {
        JSONObject data = new JSONObject();
        String provinceName = tpRegionMapper.getAddressProvince(tpDevice.getProvince());
        String city_name = tpRegionMapper.getAddressCity(tpDevice.getCity());
        String district_name = tpRegionMapper.getAddressDistrict(tpDevice.getDistrict());
        data.put("id", tpDevice.getId());
        data.put("device_sn", tpDevice.getDevice_sn());
        data.put("ad_position_id", tpDevice.getAd_position_id());
        data.put("qrscene", tpDevice.getQrscene());
        data.put("iot_id", tpDevice.getIot_id());
        data.put("product_key", tpDevice.getProduct_key());
        data.put("device_name", tpDevice.getDevice_name());
        data.put("device_secret", tpDevice.getDevice_secret());
        data.put("add_time", tpDevice.getAdd_time());
        data.put("user_id", tpDevice.getUser_id());
        data.put("paper_number", tpDevice.getPaper_number());
        data.put("paper_send", tpDevice.getPaper_send());
        data.put("paper_inventory", tpDevice.getPaper_inventory());
        data.put("income", tpDevice.getIncome());
        data.put("one_income", tpDevice.getOne_income());
        data.put("capacity", tpDevice.getCapacity());
        data.put("active_time", tpDevice.getActive_time());
        data.put("no_send", tpDevice.getNo_send());
        data.put("open_status", tpDevice.getOpen_status());
        data.put("online", tpDevice.getOnline());
        data.put("landmark_picture", tpDevice.getLandmark_picture());
        data.put("province", tpDevice.getProvince());
        data.put("city", tpDevice.getCity());
        data.put("district", tpDevice.getDistrict());
        data.put("is_active", tpDevice.getIs_active());
        data.put("order_id", tpDevice.getOrder_id());
        data.put("goods_id", tpDevice.getGoods_id());
        data.put("distribution_time", tpDevice.getDistribution_time());
        data.put("address", tpDevice.getAddress());
        data.put("longitude", tpDevice.getLongitude());
        data.put("latitude", tpDevice.getLatitude());
        data.put("active_code", tpDevice.getActive_code());
        data.put("shipping_sn", tpDevice.getShipping_sn());
        data.put("shipping_name", tpDevice.getShipping_name());
        data.put("today_num", tpDevice.getToday_num());
        data.put("today_time", tpDevice.getToday_time());
        data.put("province_name",provinceName);
        data.put("city_name",city_name);
        data.put("district_name",district_name);
        return data;
    }

    @Override
    public JSONObject getOnlyDevice(Integer user_id, String device_id, String device_sn) {
        JSONObject data = new JSONObject();
        TpDevice tpDevice = tpDeviceMapper.getOnlyDevice(user_id,device_id,device_sn);
        data.put("id", tpDevice.getId());
        data.put("device_sn", tpDevice.getDevice_sn());
        data.put("ad_position_id", tpDevice.getAd_position_id());
        data.put("qrscene", tpDevice.getQrscene());
        data.put("iot_id", tpDevice.getIot_id());
        data.put("product_key", tpDevice.getProduct_key());
        data.put("device_name", tpDevice.getDevice_name());
        data.put("device_secret", tpDevice.getDevice_secret());
        data.put("add_time", tpDevice.getAdd_time());
        data.put("user_id", tpDevice.getUser_id());
        data.put("paper_number", tpDevice.getPaper_number());
        data.put("paper_send", tpDevice.getPaper_send());
        data.put("paper_inventory", tpDevice.getPaper_inventory());
        data.put("income", tpDevice.getIncome());
        data.put("one_income", tpDevice.getOne_income());
        data.put("capacity", tpDevice.getCapacity());
        data.put("active_time", tpDevice.getActive_time());
        data.put("no_send", tpDevice.getNo_send());
        data.put("open_status", tpDevice.getOpen_status());
        data.put("online", tpDevice.getOnline());
        data.put("landmark_picture", tpDevice.getLandmark_picture());
        data.put("province", tpDevice.getProvince());
        data.put("city", tpDevice.getCity());
        data.put("district", tpDevice.getDistrict());
        data.put("is_active", tpDevice.getIs_active());
        data.put("order_id", tpDevice.getOrder_id());
        data.put("goods_id", tpDevice.getGoods_id());
        data.put("distribution_time", tpDevice.getDistribution_time());
        data.put("address", tpDevice.getAddress());
        data.put("longitude", tpDevice.getLongitude());
        data.put("latitude", tpDevice.getLatitude());
        data.put("active_code", tpDevice.getActive_code());
        data.put("shipping_sn", tpDevice.getShipping_sn());
        data.put("shipping_name", tpDevice.getShipping_name());
        data.put("today_num", tpDevice.getToday_num());
        data.put("today_time", tpDevice.getToday_time());
        return data;
    }

}
