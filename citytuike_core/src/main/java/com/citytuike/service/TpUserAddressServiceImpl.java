package com.citytuike.service;

import com.citytuike.mapper.TpUserAddressMapper;
import com.citytuike.model.TpUserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpUserAddressServiceImpl implements TpUserAddressService {
    @Autowired
    private TpUserAddressMapper tpUserAddressMapper;
    @Override
    public TpUserAddress getAddress(String address_id, Integer user_id) {
        TpUserAddress tpUserAddress = tpUserAddressMapper.getAddress(address_id,user_id);
        return tpUserAddress;
    }
}
