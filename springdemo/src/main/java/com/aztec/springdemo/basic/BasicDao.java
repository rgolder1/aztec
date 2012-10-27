package com.aztec.springdemo.basic;

import com.aztec.springdemo.dao.DemoResultDao;

public class BasicDao implements DemoResultDao {

	@Override
	public String lookupResult(String type) {
		String result = null;
		if(type!=null && type.equals("Basic")) {
			result = "Basic Success";
		}
		return result;
	}

	@Override
	public void createRecord(Long id, String type, String result) {
		// No-op.
	}

	@Override
	public void deleteByType(String type) {
		// No-op.
	}
}
