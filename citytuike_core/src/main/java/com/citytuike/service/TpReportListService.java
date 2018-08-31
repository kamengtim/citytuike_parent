package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpReportList;

import java.util.List;

public interface TpReportListService {
    List<TpReportList> getSendReport(Integer user_id);

    JSONObject getJsonReport(TpReportList tpReportList);

    void save(TpReportList tpReportList);
}
