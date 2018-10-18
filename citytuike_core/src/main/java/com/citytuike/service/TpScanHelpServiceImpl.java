package com.citytuike.service;

import com.citytuike.mapper.TpScanHelpMapper;
import com.citytuike.model.TpScanHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TpScanHelpServiceImpl implements TpScanHelpService {
    @Autowired
    private TpScanHelpMapper tpScanHelpMapper;
    @Override
    public int fansHelp(Integer user_id) {
        return tpScanHelpMapper.fansHelp(user_id);
    }

    @Override
    public int toDayHelp(Integer invite_id) {
        return tpScanHelpMapper.toDayHelp(invite_id);
    }

    @Override
    public int insert(Integer user_id, Integer invite_id) {
        TpScanHelp tpScanHelp = new TpScanHelp();
        tpScanHelp.setUser_id(invite_id);
        tpScanHelp.setFriend_id(user_id);
        tpScanHelp.setAdd_time((int)(new Date().getTime()/1000));
        return tpScanHelpMapper.insert(tpScanHelp);
    }
}
