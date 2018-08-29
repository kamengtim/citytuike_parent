package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpWithdrawalsMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpDevice;
import com.citytuike.model.TpWithdrawals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TpWithdrawalsServiceImpl implements TpWithdrawalsService {
    @Autowired
    private TpWithdrawalsMapper tpWithdrawalsMapper;
    public LimitPageList getWithdrawalsList(Integer user_id,String page) {
        LimitPageList limitPageList = new LimitPageList();
        int count = tpWithdrawalsMapper.CountWithdrawals(user_id);
        List<TpWithdrawals> stuList = new ArrayList<TpWithdrawals>();
        Page PageSize = null;
        if(page != null){
            PageSize=new Page(count,Integer.valueOf(page));
            PageSize.setPageSize(10);
            stuList = tpWithdrawalsMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
        }else{
            PageSize = new Page(count,1);
            PageSize.setPageSize(10);
            stuList = tpWithdrawalsMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),user_id);
        }
        limitPageList.setPage(PageSize);
        limitPageList.setList(stuList);
        return limitPageList;
    }

    public JSONObject JsonWithdrawals(TpWithdrawals tpWithdrawal) {
        JSONObject  jsonObject = new JSONObject();
        jsonObject.put("bank_card",tpWithdrawal.getBank_card());
        jsonObject.put("bank_name",tpWithdrawal.getBank_name());
        jsonObject.put("check_time",tpWithdrawal.getCheck_time());
        jsonObject.put("create_time",tpWithdrawal.getCreate_time());
        jsonObject.put("error_code",tpWithdrawal.getError_code());
        jsonObject.put("id",tpWithdrawal.getId());
        jsonObject.put("is_paid",tpWithdrawal.getIs_paid());
        jsonObject.put("money",tpWithdrawal.getMoney());
        jsonObject.put("order_sn",tpWithdrawal.getOrder_sn());
        jsonObject.put("pay_code",tpWithdrawal.getPay_code());
        jsonObject.put("pay_time",tpWithdrawal.getPay_time());
        jsonObject.put("query_time",tpWithdrawal.getQuery_time());
        jsonObject.put("realname",tpWithdrawal.getRealname());
        jsonObject.put("refuse_time",tpWithdrawal.getRefuse_time());
        jsonObject.put("remark",tpWithdrawal.getRemark());
        jsonObject.put("send_type",tpWithdrawal.getSend_type());
        jsonObject.put("serial_number",tpWithdrawal.getSerial_number());
        jsonObject.put("status",tpWithdrawal.getStatus());
        jsonObject.put("taxfee",tpWithdrawal.getTaxfee());
        jsonObject.put("user_id",tpWithdrawal.getUser_id());
        return jsonObject;
    }
}
