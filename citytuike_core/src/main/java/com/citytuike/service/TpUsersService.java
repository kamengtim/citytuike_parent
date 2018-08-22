package com.citytuike.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.TpRegion;
import com.citytuike.model.TpUserAddress;
import com.citytuike.model.TpUsers;

public interface TpUsersService {

	TpUsers findOneByLogo(String username, String password);

	int save(TpUsers tpUsers);

	int updateBytokenIn(TpUsers tpUsers);

	TpUsers findOneByToken(String token);

	int updateBytokenOut(TpUsers tpUsers);

	JSONObject getUserlJson(TpUsers tpUsers);

	List<TpUserAddress> findAddressByUserId(Integer user_id);

	int insertUserAddress(TpUserAddress tpUserAddress);

	TpUserAddress findUserAddresById(Integer id);

	int updateUserAddress(TpUserAddress tpUserAddress);

	List<TpRegion> findAllByLevel(Integer id);

	int updateAddressToDefault(Integer id, int i);

	List<TpUserAddress> findIsDefaultAll(Integer user_id);



}
