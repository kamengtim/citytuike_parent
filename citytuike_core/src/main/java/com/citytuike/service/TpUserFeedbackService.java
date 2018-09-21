package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpUserFeedback;
import com.github.pagehelper.PageInfo;

public interface TpUserFeedbackService {
    void insert(TpUserFeedback tpUserFeedback);

    PageInfo query(Integer user_id);

    JSONObject getJson(TpUserFeedback tpUserFeedback);
}
