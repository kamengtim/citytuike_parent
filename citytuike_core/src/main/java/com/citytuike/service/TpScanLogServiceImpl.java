package com.citytuike.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.iot.model.v20170420.PubResponse;
import com.citytuike.mapper.TpDeviceLogMapper;
import com.citytuike.mapper.TpDeviceMapper;
import com.citytuike.mapper.TpScanLogMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpDeviceLog;
import com.citytuike.model.TpScanLog;
import com.citytuike.model.TpUsers;
import com.citytuike.util.AliyunIotApi;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class TpScanLogServiceImpl implements TpScanLogService {
    @Autowired
    private TpScanLogMapper tpScanLogMapper;
    @Autowired
    private TpDeviceMapper tpDeviceMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Autowired
    private TpDeviceLogMapper deviceLogMapper;
    @Override
    public List<TpScanLog> findAlltpScanLogService() {
        List<TpScanLog> tpScanLogs = tpScanLogMapper.selectAll();
        return tpScanLogs;
    }

    @Override
    public TpScanLog getStutas(Integer id) {
        TpScanLog tpScanLog = tpScanLogMapper.getStutas(id);
        return tpScanLog;
    }

    @Override
    public void update(Integer id) {
        tpScanLogMapper.update(id);
    }

    @Override
    public JSONObject sendPaperWx(Integer id, Integer device_id, String paper_token, boolean is_test) {
        TpDevice tpDevice = tpDeviceMapper.getDevice(device_id);
        JSONObject jsonObject = check_paper_number_allowance(tpDevice.getUser_id());
        if(jsonObject.get("status").equals("0")){
            return jsonObject;
        }

        AliyunIotApi aliyunIotApi = new AliyunIotApi();
        PubResponse response = aliyunIotApi.put(tpDevice.getProduct_key(), tpDevice.getDevice_name(), paper_token);
        if(tpDevice.getUser_id() == 2951 || tpDevice.getRun_status() == 1 || is_test){
             jsonObject.put("status","1");
             jsonObject.put("msg","ok");
        }
        TpDeviceLog tpDeviceLog = new TpDeviceLog();
        tpDeviceLog.setAction("get");
        tpDeviceLog.setResponse(String.valueOf(response));
        tpDeviceLog.setAdd_time((int)(new Date().getTime()/1000));
        tpDeviceLog.setMessage_id(response.getMessageId());
        tpDeviceLog.setRequest_id(response.getRequestId());
        tpDeviceLog.setStatus(response.getSuccess());
        deviceLogMapper.insert(tpDeviceLog);

        if(response.getSuccess() == false){
            jsonObject.put("status","0");
            jsonObject.put("msg","发纸失败");
            return jsonObject;
        }
        TpUsers tpUsers = tpUsersMapper.getInviteCode(tpDevice.getUser_id());
        tpUsersMapper.update(tpUsers);
        TpDevice device = tpDeviceMapper.getDevice(tpDevice.getId());
        tpDeviceMapper.update(device);
        //该设备每日发纸统计
        int toDayCount = tpDeviceMapper.toDayCountPaper(tpDevice.getId());
        //统计当天所有设备出纸数
        int allToDayPaper = tpDeviceMapper.allToDayPaper();
        //以下json数据存到redis中
        JSONObject jsonObi = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObi.put("user_id",tpUsers.getUser_id());
        jsonObi.put("device_user_id",tpDevice.getUser_id());
        jsonObi.put("device_id",tpDevice.getId());
        jsonObi.put("scene_str",paper_token);
        jsonObi.put("time",(int)(new Date().getTime()/1000));
        jsonObi.put("number",1);
        jsonArray.add(jsonObi);
        //将jsonObj存到Redis中
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis jedis = pool.getResource();
        jedis.set("d_send_paper_list", String.valueOf(jsonArray));
        return jsonObject;

    }
    private JSONObject check_paper_number_allowance(Integer user_id) {
        JSONObject jsonObject = new JSONObject();
        int paperNumber = tpUsersMapper.getPaperNum(user_id);
        if(paperNumber <= 0){
            jsonObject.put("status",0);
            jsonObject.put("msg","机主纸巾不足，如已购买，请确认收货后再试");
        }else{
            jsonObject.put("status",1);
            jsonObject.put("msg","ok!");

        }
        return jsonObject;
    }


}
