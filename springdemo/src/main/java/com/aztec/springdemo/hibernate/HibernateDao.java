package com.aztec.springdemo.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aztec.springdemo.dao.DemoResultDao;
import com.aztec.springdemo.hibernate.repository.DemoRepository;

@Component
public class HibernateDao implements DemoResultDao {
	
	@Autowired
	private DemoRepository repository;
		
	@Override
	public String lookupResult(String type) {
		String result = null;
		
		DemoResult demoResult = repository.findByType(type);  
		if(demoResult!=null) {
			result = demoResult.getResult();
		}
		
		return result;
	}
}
