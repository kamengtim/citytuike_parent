package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpScanLog;

import java.util.List;

public interface TpScanLogService {
    List<TpScanLog> findAlltpScanLogService();

    TpScanLog getStutas(Integer id);

    void update(Integer id);

    JSONObject sendPaperWx(Integer id, Integer device_id, String paper_token, boolean is_test);
}
