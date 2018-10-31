package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpUsers;
import com.citytuike.model.TpWithdrawals;

import java.math.BigDecimal;
import java.util.List;

public interface TpWithdrawalsService {
    LimitPageList getWithdrawalsList(Integer user_id,String page);

    JSONObject JsonWithdrawals(TpWithdrawals tpWithdrawal);

    BigDecimal selectWithdrawalsMoney(Integer user_id);

    void ApplyForWithdrawals(Integer user_id, int id, float money);

    int insertWithdrawals(TpWithdrawals tpWithdrawals);
}
