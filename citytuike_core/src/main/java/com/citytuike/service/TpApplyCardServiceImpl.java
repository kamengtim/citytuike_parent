package com.citytuike.service;

import com.citytuike.mapper.TpApplyCardMapper;
import com.citytuike.model.TpApplyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TpApplyCardServiceImpl implements TpApplyCardService {
    @Autowired
    private TpApplyCardMapper tpApplyCardMapper;
    @Override
    public void save(Integer user_id, String cardid, String name, String idcard, String mobile, String mobile_code, String area) {
        TpApplyCard tpApplyCard = new TpApplyCard();
        tpApplyCard.setName(name);
        tpApplyCard.setMobile(mobile);
        tpApplyCard.setIdcard(idcard);
        tpApplyCard.setArea(area);
        tpApplyCard.setAdd_time((int)(new Date().getTime()/1000));
        tpApplyCard.setUser_id(user_id);
        tpApplyCardMapper.insert(tpApplyCard);
    }

    @Override
    public void deleteApplyPeople(Integer user_id, int id) {
        tpApplyCardMapper.deleteApplyPeople(user_id,id);
    }
}
