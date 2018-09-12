package com.citytuike.service;



import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpRegion;

import java.math.BigDecimal;
import java.util.List;

public interface ITpDeviceService {
    int selectCountDevice(Integer user_id);

    List<TpDevice> selectAll();

    LimitPageList getLimtPageList(Integer user_id, String page);

    JSONObject getDeviceJson(TpDevice tpDevice);

    BigDecimal getSumMoneyDevice(Integer user_id);

    List<TpDevice> getParentId(Integer user_id);

    List<TpDevice> findByCity(int id);

    JSONObject statistics(Integer user_id);

    LimitPageList selectDeviceList(Integer user_id,String page);

    JSONObject getNewDeviceJson(TpDevice tpDevice);

    JSONObject getOnlyDevice(Integer user_id, String device_id, String device_sn);

    JSONObject selectDeviceBySn(String deviceSn);

    void getConf(Integer user_id,String device_sn, String province, String city, String district, String landmark_picture);

    List<TpDevice> getHaveDeviceCity();

    TpDevice getUserDevice(String productKey, String deviceName, String lat, String lng);

    TpDevice getDeviceId(BigDecimal lng, BigDecimal lat);

    TpDevice getDevice(String productKey, String deviceName);

    void updateType(TpDevice tpDevice);

    TpDevice getDeviceById(String device_id);

    void updateRunStatus(TpDevice tpDevice);
}
