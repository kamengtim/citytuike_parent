package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpReportListMapper;
import com.citytuike.model.TpReportList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpReportListServiceImpl implements TpReportListService {
    @Autowired
    private TpReportListMapper tpReportListMapper;
    @Override
    public List<TpReportList> getSendReport(Integer user_id) {
       return tpReportListMapper.getSendReport(user_id);
    }

    @Override
    public JSONObject getJsonReport(TpReportList tpReportList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address",tpReportList.getAddress());
        jsonObject.put("area",tpReportList.getArea());
        jsonObject.put("han_time",tpReportList.getHan_time());
        jsonObject.put("id",tpReportList.getId());
        jsonObject.put("image",tpReportList.getImage());
        jsonObject.put("reply_mess",tpReportList.getReply_mess());
        jsonObject.put("report_mess",tpReportList.getReport_mess());
        jsonObject.put("send_time",tpReportList.getSend_time());
        jsonObject.put("status",tpReportList.getStatus());
        jsonObject.put("user_id",tpReportList.getUser_id());
        return jsonObject;
    }

    @Override
    public void save(TpReportList tpReportList) {
        tpReportListMapper.insert(tpReportList);
    }
}
