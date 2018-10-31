package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpUserAliAccount;

import java.util.List;

public interface TpUserAliAccountService {
    void addAliAccount(String real_name, String mobile, String account);

    LimitPageList AliAccount(String page);

    JSONObject getJsonUserAliAccount(TpUserAliAccount tpUserAliAccount);

    void getMoneyAli(String id, String money);

    TpUserAliAccount findByIdAndUserId(Integer id, Integer user_id);
}
