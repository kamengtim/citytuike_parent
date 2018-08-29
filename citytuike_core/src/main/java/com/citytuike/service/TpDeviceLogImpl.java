package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpDeviceLogMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.TpDeviceLog;
import com.citytuike.model.TpUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpDeviceLogImpl implements TpDeviceLogService {
    @Autowired
    private TpDeviceLogMapper deviceLogMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    public JSONObject getTodayDevice(Integer user_id) {
        JSONObject jsonObject = new JSONObject();
        int today_add_device_number = deviceLogMapper.getTodayDevice(user_id);
        double today_add_income = deviceLogMapper.getTodayIncome(user_id);
        List<TpUsers> users =  tpUsersMapper.selectParentId(user_id);
        for (TpUsers user : users) {
        int today_add_team_number = deviceLogMapper.getCountMan(user.getUser_id());
        jsonObject.put("today_add_team_number",today_add_team_number);
        }
        int today_add_ad_number = 0;
        jsonObject.put("today_add_device_number",today_add_device_number);
        jsonObject.put("today_add_income",today_add_income);
        jsonObject.put("today_add_ad_number",today_add_ad_number);
        return jsonObject;
    }
}
