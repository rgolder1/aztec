package com.aztec.springdemo.dao;

public interface ItemDao {

	public String getValue(String key);
	
	public void createItem(Long id, String key, String value);
	
	public void deleteByKey(String key);
}
