package com.citytuike.service;

import com.citytuike.mapper.TpFansSaleMapper;
import com.citytuike.model.TpFansSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpFansSaleServiceImpl implements TpFansSaleService {
    @Autowired
    private TpFansSaleMapper tpFansSaleMapper;
    @Override
    public int fansSale(TpFansSale tpFansSale) {
      return  tpFansSaleMapper.save(tpFansSale);
    }
}
