package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpApplyReport;

import java.util.List;

public interface TpApplyReportService {
    List<TpApplyReport> getCardList();

    JSONObject getJsonApplyReport(TpApplyReport tpApplyReport);
}
