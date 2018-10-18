package com.citytuike.service;

import com.citytuike.model.TpApplyCard;

import java.util.List;

public interface TpApplyCardService {
    void save(Integer user_id, String cardid, String name, String idcard, String mobile, String mobile_code, String area);

    void deleteApplyPeople(Integer user_id, int id);

    List<TpApplyCard> selectList(Integer user_id);
}
