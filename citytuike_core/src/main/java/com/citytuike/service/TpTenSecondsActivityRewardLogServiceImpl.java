package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpTenSecondsActivityRewardLogMapper;
import com.citytuike.mapper.TpTenSecondsActivityRewardMapper;
import com.citytuike.model.Page;
import com.citytuike.model.TpTenSecondsActivityReward;
import com.citytuike.model.TpTenSecondsActivityRewardLog;

import com.citytuike.model.TpUsers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service@Transactional
public class TpTenSecondsActivityRewardLogServiceImpl implements TpTenSecondsActivityRewardLogService {
    @Autowired
    private TpTenSecondsActivityRewardLogMapper tpTenSecondsActivityRewardLogMapper;
    @Autowired
    private TpTenSecondsActivityRewardMapper tpTenSecondsActivityRewardMapper;
    @Override
    public int checkWeekShare(Integer user_id, Integer shareId) {
        int count = tpTenSecondsActivityRewardLogMapper.checkWeekShare(user_id,shareId);
        return count;
    }

    @Override
    public int getLogCount(Integer user_id, String activity_id) {
        int count = tpTenSecondsActivityRewardLogMapper.getLogCount(user_id,activity_id);
        return count;
    }

    @Override
    public int checkDayShare(Integer user_id) {
        int count = tpTenSecondsActivityRewardLogMapper.checkDayShare(user_id);
        return count;
    }

    @Override
    public int selectCount(String activity_id) {
        int count = tpTenSecondsActivityRewardLogMapper.selectCount(activity_id);
        return count;
    }

    @Override
    public int selectWeekCount(String activity_id, Integer user_id) {
        int count = tpTenSecondsActivityRewardLogMapper.selectWeekCount(activity_id,user_id);
        return count;
    }

    @Override
    public JSONObject insertLog(String rewardGet, String activity_id, TpUsers share_id, TpUsers user_id, String seconds, TpTenSecondsActivityReward tpTenSecondsActivityReward, String nickName) {
        BigDecimal bg = new BigDecimal(seconds);
        double second =bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        JSONObject jsonObject = new JSONObject();
        if (tpTenSecondsActivityReward == null){
            jsonObject.put("status",0);
            jsonObject.put("msg","很抱歉，本次未中奖，请再接再厉~~");
            return jsonObject;
        }
        Date date = new Date();
        TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog = new TpTenSecondsActivityRewardLog();
        tpTenSecondsActivityRewardLog.setActivity_id(Integer.valueOf(activity_id));
        if (rewardGet.equals("0")){
            tpTenSecondsActivityRewardLog.setReward_id(0);
        }else{
            tpTenSecondsActivityRewardLog.setReward_id(tpTenSecondsActivityReward.getId());
        }
        if(share_id != null){
        tpTenSecondsActivityRewardLog.setUser_id(share_id.getUser_id());
        }else{
            tpTenSecondsActivityRewardLog.setUser_id(user_id.getUser_id());
        }
        tpTenSecondsActivityRewardLog.setAdd_time(date);
        tpTenSecondsActivityRewardLog.setSeconds(String.valueOf(second));
        if(share_id != null){
        tpTenSecondsActivityRewardLog.setFrom_user_id(user_id.getUser_id());
        }else{
            tpTenSecondsActivityRewardLog.setFrom_user_id(0);
        }
        String condition = tpTenSecondsActivityReward.getCondition();
        BigDecimal bgd = new BigDecimal(condition);
        double v = bgd.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(String.valueOf(second).equals(v+"") && !rewardGet.equals("0")){
            tpTenSecondsActivityRewardLog.setStatus(1);
            tpTenSecondsActivityRewardLog.setReward_name(tpTenSecondsActivityReward.getAlias()==null ?
            tpTenSecondsActivityReward.getName() : tpTenSecondsActivityReward.getAlias() +":"+ tpTenSecondsActivityReward.getReward());
        }else{
            tpTenSecondsActivityRewardLog.setStatus(-1);
            tpTenSecondsActivityRewardLog.setReward_name("");
        }
        tpTenSecondsActivityRewardLog.setSeconds(String.valueOf(second));
        tpTenSecondsActivityRewardLog.setLogistics_name("");
        tpTenSecondsActivityRewardLog.setLogistics_number("");
        tpTenSecondsActivityRewardLog.setConsignee("");
        tpTenSecondsActivityRewardLog.setProvince("");
        tpTenSecondsActivityRewardLog.setCity("");
        tpTenSecondsActivityRewardLog.setDistrict("");
        tpTenSecondsActivityRewardLog.setTwon("");
        tpTenSecondsActivityRewardLog.setAddress("");
        tpTenSecondsActivityRewardLog.setMobile("");
        int i = tpTenSecondsActivityRewardLogMapper.insert(tpTenSecondsActivityRewardLog);
        if(rewardGet.equals("0")){
            jsonObject.put("status",0);
            jsonObject.put("msg","很抱歉，本次未中奖，请再接再厉~~");
            return jsonObject;
        }
        if(tpTenSecondsActivityReward.getNumber()>0 && !rewardGet.equals("0") && i>0){
            if(tpTenSecondsActivityRewardLog.getStatus()==1){
                tpTenSecondsActivityReward.setId(tpTenSecondsActivityReward.getId());
                int a = tpTenSecondsActivityRewardMapper.updateByPrimaryKey(tpTenSecondsActivityReward.getId());
                if(a>0){
                    tpTenSecondsActivityReward.setId(tpTenSecondsActivityReward.getId());
                    int b = tpTenSecondsActivityRewardMapper.update(tpTenSecondsActivityReward.getId());
                    if(b>0){
                        jsonObject.put("status","1");
                        jsonObject.put("msg",nickName+"获得"+tpTenSecondsActivityReward.getReward());
                    }
                }
            }

        }else{
            jsonObject.put("status",0);
            jsonObject.put("msg","您下手慢了一步，该奖励已被领取完~");
        }
            return jsonObject;
    }

    @Override
    public PageInfo reward_list() {
        Page page = new Page();
        PageHelper.startPage(page.getPageNow(),page.getPageSize());
        List list = tpTenSecondsActivityRewardLogMapper.fansTypeList();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public JSONObject getJson(TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog) {
        JSONObject  jsonObject = new JSONObject();
        jsonObject.put("reward_id",tpTenSecondsActivityRewardLog.getReward_id());
        jsonObject.put("add_time",tpTenSecondsActivityRewardLog.getAdd_time());
        jsonObject.put("reward_name",tpTenSecondsActivityRewardLog.getReward_name());
        jsonObject.put("status",tpTenSecondsActivityRewardLog.getStatus());
        jsonObject.put("status_str",tpTenSecondsActivityRewardLog.getStatus() == 1? "已領取" : "未領取");
        return jsonObject;
    }

    @Override
    public TpTenSecondsActivityRewardLog getLogById(String reward_id,Integer user_id) {
        TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog = tpTenSecondsActivityRewardLogMapper.getLogById(reward_id,user_id);
        return tpTenSecondsActivityRewardLog;
    }

    @Override
    public int update(TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog) {
        int  i = tpTenSecondsActivityRewardLogMapper.updateByPrimaryKey(tpTenSecondsActivityRewardLog);
        return i;
    }

    @Override
    public TpTenSecondsActivityRewardLog getReward(String log_id) {
        TpTenSecondsActivityRewardLog tpTenSecondsActivityRewardLog = tpTenSecondsActivityRewardLogMapper.getReward(log_id);
        return tpTenSecondsActivityRewardLog;
    }

}
