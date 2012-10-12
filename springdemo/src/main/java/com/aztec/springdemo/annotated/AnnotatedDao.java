package com.aztec.springdemo.annotated;

import org.springframework.stereotype.Component;

import com.aztec.springdemo.dao.DemoResultDao;

@Component
public class AnnotatedDao implements DemoResultDao {
	
	@Override
	public String lookupResult(String type) {
		String result = null;
		if(type!=null && type.equals("Annotated")) {
			result = "Annotated Success";
		}
		return result;
	}
}
