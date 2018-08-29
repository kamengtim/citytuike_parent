package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpBankMapper;
import com.citytuike.model.TpBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpBankServiceImpl implements TpBankService {
    @Autowired
    private TpBankMapper tpBankMapper;
    public TpBank getListBank(Integer bank_id) {
        TpBank tpBank = tpBankMapper.selectByPrimaryKey(bank_id);
        return tpBank;
    }

    public JSONObject getJsonBank(TpBank tpBank) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("apply_need",tpBank.getApply_need());
        jsonObject.put("apply_num",tpBank.getApply_num());
        jsonObject.put("apply_rate",tpBank.getApply_rate());
        jsonObject.put("apply_speed",tpBank.getApply_speed());
        jsonObject.put("avg_apply_num",tpBank.getAvg_apply_num());
        jsonObject.put("avg_quota",tpBank.getAvg_quota());
        jsonObject.put("background",tpBank.getBackground());
        jsonObject.put("banner",tpBank.getBanner());
        jsonObject.put("banner_index",tpBank.getBanner_index());
        jsonObject.put("bill_day",tpBank.getBill_day());
        jsonObject.put("bonus",tpBank.getBonus());
        jsonObject.put("code",tpBank.getCode());
        jsonObject.put("color",tpBank.getColor());
        jsonObject.put("create_at",tpBank.getCreate_at());
        jsonObject.put("credit_card_desc",tpBank.getCredit_card_desc());
        jsonObject.put("desc",tpBank.getDesc());
        jsonObject.put("free_day",tpBank.getFree_day());
        jsonObject.put("free_fee_times",tpBank.getFree_fee_times());
        jsonObject.put("id",tpBank.getId());
        jsonObject.put("jump_url",tpBank.getJump_url());
        jsonObject.put("name",tpBank.getName());
        jsonObject.put("need_query",tpBank.getNeed_query());
        jsonObject.put("nuclear_card",tpBank.getNuclear_card());
        jsonObject.put("open_url",tpBank.getOpen_url());
        jsonObject.put("partner_id",tpBank.getPartner_id());
        jsonObject.put("pass_rate",tpBank.getPass_rate());
        jsonObject.put("real_apply_num",tpBank.getReal_apply_num());
        jsonObject.put("recommend_rate",tpBank.getRecommend_rate());
        jsonObject.put("school_url",tpBank.getSchool_url());
        jsonObject.put("sort",tpBank.getSort());
        jsonObject.put("status",tpBank.getStatus());
        jsonObject.put("tag",tpBank.getTag());
        jsonObject.put("tel",tpBank.getTel());
        jsonObject.put("thumb",tpBank.getThumb());
        jsonObject.put("thumb_l",tpBank.getThumb_l());
        jsonObject.put("use_bonus",tpBank.getUse_bonus());
        jsonObject.put("work_day",tpBank.getWork_day());
        jsonObject.put("year_fee",tpBank.getYear_fee());
        jsonObject.put("type",tpBank.getType());
        return jsonObject;
    }
}
