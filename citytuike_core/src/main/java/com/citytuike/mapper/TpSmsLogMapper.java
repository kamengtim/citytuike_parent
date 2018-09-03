package com.citytuike.mapper;


import com.citytuike.model.TpSmsLog;
import org.apache.ibatis.annotations.Param;

public interface TpSmsLogMapper {

	TpSmsLog findOneByMobileAndCode(@Param("username") String username, @Param("mobileCode") String mobileCode);

	int updateByStatus(TpSmsLog tpSmsLog);


    void saveMsg(TpSmsLog tpSmsLog);

    String selectLog(String mobile);

    int selectCode(String mobile_code);
}
