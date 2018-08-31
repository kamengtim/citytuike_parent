package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpCardListMapper;
import com.citytuike.model.TpCardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.TabbedPaneUI;
import java.util.List;

@Service
public class TpCardListServiceImpl implements TpCardListService {
    @Autowired
    private TpCardListMapper tpCardListMapper;
    @Override
    public List<TpCardList> selectCardList() {
        return tpCardListMapper.selectAll();
    }

    @Override
    public JSONObject getJsonString(TpCardList tpCardList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpCardList.getId());
        jsonObject.put("logo",tpCardList.getLogo());
        jsonObject.put("title",tpCardList.getTitle());
        jsonObject.put("apply_num",tpCardList.getApply_num());
        jsonObject.put("tig",tpCardList.getTig());
        jsonObject.put("money",tpCardList.getMoney());
        jsonObject.put("gift",tpCardList.getGift());
        jsonObject.put("create_time",tpCardList.getCreate_time());
        jsonObject.put("url",tpCardList.getUrl());
        jsonObject.put("card_logo",tpCardList.getCard_logo());
        jsonObject.put("card_logo2",tpCardList.getCard_logo2());
        jsonObject.put("partner_money",tpCardList.getPartner_money());
        jsonObject.put("chief_money",tpCardList.getChief_money());
        jsonObject.put("manager_money",tpCardList.getManager_money());
        jsonObject.put("status",tpCardList.getStatus());
        return jsonObject;
    }

    @Override
    public int countCard() {
        return tpCardListMapper.countCard();
    }
}
