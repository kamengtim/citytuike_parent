package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpUserLogMapper;
import com.citytuike.mapper.TpUsersMapper;
import com.citytuike.model.TpUserLog;
import com.citytuike.model.TpUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpUserLogServiceImpl implements TpUserLogService {
    @Autowired
    private TpUserLogMapper tpUserLogMapper;
    @Autowired
    private TpUsersMapper tpUsersMapper;
    @Override
    public List<TpUserLog> paper() {
        List<TpUserLog>tpUserLogs =  tpUserLogMapper.getPaper();
        return tpUserLogs;
    }

    @Override
    public JSONObject getJson(TpUserLog tpUserLog) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("user_id",tpUserLog.getUser_id());
        TpUsers tpUsers = tpUsersMapper.getInviteCode(tpUserLog.getUser_id());
        jsonObject.put("nickname",tpUsers.getNickname());
        jsonObject.put("invite_code",tpUsers.getInvite_code());
        jsonObject.put("head_pic",tpUsers.getHead_pic());
        return jsonObject;
    }
}
