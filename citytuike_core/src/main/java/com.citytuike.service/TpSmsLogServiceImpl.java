package com.citytuike.service;


import org.aspectj.lang.annotation.Around;
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
	

}
