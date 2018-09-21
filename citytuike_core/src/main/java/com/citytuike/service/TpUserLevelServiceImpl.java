package com.citytuike.service;

import com.citytuike.mapper.TpUserLevelMapper;
import com.citytuike.model.TpUserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpUserLevelServiceImpl implements TpUserLevelService {
    @Autowired
    private TpUserLevelMapper tpUserLevelMapper;
    @Override
    public TpUserLevel getLevelName(Integer level) {
        TpUserLevel tpUserLevel = tpUserLevelMapper.getLevelName(level);
        return tpUserLevel;
    }
}
