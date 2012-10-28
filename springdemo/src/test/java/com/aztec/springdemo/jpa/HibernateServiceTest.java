package com.aztec.springdemo.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aztec.springdemo.hibernate.HibernateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/root-context.xml"})
@ActiveProfiles(profiles="dev")
public class HibernateServiceTest {

	@Autowired
	private HibernateService service;
	
	@Test
	public void testHibernateService() {
		String value = service.getValue("JPA");

		assertTrue(value.contains("JPA Success"));
		assertTrue(value.contains("DEV"));
	}
	
	@Test
	public void testCreateAndDelete() {
		Long id = 10l;
		String type = "TEST";
		String result = "CREATED";
		
		// Create and assert.
		service.createItem(id, type, result);
		assertEquals("CREATED", service.getValue("TEST"));
		
		// Delete and assert.
		service.deleteByType(type);
		assertNull(service.getValue("TEST"));
	}
}
