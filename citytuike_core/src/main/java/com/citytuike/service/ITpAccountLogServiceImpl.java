package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpAccountLogMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpAccountLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ITpAccountLogServiceImpl implements ITpAccountLogService {
    @Autowired
    private TpAccountLogMapper accountLogMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    public JSONObject UserMoney(Integer user_id) {
        TpAccountLog accountLog = accountLogMapper.selectUserMoney(user_id);
        JSONObject jsonObj = new JSONObject();
        double UserMoney = accountLog.getUser_money();
        double FrozenMoney = accountLog.getFrozen_money();
        double balance = UserMoney + FrozenMoney;
        jsonObj.put("balance",balance);
        return jsonObj;
    }

    public LimitPageList getDetail(Integer user_id, String type,String page) {
        LimitPageList  limitPageList = new LimitPageList();
        int acount = accountLogMapper.accountDetail(user_id,type);
        List<TpAccountLog> stuList = new ArrayList<TpAccountLog>();
        Page PageSize = null;
        if(page != null ){
            PageSize=new Page(acount,Integer.valueOf(page));
            PageSize.setPageSize(10);
            stuList = accountLogMapper.selectUserMoneyDetail(PageSize.getStartPos(),PageSize.getPageSize(),user_id,type);
        }else{
            PageSize = new Page(acount,1);
            PageSize.setPageSize(10);
            stuList = accountLogMapper.selectUserMoneyDetail(PageSize.getStartPos(),PageSize.getPageSize(),user_id,type);
        }
        limitPageList.setPage(PageSize);
        limitPageList.setList(stuList);
        return limitPageList;
    }

    public JSONObject getJsonToAccount(TpAccountLog tpAccountLog) {
        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("log_id",tpAccountLog.getLog_id());
        String dataFormat = transferLongToDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(tpAccountLog.getChange_time()));
        System.out.println(tpAccountLog.getChange_time());
        jsonObject.put("change_time",dataFormat);
        jsonObject.put("change_type",tpAccountLog.getChange_type());
        jsonObject.put("desc",tpAccountLog.getDesc());
        jsonObject.put("frozen_money",tpAccountLog.getFrozen_money());
        jsonObject.put("is_delete",tpAccountLog.getIs_delete());
        jsonObject.put("order_id",tpAccountLog.getOrder_id());
        jsonObject.put("order_sn",tpAccountLog.getOrder_sn());
        jsonObject.put("pay_points",tpAccountLog.getPay_points());
        jsonObject.put("second_type",tpAccountLog.getSecond_type());
        jsonObject.put("status",tpAccountLog.getStatus());
        jsonObject.put("third_type",tpAccountLog.getThird_type());
        jsonObject.put("user_id",tpAccountLog.getUser_id());
        jsonObject.put("user_money",tpAccountLog.getUser_money());
        String dataFormat1 = transferLongToDate("MM月dd日", Long.valueOf(tpAccountLog.getChange_time()));
        jsonObject.put("change_date",dataFormat1);
        return jsonObject;
    }

    public double SumMoney(Integer user_id) {
        return accountLogMapper.SumMoney(user_id);
    }

    private String transferLongToDate(String dateFormat,Long millSec){
        String result = null;
        Date date = new Date(millSec*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        result =  sdf.format(date);
        return result;

    }

    public int insertAccountLog(TpAccountLog tpAccountLog) {
        return accountLogMapper.insert(tpAccountLog);
    }
}
