package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpCardListMapper;
import com.citytuike.mapper.TpCartGiftMapper;
import com.citytuike.mapper.TpUserLevelMapper;
import com.citytuike.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpCartGiftServiceImpl implements TpCartGiftService {
    @Autowired
    private TpCartGiftMapper tpCartGiftMapper;
    @Autowired
    private TpCardListMapper tpCardListMapper;
    @Autowired
    private TpUserLevelMapper tpUserLevelMapper;

    @Override
    public int getCount(Integer user_id) {
        int count = tpCartGiftMapper.getCount(user_id);
        return count;
    }

    @Override
    public PageInfo query(int count, Integer user_id) {
        Page page = new Page();
        page.setTotalCount(count);
        PageHelper.startPage(page.getPageNow(),page.getPageSize());
        List<TpCartGift> list = tpCartGiftMapper.selectByPage(page.getPageNow(),10,user_id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public JSONObject getJson(TpUsers tpUsers,TpCartGift tpCartGift, Integer level, String nickname) {
        JSONObject jsonObject = new JSONObject();
        String level_name = tpUserLevelMapper.selectLevelName(tpUsers.getLevel());
        jsonObject.put("id",tpCartGift.getId());
        jsonObject.put("gift_name",tpCartGift.getGift_name());
        jsonObject.put("create_time",tpCartGift.getCreate_time());
        jsonObject.put("add_time",tpCartGift.getAdd_time());
        jsonObject.put("gift_type",tpCartGift.getGift_type());
        jsonObject.put("status",tpCartGift.getStatus());
        jsonObject.put("money",tpCartGift.getMoney());
        jsonObject.put("user_id",tpCartGift.getUser_id());
        jsonObject.put("card_list_id",tpCartGift.getCard_list_id());
        jsonObject.put("report_id",tpCartGift.getReport_id());
        jsonObject.put("mess",tpCartGift.getMess());
        jsonObject.put("orderid",tpCartGift.getOrderid());
        jsonObject.put("mobile",tpCartGift.getMobile());
        jsonObject.put("taskid",tpCartGift.getTaskid());
        jsonObject.put("obtain",tpCartGift.getObtain());
        jsonObject.put("user_time",tpCartGift.getUser_time());
        TpCardList tpCardList = tpCardListMapper.getCard(tpCartGift.getCard_list_id());

        jsonObject.put("level",tpUsers.getLevel());
        jsonObject.put("nickname",nickname);
        jsonObject.put("level_name",level_name);
        jsonObject.put("bank_name",tpCardList.getTitle());
        jsonObject.put("logo",tpCardList.getLogo());
        return jsonObject;
    }
}
