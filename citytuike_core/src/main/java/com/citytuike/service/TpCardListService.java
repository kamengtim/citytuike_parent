package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpCardList;

import java.util.List;

public interface TpCardListService {
    List<TpCardList> selectCardList();

    JSONObject getJsonString(TpCardList tpCardList);

    int countCard();
}
