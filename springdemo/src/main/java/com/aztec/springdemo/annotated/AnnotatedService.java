package com.aztec.springdemo.annotated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aztec.springdemo.dao.ItemDao;

@Service
public class AnnotatedService {

	@Autowired
	@Qualifier("annotatedDao")
	private ItemDao dao;
		
	public String getValue(String key) {
		return dao.getValue(key);
	}
}
