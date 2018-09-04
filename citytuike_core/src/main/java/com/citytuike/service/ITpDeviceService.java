package com.citytuike.service;



import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpDevice;

import java.math.BigDecimal;
import java.util.List;

public interface ITpDeviceService {
    int selectCountDevice(Integer user_id);

    List<TpDevice> selectAll();

    LimitPageList getLimtPageList(Integer user_id, String page);

    JSONObject getDeviceJson(TpDevice tpDevice);

    BigDecimal getSumMoneyDevice(Integer user_id);

    List<TpDevice> getParentId(Integer user_id);

    JSONObject statistics(Integer user_id);

    LimitPageList selectDeviceList(Integer user_id,String page);

    JSONObject getNewDeviceJson(TpDevice tpDevice);

    JSONObject getOnlyDevice(Integer user_id, String device_id, String device_sn);
}
