package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.exception.SendMessageException;
import com.citytuike.mapper.TpBankMapper;
import com.citytuike.mapper.TpUserBankMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.mapper.TpWithdrawalsMapper;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TpWithdrawalsServiceImpl implements TpWithdrawalsService {
    @Autowired
    private TpWithdrawalsMapper tpWithdrawalsMapper;
    @Autowired
    private TpBankMapper tpBankMapper;
    @Autowired
    private TpUserBankMapper tpUserBankMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
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

    @Override
    public BigDecimal selectWithdrawalsMoney(Integer user_id) {
        return tpWithdrawalsMapper.selectWithdrawalsMoney(user_id);
    }

    @Override
    public void ApplyForWithdrawals(Integer user_id, int id, float money) {
        BigDecimal userMoney = tpUsersMapper.selectCountMoney(user_id);
        TpWithdrawals tpWithdrawals = new TpWithdrawals();
        if(money >= 15 && userMoney.intValue() >= 15){
        tpWithdrawals.setUser_id(user_id);
        tpWithdrawals.setMoney(money);
        tpWithdrawals.setCreate_time((int)(new Date().getTime()/1000));
        String bankName = tpBankMapper.selectBank(id);
        tpWithdrawals.setBank_name(bankName);
        TpUserBank tpUserBank = tpUserBankMapper.selectBankCard(id,user_id);
        tpWithdrawals.setRealname(tpUserBank.getReal_name());
        tpWithdrawals.setBank_card(tpUserBank.getBank_card());
        tpWithdrawals.setStatus(0);
        tpWithdrawals.setSend_type(0);
        tpWithdrawals.setTaxfee(5);
        tpWithdrawals.setIs_paid(0);
        tpWithdrawals.setQuery_time(0);
        tpWithdrawalsMapper.saveWithdrawals(tpWithdrawals);
        BigDecimal newUserMoney = userMoney.subtract((BigDecimal.valueOf(money)).multiply(new BigDecimal(1).subtract(new BigDecimal(0.05))));
        tpUsersMapper.updateUserMoney(user_id,newUserMoney);
        }else{
            throw new SendMessageException("最低提现金额:15");
        }
    }

    @Override
    public int insertWithdrawals(TpWithdrawals tpWithdrawals) {
        return tpWithdrawalsMapper.insert(tpWithdrawals);
    }

}
