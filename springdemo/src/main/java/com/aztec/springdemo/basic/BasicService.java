package com.aztec.springdemo.basic;

import com.aztec.springdemo.dao.ItemDao;

public class BasicService {
	
	private ItemDao dao;
	
	public BasicService(ItemDao dao) {
		this.dao = dao;
	}

	public String getValue(String key) {
		return dao.getValue(key);
	}
}
