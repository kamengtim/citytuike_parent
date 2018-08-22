package com.citytuike.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citytuike.model.TpRegion;
import com.citytuike.model.TpUserAddress;
import com.citytuike.model.TpUsers;

public interface TpUsersMapper {

	TpUsers findOneByLogo(@Param("username")String username, @Param("password")String password);

	int save(TpUsers tpUsers);
	
	int updateBytokenIn(TpUsers tpUsers);

	TpUsers findOneByToken(@Param("token")String token);

	int updateBytokenOut(TpUsers tpUsers);

	List<TpUserAddress> findAddressByUserId(@Param("user_id")Integer user_id);

	int insertUserAddress(TpUserAddress tpUserAddress);

	TpUserAddress findUserAddresById(@Param("id")Integer id);

	int updateUserAddress(TpUserAddress tpUserAddress);

	List<TpRegion> findAllByLevel(@Param("id")Integer id);

	int updateAddressToDefault(@Param("id")Integer id, @Param("i")int i);

	List<TpUserAddress> findIsDefaultAll(Integer user_id);



}
