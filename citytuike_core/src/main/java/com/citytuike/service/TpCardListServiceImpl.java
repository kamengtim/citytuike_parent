package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpApplyCardMapper;
import com.citytuike.mapper.TpApplyReportMapper;
import com.citytuike.mapper.TpCardListMapper;
import com.citytuike.mapper.TpUserBankMapper;
import com.citytuike.model.TpApplyCard;
import com.citytuike.model.TpApplyReport;
import com.citytuike.model.TpCardList;
import com.citytuike.model.TpUserBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.TabbedPaneUI;
import java.util.Date;
import java.util.List;

@Service
public class TpCardListServiceImpl implements TpCardListService {
    @Autowired
    private TpCardListMapper tpCardListMapper;
    @Autowired
    private TpApplyReportMapper tpApplyReportMapper;
    @Autowired
    private TpApplyCardMapper tpApplyCardMapper;
    @Autowired
    private TpUserBankMapper tpUserBankMapper;
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

    @Override
    public void save(String mobile_code, String cardid, String id) {
        TpApplyCard tpApplyCard = tpApplyCardMapper.selectListId(id);
        TpApplyReport tpApplyReport = new TpApplyReport();
        tpApplyReport.setIdcard(tpApplyCard.getIdcard().substring(12,tpApplyCard.getIdcard().length()));
        tpApplyReport.setMobile(tpApplyCard.getMobile().substring(4,tpApplyCard.getMobile().length()));
        tpApplyReport.setName_first(tpApplyCard.getName().substring(0,1));
        tpApplyReport.setAdd_time((int)(new Date().getTime()/1000));
        TpCardList tpCardList = tpCardListMapper.selectUserBankById(cardid);
        tpApplyReport.setBank_name(tpCardList.getTitle());
        tpApplyReport.setBank_id(tpCardList.getId());
        tpApplyReport.setStatus(false);
        tpApplyReport.setTig(null);
        tpApplyReport.setSend_gift(false);
        tpApplyReport.setGift("话费");
        tpApplyReport.setMoney((long) 50);
        tpApplyReport.setName_last(tpApplyCard.getName().substring(1,tpApplyCard.getName().length()));
        tpApplyReport.setId_card_all(tpApplyCard.getIdcard());
        tpApplyReport.setMobile_all(tpApplyCard.getMobile());
        tpApplyReport.setUser_id(tpApplyCard.getUser_id());
        tpApplyReportMapper.save(tpApplyReport);
    }

    @Override
    public JSONObject cardDetail(Integer id) {
        JSONObject jsonObject= new JSONObject();
        TpCardList tpCardList = tpCardListMapper.selectByPrimaryKey(id);
        String card_logo = tpCardList.getCard_logo();
        String card_logo2 = tpCardList.getCard_logo2();
        jsonObject.put("card_logo",card_logo);
        jsonObject.put("card_logo2",card_logo2);
        jsonObject.put("id",tpCardList.getId());
        return jsonObject;
    }
}
