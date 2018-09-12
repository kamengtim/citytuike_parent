package com.citytuike.service;

import com.citytuike.mapper.TpWxUserMapper;
import com.citytuike.model.TpWxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpWxUserServiceImpl implements TpWxUserService {
    @Autowired
    private TpWxUserMapper tpWxUserMapper;
    @Override
    public List<TpWxUser> getWxUser() {
        List<TpWxUser> tpWxUser =  tpWxUserMapper.getWxUser();
        return tpWxUser;
    }
}
