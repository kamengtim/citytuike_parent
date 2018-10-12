package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpUserFeedbackMapper;
import com.citytuike.model.Page;
import com.citytuike.model.TpUserFeedback;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpUserFeedbackServiceImpl implements TpUserFeedbackService {
    @Autowired
    private TpUserFeedbackMapper tpUserFeedbackMapper;
    @Override
    public void insert(TpUserFeedback tpUserFeedback) {
        tpUserFeedbackMapper.insert(tpUserFeedback);
    }

    @Override
    public PageInfo query(Integer user_id) {
        Page page = new Page();
        PageHelper.startPage(page.getPageNow()+1,page.getPageSize());
        List list = tpUserFeedbackMapper.fansTypeList(user_id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public JSONObject getJson(TpUserFeedback tpUserFeedback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",tpUserFeedback.getContent());
        jsonObject.put("type",tpUserFeedback.getType());
        jsonObject.put("send_time",tpUserFeedback.getSend_time());
        jsonObject.put("feedback",tpUserFeedback.getUser_send());
        jsonObject.put("status",tpUserFeedback.getStatus());
        return jsonObject;
    }
}
