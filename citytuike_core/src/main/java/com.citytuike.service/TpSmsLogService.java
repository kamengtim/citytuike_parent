package com.citytuike.service;

import com.citytuike.model.TpSmsLog;

public interface TpSmsLogService {

	TpSmsLog findOneByMobileAndCode(String username, String mobileCode);

	int updateByStatus(TpSmsLog tpSmsLog);


}
