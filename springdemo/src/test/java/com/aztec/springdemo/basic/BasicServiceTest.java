package com.aztec.springdemo.basic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aztec.springdemo.basic.BasicService;

public class BasicServiceTest {

	@Test
	public void testBasicService() {
		BasicService service = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml").getBean(BasicService.class);

		String value = service.getValue("Basic");
		
		assertTrue(value.contains("Basic Success"));
	}
}
