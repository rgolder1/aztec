package com.aztec.webapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.aztec.webappdemo.controller.MvcRestController;
import com.aztec.webappdemo.service.EntityService;
import com.aztec.webappdemo.types.EntityCreation;

public class MvcRestControllerTest {
	
	private static final long KEY = 111;
	private static final String VALUE = "value";
	
	private static final String URI_CREATED = "/mvc/rest/entity/";

	@Test
	public void testCreateEntity() {
		EntityService service = new EntityService();
		MvcRestController controller = new MvcRestController(service);
		EntityCreation entityCreation = new EntityCreation();
		entityCreation.setKey(KEY);
		entityCreation.setValue(VALUE);
		
		ResponseEntity<String> response = controller.createEntity(entityCreation);
		assertNotNull(response);
		assertEquals(URI.create(URI_CREATED+KEY), response.getHeaders().getLocation());
		assertEquals(1, service.size());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}
}
