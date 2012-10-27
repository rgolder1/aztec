package com.aztec.springdemo.dao;

public interface DemoResultDao {

	public String lookupResult(String type);
	
	public void createRecord(Long id, String type, String result);
	
	public void deleteByType(String type);
}
