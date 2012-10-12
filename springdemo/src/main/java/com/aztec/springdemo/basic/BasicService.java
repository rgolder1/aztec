package com.aztec.springdemo.basic;

import com.aztec.springdemo.dao.DemoResultDao;

public class BasicService {
	
	private DemoResultDao dao;
	
	public BasicService(DemoResultDao dao) {
		this.dao = dao;
	}

	public String getResult(String type) {
		return dao.lookupResult(type);
	}
}
