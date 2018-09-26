package com.citytuike.mapper;


import com.citytuike.model.TpCardList;
import com.citytuike.model.TpCartGift;

import java.util.List;

public interface TpCardListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TpCardList record);

    TpCardList selectByPrimaryKey(Integer id);

    List<TpCardList> selectAll();

    int updateByPrimaryKey(TpCardList record);

    int countCard();

    TpCardList selectUserBankById(String cardid);

    TpCardList getCard(Integer card_list_id);

}