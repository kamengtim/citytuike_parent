package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpBank;

import java.util.List;

public interface TpBankService {
    TpBank getListBank(Integer bankId);

    JSONObject getJsonBank(TpBank tpBank);
}