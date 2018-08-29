package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpWithdrawals;

import java.util.List;

public interface TpWithdrawalsService {
    LimitPageList getWithdrawalsList(Integer user_id,String page);

    JSONObject JsonWithdrawals(TpWithdrawals tpWithdrawal);
}
