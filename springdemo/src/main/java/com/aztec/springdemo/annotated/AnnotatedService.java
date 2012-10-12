package com.aztec.springdemo.annotated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aztec.springdemo.dao.DemoResultDao;

@Service
public class AnnotatedService {

	@Autowired
	@Qualifier("annotatedDao")
	private DemoResultDao dao;
		
	public String getResult(String type) {
		return dao.lookupResult(type);
	}
}
