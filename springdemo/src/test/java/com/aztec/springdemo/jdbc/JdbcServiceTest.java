package com.aztec.springdemo.jdbc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aztec.springdemo.jdbc.JdbcService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/root-context.xml"})
@ActiveProfiles(profiles="dev")
public class JdbcServiceTest {

	@Autowired
	private JdbcService service;
	
	@Test
	public void testJdbcService() {
		String value = service.getValue("JDBC");
				
		assertTrue(value.contains("JDBC Success"));
		assertTrue(value.contains("DEV"));
	}
}