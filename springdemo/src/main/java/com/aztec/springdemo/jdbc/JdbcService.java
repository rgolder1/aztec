package com.aztec.springdemo.jdbc;

import com.aztec.springdemo.dao.ItemDao;

public class JdbcService {

	private ItemDao dao;
	
	public JdbcService(ItemDao dao) {
		this.dao = dao;
	}

	public String getValue(String key) {
		return dao.getValue(key);
	}
}
