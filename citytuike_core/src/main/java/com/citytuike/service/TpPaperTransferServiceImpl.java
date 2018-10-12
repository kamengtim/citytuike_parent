package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpPaperTransferMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.Page;
import com.citytuike.model.TpCartGift;
import com.citytuike.model.TpPaperTransfer;
import com.citytuike.model.TpUsers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service@Transactional
public class TpPaperTransferServiceImpl implements TpPaperTransferService {
    @Autowired
    private TpPaperTransferMapper tpPaperTransferMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Override
    public int saveTransfer(Integer fromUser, Integer toUser, String number, String money, Integer paper_number_allowance) {
        TpPaperTransfer tpPaperTransfer = new TpPaperTransfer();
        tpPaperTransfer.setFrom_user_id(fromUser);
        tpPaperTransfer.setTo_user_id(toUser);
        int a = Integer.parseInt(number);
        tpPaperTransfer.setNumber(a);
        BigDecimal newMoney = new BigDecimal(money);
        tpPaperTransfer.setMoney(newMoney);
        BigDecimal count = newMoney.multiply(BigDecimal.valueOf(a));
        tpPaperTransfer.setAll_money(count);
        tpPaperTransfer.setFrom_before_number(paper_number_allowance);
        tpPaperTransfer.setFrom_after_number(paper_number_allowance - a);
        tpPaperTransfer.setStatus(Byte.valueOf("1"));
        tpPaperTransfer.setRefuse_after_number(0);
        tpPaperTransfer.setRefuse_before_number(0);
        tpPaperTransfer.setRemark("");
        tpPaperTransfer.setTo_before_number(0);
        tpPaperTransfer.setTo_after_number(0);
        return tpPaperTransferMapper.save(tpPaperTransfer);
    }

    @Override
    public PageInfo selectList(String bigStatus, Integer user_id,String pageNow) {
        PageHelper.startPage(Integer.parseInt(pageNow)+1, 5);
        List<TpPaperTransfer> tpPaperTransfers = tpPaperTransferMapper.selectList(bigStatus,user_id);
        PageInfo pageInfo = new PageInfo(tpPaperTransfers);
        return pageInfo;
    }

    @Override
    public JSONObject selectArr(int id) {
        JSONObject jsonObject = new JSONObject();
        TpPaperTransfer tpPaperTransfer = tpPaperTransferMapper.selectArr(id);
        if (tpPaperTransfer == null){
            jsonObject.put("status","0");
            jsonObject.put("msg","没有数据");
            return jsonObject;
        }
        jsonObject.put("id",tpPaperTransfer.getId());
        jsonObject.put("add_time",tpPaperTransfer.getAdd_time());
        jsonObject.put("number",tpPaperTransfer.getNumber());
        jsonObject.put("money",tpPaperTransfer.getMoney());
        jsonObject.put("all_money",tpPaperTransfer.getAll_money());
        jsonObject.put("from_user_id",tpPaperTransfer.getFrom_user_id());
        jsonObject.put("to_user_id",tpPaperTransfer.getTo_user_id());
        jsonObject.put("status",tpPaperTransfer.getStatus());
        jsonObject.put("operate_time",tpPaperTransfer.getOperate_time());
        jsonObject.put("remark",tpPaperTransfer.getRemark());
        TpUsers tpUsers = tpUsersMapper.getFriendsName(tpPaperTransfer.getFrom_user_id());
        jsonObject.put("friend_name",tpUsers.getNickname());
        jsonObject.put("desc","您的好友"+tpUsers.getNickname()+"("+tpPaperTransfer.getFrom_user_id()+")"+"转让给您"+tpPaperTransfer.getNumber()+"包纸巾，单价为{"+tpPaperTransfer.getMoney()+"}/包，总金额为{"+tpPaperTransfer.getAll_money()+"}");
        return jsonObject;
    }

    @Override
    public List<TpPaperTransfer> selectAll(Integer user_id) {
        List<TpPaperTransfer> tpPaperTransfers = tpPaperTransferMapper.selectAll(user_id);
        return tpPaperTransfers;
    }

    @Override
    public JSONObject getJson(TpPaperTransfer tpPaperTransfer) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpPaperTransfer.getId());
        jsonObject.put("add_time",tpPaperTransfer.getAdd_time());
        jsonObject.put("number",tpPaperTransfer.getNumber());
        jsonObject.put("money",tpPaperTransfer.getMoney());
        jsonObject.put("all_money",tpPaperTransfer.getAll_money());
        jsonObject.put("from_user_id",tpPaperTransfer.getFrom_user_id());
        jsonObject.put("to_user_id",tpPaperTransfer.getTo_user_id());
        jsonObject.put("status",tpPaperTransfer.getStatus());
        jsonObject.put("operate_time",tpPaperTransfer.getOperate_time());
        jsonObject.put("remark",tpPaperTransfer.getRemark());
        TpUsers tpUsers = tpUsersMapper.getFriendsName(tpPaperTransfer.getFrom_user_id());
        jsonObject.put("friend_name",tpUsers.getNickname());
        jsonObject.put("desc","您的好友"+tpUsers.getNickname()+"("+tpPaperTransfer.getFrom_user_id()+")"+"转让给您"+tpPaperTransfer.getNumber()+"包纸巾，单价为{"+tpPaperTransfer.getMoney()+"}/包，总金额为{"+tpPaperTransfer.getAll_money()+"}");
        return jsonObject;
    }

    @Override
    public TpPaperTransfer selectToUser(Integer user_id,Integer log_id) {
        TpPaperTransfer tpPaperTransfer = tpPaperTransferMapper.selectToUser(user_id,log_id);
        return tpPaperTransfer;
    }

    @Override
    public int updateStatus(Integer from_user_id, String log_id, Integer paper_number_allowance, Integer number) {
        int i= tpPaperTransferMapper.updateStatus(from_user_id,log_id,paper_number_allowance,number);
        return i;
    }

    @Override
    public int addPaper(String status, Integer paper_number_allowance, Integer number,Integer log_id,Integer toUser) {
        int i = tpPaperTransferMapper.addPaper(status,paper_number_allowance,number,log_id,toUser);
        return i;
    }
}
