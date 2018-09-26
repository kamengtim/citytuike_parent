package com.citytuike.service;

import com.citytuike.mapper.TpTenSecondsActivityConfigMapper;
import com.citytuike.model.TpTenSecondsActivityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpTenSecondsActivityConfigServiceImpl implements TpTenSecondsActivityConfigService {
    @Autowired
    private TpTenSecondsActivityConfigMapper tpTenSecondsActivityConfigMapper;
    @Override
    public TpTenSecondsActivityConfig getConfig(String activity_id) {
        TpTenSecondsActivityConfig tpTenSecondsActivityConfig = tpTenSecondsActivityConfigMapper.getConfig(activity_id);
        return tpTenSecondsActivityConfig;
    }
}
