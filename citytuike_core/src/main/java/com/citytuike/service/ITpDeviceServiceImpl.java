package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpDeviceMapper;
import com.citytuike.mapper.TpDevicePlayMapper;
import com.citytuike.mapper.TpRegionMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpDevicePlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ITpDeviceServiceImpl implements ITpDeviceService {
    @Autowired
    private TpDeviceMapper tpDeviceMapper;
    @Autowired
    private TpRegionMapper tpRegionMapper;
    @Autowired
    private TpDevicePlayMapper tpDevicePlayMapper;

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
        data.put("device_sn", tpDevice.getDeviceSn());
        data.put("ad_position_id", tpDevice.getAdPositionId());
        data.put("qrscene", tpDevice.getQrscene());
        data.put("iot_id", tpDevice.getIotId());
        data.put("product_key", tpDevice.getProductKey());
        data.put("device_name", tpDevice.getDeviceName());
        data.put("device_secret", tpDevice.getDeviceSecret());
        data.put("add_time", tpDevice.getAddTime());
        data.put("user_id", tpDevice.getUserId());
        data.put("paper_number", tpDevice.getPaperNumber());
        data.put("paper_send", tpDevice.getPaperSend());
        data.put("paper_inventory", tpDevice.getPaperInventory());
        data.put("income", tpDevice.getIncome());
        data.put("one_income", tpDevice.getOneIncome());
        data.put("capacity", tpDevice.getCapacity());
        data.put("active_time", tpDevice.getActiveTime());
        data.put("no_send", tpDevice.getNoSend());
        data.put("open_status", tpDevice.getOpenStatus());
        data.put("online", tpDevice.getOnline());
        data.put("landmark_picture", tpDevice.getLandmarkPicture());
        data.put("province", tpDevice.getProvince());
        data.put("city", tpDevice.getCity());
        data.put("district", tpDevice.getDistrict());
        data.put("is_active", tpDevice.getIsActive());
        data.put("order_id", tpDevice.getOrderId());
        data.put("goods_id", tpDevice.getGoodsId());
        data.put("distribution_time", tpDevice.getDistributionTime());
        data.put("address", tpDevice.getAddress());
        data.put("longitude", tpDevice.getLongitude());
        data.put("latitude", tpDevice.getLatitude());
        data.put("active_code", tpDevice.getActiveCode());
        data.put("shipping_sn", tpDevice.getShippingSn());
        data.put("shipping_name", tpDevice.getShippingName());
        data.put("today_num", tpDevice.getTodayNum());
        data.put("today_time", tpDevice.getTodayTime());
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
    public List<TpDevice> findByCity(int id) {
        return tpDeviceMapper.findByCity(id);
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
            int paper_number = tpDevice.getPaperNumber();
            PaperNumber = PaperNumber + paper_number;
            int paper_send = tpDevice.getPaperSend();
            PaperSend = PaperSend +paper_send;
            int paper_inventory = tpDevice.getPaperInventory();
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
        data.put("device_sn", tpDevice.getDeviceSn());
        data.put("ad_position_id", tpDevice.getAdPositionId());
        data.put("qrscene", tpDevice.getQrscene());
        data.put("iot_id", tpDevice.getIotId());
        data.put("product_key", tpDevice.getProductKey());
        data.put("device_name", tpDevice.getDeviceName());
        data.put("device_secret", tpDevice.getDeviceSecret());
        data.put("add_time", tpDevice.getAddTime());
        data.put("user_id", tpDevice.getUserId());
        data.put("paper_number", tpDevice.getPaperNumber());
        data.put("paper_send", tpDevice.getPaperSend());
        data.put("paper_inventory", tpDevice.getPaperInventory());
        data.put("income", tpDevice.getIncome());
        data.put("one_income", tpDevice.getOneIncome());
        data.put("capacity", tpDevice.getCapacity());
        data.put("active_time", tpDevice.getActiveTime());
        data.put("no_send", tpDevice.getNoSend());
        data.put("open_status", tpDevice.getOpenStatus());
        data.put("online", tpDevice.getOnline());
        data.put("landmark_picture", tpDevice.getLandmarkPicture());
        data.put("province", tpDevice.getProvince());
        data.put("city", tpDevice.getCity());
        data.put("district", tpDevice.getDistrict());
        data.put("is_active", tpDevice.getIsActive());
        data.put("order_id", tpDevice.getOrderId());
        data.put("goods_id", tpDevice.getGoodsId());
        data.put("distribution_time", tpDevice.getDistributionTime());
        data.put("address", tpDevice.getAddress());
        data.put("longitude", tpDevice.getLongitude());
        data.put("latitude", tpDevice.getLatitude());
        data.put("active_code", tpDevice.getActiveCode());
        data.put("shipping_sn", tpDevice.getShippingSn());
        data.put("shipping_name", tpDevice.getShippingName());
        data.put("today_num", tpDevice.getTodayNum());
        data.put("today_time", tpDevice.getTodayTime());
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
        data.put("device_sn", tpDevice.getDeviceSn());
        data.put("ad_position_id", tpDevice.getAdPositionId());
        data.put("qrscene", tpDevice.getQrscene());
        data.put("iot_id", tpDevice.getIotId());
        data.put("product_key", tpDevice.getProductKey());
        data.put("device_name", tpDevice.getDeviceName());
        data.put("device_secret", tpDevice.getDeviceSecret());
        data.put("add_time", tpDevice.getAddTime());
        data.put("user_id", tpDevice.getUserId());
        data.put("paper_number", tpDevice.getPaperNumber());
        data.put("paper_send", tpDevice.getPaperSend());
        data.put("paper_inventory", tpDevice.getPaperInventory());
        data.put("income", tpDevice.getIncome());
        data.put("one_income", tpDevice.getOneIncome());
        data.put("capacity", tpDevice.getCapacity());
        data.put("active_time", tpDevice.getActiveTime());
        data.put("no_send", tpDevice.getNoSend());
        data.put("open_status", tpDevice.getOpenStatus());
        data.put("online", tpDevice.getOnline());
        data.put("landmark_picture", tpDevice.getLandmarkPicture());
        data.put("province", tpDevice.getProvince());
        data.put("city", tpDevice.getCity());
        data.put("district", tpDevice.getDistrict());
        data.put("is_active", tpDevice.getIsActive());
        data.put("order_id", tpDevice.getOrderId());
        data.put("goods_id", tpDevice.getGoodsId());
        data.put("distribution_time", tpDevice.getDistributionTime());
        data.put("address", tpDevice.getAddress());
        data.put("longitude", tpDevice.getLongitude());
        data.put("latitude", tpDevice.getLatitude());
        data.put("active_code", tpDevice.getActiveCode());
        data.put("shipping_sn", tpDevice.getShippingSn());
        data.put("shipping_name", tpDevice.getShippingName());
        data.put("today_num", tpDevice.getTodayNum());
        data.put("today_time", tpDevice.getTodayTime());
        return data;
    }

    @Override
    public JSONObject selectDeviceBySn(String deviceSn) {
        TpDevice tpDevice = tpDeviceMapper.selectDeviceBySn(deviceSn);
        JSONObject deviceJson = getDeviceJson(tpDevice);
        return deviceJson;
    }

    @Override
    public List<TpDevice> getHaveDeviceCity() {
        List<TpDevice>tpDevices = tpDeviceMapper.getHaveDeviceCity();
        return tpDevices;
    }

    @Override
    public TpDevice getUserDevice(String productKey, String deviceName, String lat, String lng) {
        TpDevice tpDevice = tpDeviceMapper.getUserDevice(productKey,deviceName);
        return tpDevice;
    }

    @Override
    public TpDevice getDeviceId(BigDecimal lng, BigDecimal lat) {
        TpDevice tpDevice = tpDeviceMapper.getDeviceId(lng,lat);
        return tpDevice;
    }

    @Override
    public TpDevice getDevice(String productKey, String deviceName) {
        TpDevice userDevice = tpDeviceMapper.getUserDevice(productKey, deviceName);
        return userDevice;
    }

    @Override
    public void updateType(TpDevice tpDevice) {
        tpDeviceMapper.updateType(tpDevice);
    }

    @Override
    public TpDevice getDeviceById(String device_id) {
        TpDevice tpDevice = tpDeviceMapper.getDeviceById(device_id);
        return tpDevice;
    }

    @Override
    public void updateRunStatus(TpDevice tpDevice) {
        tpDeviceMapper.updateRunStatus(tpDevice);
    }

    @Override
    public void updateVersion(String get_version, String imei) {
        tpDeviceMapper.updateVersion(get_version,imei);
    }

    @Override
    public TpDevice selectDevice(Integer user_id, String device_sn) {
        TpDevice tpDevice = tpDeviceMapper.selectDevice(user_id,device_sn);
        return tpDevice;
    }

    @Override
    public int update(Integer id, String province, String city, String district, String landmark_picture) {
        int date = (int)(new Date().getTime()/1000);
        int i= tpDeviceMapper.updateDevice(id,province,city,district,date,landmark_picture);
        return i;
    }

    @Override
    public TpDevice selectPaper(String device_sn, Integer user_id) {
        TpDevice tpDevice = tpDeviceMapper.selectPaper(device_sn,user_id);
        return tpDevice;
    }

    @Override
    public TpDevice findByDevicesnAndIsactive(String imei, int is_active) {
        return tpDeviceMapper.findByDevicesnAndIsactive(imei, is_active);
    }

    @Override
    public TpDevice findDeviceBySn(String deviceSn) {
        return tpDeviceMapper.selectDeviceBySn(deviceSn);
    }

    @Override
    public TpDevicePlay findDevicePlayByDeviceId(Integer device_id) {
        return tpDevicePlayMapper.findDevicePlayByDeviceId(device_id);
    }

    @Override
    public int updataDeviceByRegionId(Integer id, Integer district) {
        return tpDevicePlayMapper.updataDeviceByRegionId(id, district);
    }

}
