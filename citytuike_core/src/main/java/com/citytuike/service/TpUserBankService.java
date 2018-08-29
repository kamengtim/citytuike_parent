package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUserBank;

import java.util.List;

public interface TpUserBankService {
    List<TpUserBank> getBankByUserId(Integer user_id);

    JSONObject getJsonBankAndUser(TpUserBank tpUserBank);
}
