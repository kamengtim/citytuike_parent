package com.citytuike.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpApplyReportMapper;
import com.citytuike.model.TpApplyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpApplyReportServiceImpl implements TpApplyReportService {
    @Autowired
    private TpApplyReportMapper tpApplyReportMapper;
    @Override
    public List<TpApplyReport> getCardList() {
        List<TpApplyReport> tpApplyReport = tpApplyReportMapper.selectList();
        return tpApplyReport;
    }

    @Override
    public JSONObject getJsonApplyReport(TpApplyReport tpApplyReport) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpApplyReport.getId());
        jsonObject.put("Idcard",tpApplyReport.getIdcard());
        jsonObject.put("Mobile",tpApplyReport.getMobile());
        jsonObject.put("Name_first",tpApplyReport.getName_first());
        jsonObject.put("Add_time",tpApplyReport.getAdd_time());
        jsonObject.put("Bank_name",tpApplyReport.getBank_name());
        jsonObject.put("Bank_id",tpApplyReport.getBank_id());
        jsonObject.put("Status",tpApplyReport.getStatus());
        jsonObject.put("Tig",tpApplyReport.getTig());
        jsonObject.put("Send_gift",tpApplyReport.getSend_gift());
        jsonObject.put("Gift",tpApplyReport.getGift());
        jsonObject.put("Money",tpApplyReport.getMoney());
        jsonObject.put("Name_last",tpApplyReport.getName_last());
        jsonObject.put("Id_card_all",tpApplyReport.getId_card_all());
        jsonObject.put("Mobile_all",tpApplyReport.getMobile_all());
        jsonObject.put("User_id",tpApplyReport.getUser_id());
        return jsonObject;
    }
}
