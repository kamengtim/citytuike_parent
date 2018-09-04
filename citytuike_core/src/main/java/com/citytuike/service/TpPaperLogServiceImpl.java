package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpDeviceMapper;
import com.citytuike.mapper.TpPaperLogMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpGoods;
import com.citytuike.model.TpPaperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TpPaperLogServiceImpl implements TpPaperLogService {
    @Autowired
    private TpPaperLogMapper tpPaperLogMapper;
    @Autowired
    private TpDeviceMapper tpDeviceMapper;
    @Override
    public LimitPageList paperLog(Integer user_id, String page, String device_id, String type) {
        LimitPageList limitPageList = new LimitPageList();
        if(type == null || type.equals("0")){
        int totalCount = tpPaperLogMapper.selectCount(Integer.valueOf(device_id));
        List<TpPaperLog> stuList=new ArrayList<TpPaperLog>();
        Page p=null;
            if(p!=null){
                p=new Page(totalCount, Integer.valueOf(page));
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPage(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }else{
                p=new Page(totalCount, 1);//初始化pageNow为1
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPage(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }
            limitPageList.setPage(p);
            limitPageList.setList(stuList);
        }else if(type.equals("1")){
            int totalCount = tpPaperLogMapper.selectCountToDay(Integer.valueOf(device_id));
            List<TpPaperLog> stuList=new ArrayList<TpPaperLog>();
            Page p=null;
            if(p!=null){
                p=new Page(totalCount, Integer.valueOf(page));
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPageToDay(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }else{
                p=new Page(totalCount, 1);//初始化pageNow为1
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPageToDay(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }
            limitPageList.setPage(p);
            limitPageList.setList(stuList);
        }else if(type.equals("2")){
            int totalCount = tpPaperLogMapper.selectCountToWeek(Integer.valueOf(device_id));
            List<TpPaperLog> stuList=new ArrayList<TpPaperLog>();
            Page p=null;
            if(p!=null){
                p=new Page(totalCount, Integer.valueOf(page));
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPageToWeek(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }else{
                p=new Page(totalCount, 1);//初始化pageNow为1
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPageToWeek(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }
            limitPageList.setPage(p);
            limitPageList.setList(stuList);
        }else if(type.equals("3")){
            int totalCount = tpPaperLogMapper.selectCountToMonth(Integer.valueOf(device_id));
            List<TpPaperLog> stuList=new ArrayList<TpPaperLog>();
            Page p=null;
            if(p!=null){
                p=new Page(totalCount, Integer.valueOf(page));
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPageToMonth(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }else{
                p=new Page(totalCount, 1);//初始化pageNow为1
                p.setPageSize(10);
                stuList=tpPaperLogMapper.selectByPageToMonth(p.getStartPos(), p.getPageSize(),device_id);//从startPos开始，获取pageSize条数据
            }
            limitPageList.setPage(p);
            limitPageList.setList(stuList);
        }else{
            throw new SecurityException("无相关数据");
        }
    return limitPageList;
    }

    @Override
    public JSONObject getJsonLog(TpPaperLog tpPaperLog) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpPaperLog.getId());
        jsonObject.put("device_id",tpPaperLog.getDevice_id());
        jsonObject.put("number",tpPaperLog.getNumber());
        jsonObject.put("type",tpPaperLog.getType());
        jsonObject.put("add_time",tpPaperLog.getAdd_time());
        jsonObject.put("income",tpPaperLog.getIncome());
        jsonObject.put("scene_str",tpPaperLog.getScene_str());
        jsonObject.put("handel_user_id",tpPaperLog.getHandel_user_id());
        return jsonObject;
    }

    @Override
    public void save(String device_sn, String number, Integer user_id) {
        Integer deviceId = tpPaperLogMapper.savePaperLog(device_sn);
        TpPaperLog tpPaperLog = new TpPaperLog();
        tpPaperLog.setDevice_id(deviceId);
        tpPaperLog.setNumber(Integer.valueOf(number));
        tpPaperLog.setType(Byte.valueOf("1"));
        tpPaperLog.setAdd_time((int)(new Date().getTime()/1000));
        tpPaperLog.setHandel_user_id(user_id);
        tpPaperLogMapper.insertPaperLog(tpPaperLog);
        tpDeviceMapper.updateDeviceNumber(number,device_sn);
    }
}
