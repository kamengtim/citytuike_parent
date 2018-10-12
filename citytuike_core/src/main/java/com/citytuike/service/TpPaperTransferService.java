package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpPaperTransfer;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TpPaperTransferService {
    int saveTransfer(Integer fromUser, Integer tpUser, String number, String money, Integer paper_number_allowance);

    PageInfo selectList(String bigStatus, Integer user_id,String page);

    JSONObject selectArr(int id);

    List<TpPaperTransfer> selectAll(Integer user_id);

    JSONObject getJson(TpPaperTransfer tpPaperTransfer);

    TpPaperTransfer selectToUser(Integer user_id,Integer log_id);

    int updateStatus(Integer from_user_id, String log_id, Integer paper_number_allowance, Integer number);

    int addPaper(String status, Integer paper_number_allowance, Integer number,Integer log_id,Integer toUser);
}
