package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.exception.SendMessageException;
import com.citytuike.mapper.*;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TpUserAliAccountServiceImpl implements TpUserAliAccountService {
    @Autowired
    private TpUserAliAccountMapper tpUserAliAccountMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Autowired
    private TpWithdrawalsMapper tpWithdrawalsMapper;
    @Autowired
    private TpBankMapper tpBankMapper;
    @Autowired
    private TpUserBankMapper tpUserBankMapper;
    @Override
    public void addAliAccount(String real_name, String mobile, String account) {
        TpUserAliAccount tpUserAliAccount = new TpUserAliAccount();
        tpUserAliAccount.setReal_name(real_name);
        tpUserAliAccount.setMobile(mobile);
        tpUserAliAccount.setAccount(account);
        tpUserAliAccount.setAdd_time((int)(new Date().getTime()/1000));
        TpUsers tpUsers = tpUsersMapper.selectUserByMobile(mobile);
        tpUserAliAccount.setUser_id(tpUsers.getUser_id());
        tpUserAliAccount.setIs_delete(Byte.valueOf("0"));
        tpUserAliAccountMapper.save(tpUserAliAccount);
    }

    @Override
    public LimitPageList AliAccount(String page) {
        //List<TpUserAliAccount>tpUserAliAccounts = tpUserAliAccountMapper.selectAliAccount();
        //return tpUserAliAccounts;
        LimitPageList  limitPageList = new LimitPageList();
        int acount = tpUserAliAccountMapper.selectAliAccount();
        List<TpAccountLog> stuList = new ArrayList<TpAccountLog>();
        Page PageSize = null;
        if(page != null ){
            PageSize=new Page(acount,Integer.valueOf(page));
            PageSize.setPageSize(10);
            stuList = tpUserAliAccountMapper.selectAliAccountList(PageSize.getStartPos(),PageSize.getPageSize());
        }else{
            PageSize = new Page(acount,1);
            PageSize.setPageSize(10);
            stuList = tpUserAliAccountMapper.selectAliAccountList(PageSize.getStartPos(),PageSize.getPageSize());
        }
        limitPageList.setPage(PageSize);
        limitPageList.setList(stuList);
        return limitPageList;
    }

    @Override
    public JSONObject getJsonUserAliAccount(TpUserAliAccount tpUserAliAccount) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", tpUserAliAccount.getId());
        jsonObject.put("real_name", tpUserAliAccount.getReal_name());
        jsonObject.put("mobile", tpUserAliAccount.getMobile());
        jsonObject.put("account", tpUserAliAccount.getAccount());
        jsonObject.put("add_time", tpUserAliAccount.getAdd_time());
        jsonObject.put("is_delete", tpUserAliAccount.getIs_delete());
        jsonObject.put("user_id", tpUserAliAccount.getUser_id());
        return jsonObject;
    }

    @Override
    public void getMoneyAli(String id, String money) {
        TpUserAliAccount tpUserAliAccount = tpUserAliAccountMapper.selectAliById(id);
        BigDecimal userMoney = tpUsersMapper.selectCountMoney(tpUserAliAccount.getUser_id());
        TpWithdrawals tpWithdrawals = new TpWithdrawals();
        if(Integer.parseInt(money) >= 15 && userMoney.intValue() >= 15){
            tpWithdrawals.setUser_id(tpUserAliAccount.getUser_id());
            tpWithdrawals.setMoney(Double.valueOf(Integer.parseInt(money)));
            tpWithdrawals.setCreate_time((int)(new Date().getTime()/1000));
            String bankName = "";
            tpWithdrawals.setBank_name(bankName);
            tpWithdrawals.setRealname(tpUserAliAccount.getReal_name());
            tpWithdrawals.setBank_card("");
            tpWithdrawals.setStatus(0);
            tpWithdrawals.setSend_type(1);
            tpWithdrawals.setTaxfee(5.00);
            tpWithdrawals.setIs_paid(0);
            tpWithdrawals.setQuery_time(0);
            tpWithdrawalsMapper.saveWithdrawals(tpWithdrawals);
            BigDecimal newUserMoney = userMoney.subtract((BigDecimal.valueOf(Integer.parseInt(money))).multiply(new BigDecimal(1).subtract(new BigDecimal(0.05))));
            tpUsersMapper.updateUserMoney(tpUserAliAccount.getUser_id(),newUserMoney);
        }else{
            throw new SendMessageException("最低提现金额:15");
        }
    }

    @Override
    public TpUserAliAccount findByIdAndUserId(Integer id, Integer user_id) {
        return tpUserAliAccountMapper.findByIdAndUserId(id, user_id);
    }
}
