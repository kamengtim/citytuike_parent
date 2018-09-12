package com.citytuike.service;

import com.citytuike.mapper.TpAppVersionMapper;
import com.citytuike.model.TpAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpAppVersionServiceImpl implements TpAppVersionService {
    @Autowired
    private TpAppVersionMapper tpAppVersionMapper;
    @Override
    public TpAppVersion getVersion() {
        TpAppVersion tpAppVersion = tpAppVersionMapper.getVersion();
        return tpAppVersion;
    }
}
