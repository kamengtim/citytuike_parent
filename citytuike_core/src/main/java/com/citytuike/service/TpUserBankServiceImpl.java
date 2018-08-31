package com.citytuike.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpUserBankMapper;
import com.citytuike.model.TpUserBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpUserBankServiceImpl implements TpUserBankService {
    @Autowired
    private TpUserBankMapper tpUserBankMapper;
    public List<TpUserBank> getBankByUserId(Integer user_id) {
        List<TpUserBank>tpUserBanks = tpUserBankMapper.selectBankByUserId(user_id);
        return tpUserBanks;
    }

    public JSONObject getJsonBankAndUser(TpUserBank tpUserBank) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("add_time",tpUserBank.getAdd_time());
        jsonObject.put("bank_card",tpUserBank.getBank_card());
        jsonObject.put("bank_id",tpUserBank.getBank_id());
        jsonObject.put("bank_name",tpUserBank.getBank_name());
        jsonObject.put("branch",tpUserBank.getBranch());
        jsonObject.put("branch_area",tpUserBank.getBranch_area());
        jsonObject.put("id",tpUserBank.getId());
        jsonObject.put("id_card",tpUserBank.getId_card());
        jsonObject.put("is_delete",tpUserBank.getIs_delete());
        jsonObject.put("mobile",tpUserBank.getMobile());
        jsonObject.put("real_name",tpUserBank.getReal_name());
        jsonObject.put("user_id",tpUserBank.getUser_id());
        return jsonObject;
    }

    @Override
    public void save(TpUserBank tpUserBank) {
        tpUserBankMapper.insert(tpUserBank);
    }

    @Override
    public void deleteBank(Integer user_id, String id) {
        tpUserBankMapper.deleteBank(user_id,id);
    }
}
