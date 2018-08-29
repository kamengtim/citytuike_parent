package com.citytuike.service;

import com.citytuike.mapper.TpAccountLogMapper;
import com.citytuike.model.TpAccountLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITpAccountLogServiceImpl implements ITpAccountLogService {
    @Autowired
    private TpAccountLogMapper accountLogMapper;
    public TpAccountLog UserMoney(Integer user_id) {
        //accountLogMapper.selectUserMoney();
        return null;
    }

    public int insertAccountLog(TpAccountLog tpAccountLog) {
        return accountLogMapper.insert(tpAccountLog);
    }
}
