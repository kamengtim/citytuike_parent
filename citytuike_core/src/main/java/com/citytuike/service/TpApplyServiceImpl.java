package com.citytuike.service;

import com.citytuike.mapper.TpApplyMapper;
import com.citytuike.model.TpApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpApplyServiceImpl implements TpApplyService {
    @Autowired
    private TpApplyMapper tpApplyMapper;
    @Override
    public int insert(TpApply tpApply) {
        int i= tpApplyMapper.insert(tpApply);
        return i;
    }
}
