package com.citytuike.service;

import com.citytuike.mapper.TpJoinUsMapper;
import com.citytuike.model.TpJoinUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpJoinUsServiceImpl implements TpJoinUsService {
    @Autowired
    private TpJoinUsMapper tpJoinUsMapper;
    @Override
    public int insert(TpJoinUs tpJoinUs) {
        int i = tpJoinUsMapper.insert(tpJoinUs);
        return i;
    }
}
