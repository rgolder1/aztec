package com.aztec.springdemo.jdbc;

import com.aztec.springdemo.dao.DemoResultDao;

public class JdbcService {

	private DemoResultDao dao;
	
	public JdbcService(DemoResultDao dao) {
		this.dao = dao;
	}

	public String getResult(String type) {
		return dao.lookupResult(type);
	}
}
