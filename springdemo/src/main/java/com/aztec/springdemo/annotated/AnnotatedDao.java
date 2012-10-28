package com.aztec.springdemo.annotated;

import org.springframework.stereotype.Component;

import com.aztec.springdemo.dao.ItemDao;

@Component
public class AnnotatedDao implements ItemDao {
	
	@Override
	public String getValue(String key) {
		String value = null;
		if(key!=null && key.equals("Annotated")) {
			value = "Annotated Success";
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
