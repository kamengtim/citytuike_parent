package com.citytuike.mapper;


import com.citytuike.model.TpSmsLog;
import org.apache.ibatis.annotations.Param;

public interface TpSmsLogMapper {

	TpSmsLog findOneByMobileAndCode(@Param("username") String username, @Param("mobileCode") String mobileCode);

	int updateByStatus(TpSmsLog tpSmsLog);



}
