package com.citytuike.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citytuike.mapper.TpSmsLogMapper;
import com.citytuike.model.TpSmsLog;

@Service
public class TpSmsLogServiceImpl implements TpSmsLogService{
	@Autowired
	private TpSmsLogMapper tpSmsLogMapper;

	public TpSmsLog findOneByMobileAndCode(String username, String mobileCode) {
		return tpSmsLogMapper.findOneByMobileAndCode(username, mobileCode);
	}

	public int updateByStatus(TpSmsLog tpSmsLog) {
		return tpSmsLogMapper.updateByStatus(tpSmsLog);
	}

	@Override
	public String selectLog(String mobile) {
		return tpSmsLogMapper.selectLog(mobile);
	}

	@Override
	public int selectCode(String mobile_code) {
	   return tpSmsLogMapper.selectCode(mobile_code);

	}

	@Override
	public TpSmsLog selectvalidateCode(String code, String mobile) {
		return tpSmsLogMapper.selectvalidateCode(code,mobile);
	}


}
