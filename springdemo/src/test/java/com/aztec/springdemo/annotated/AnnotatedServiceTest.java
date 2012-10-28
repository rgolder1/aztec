package com.aztec.springdemo.annotated;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aztec.springdemo.annotated.AnnotatedService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/application-context.xml"})
public class AnnotatedServiceTest {

	@Autowired
	private AnnotatedService service;
	
	@Test
	public void testAnnotatedService() {
		String value = service.getValue("Annotated");

		assertTrue(value.contains("Annotated Succes"));
	}
}
