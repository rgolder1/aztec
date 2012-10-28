package com.aztec.springdemo.basic;

import com.aztec.springdemo.dao.ItemDao;

public class BasicDao implements ItemDao {

	@Override
	public String getValue(String key) {
		String value = null;
		if(key!=null && key.equals("Basic")) {
			value = "Basic Success";
		}
		return value;
	}

	@Override
	public void createItem(Long id, String key, String value) {
		// No-op.
	}

	@Override
	public void deleteByKey(String key) {
		// No-op.
	}
}
