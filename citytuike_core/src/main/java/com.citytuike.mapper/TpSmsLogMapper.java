package com.citytuike.mapper;

import org.apache.ibatis.annotations.Param;

import com.citytuike.model.TpSmsLog;

public interface TpSmsLogMapper {

	TpSmsLog findOneByMobileAndCode(@Param("username")String username, @Param("mobileCode")String mobileCode);

	int updateByStatus(TpSmsLog tpSmsLog);



}
