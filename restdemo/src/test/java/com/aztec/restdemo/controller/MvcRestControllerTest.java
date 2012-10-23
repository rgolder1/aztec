package com.aztec.restdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aztec.restdemo.controller.MvcRestController;
import com.aztec.restdemo.service.EntityService;
import com.aztec.restdemo.types.Entity;

public class MvcRestControllerTest {
	
	private static final long KEY = 111;
	private static final String VALUE = "value";
	private static final String VALUE_UPDATED = "value_updated";
	private static final long INVALID_KEY = 999;
	
	private static final String URI_CREATED = "/mvc/rest/entity/";

	private EntityService service;
	private MvcRestController controller;
	private Entity entity;
	
	@Before
	public void setUp() {
		service = new EntityService();
		controller = new MvcRestController(service);
		
		entity = new Entity();
		entity.setKey(KEY);
		entity.setValue(VALUE);
	}
	
	@Test
	public void testCreateEntitySuccess() {
		ResponseEntity<String> response = controller.createEntity(entity);
		assertNotNull(response);
		assertEquals(URI.create(URI_CREATED+KEY), response.getHeaders().getLocation());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(1, service.size());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}
	
	@Test
	public void testCreateEntityFail() {
		ResponseEntity<String> response = controller.createEntity(new Entity());
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNull(response.getBody());
		assertTrue(service.isEmpty());
	}
	
	@Test
	public void testGetEntitySuccess() {
		controller.createEntity(entity);
		ResponseEntity<Entity> response = controller.getEntity(KEY);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(KEY, response.getBody().getKey());
		assertEquals(VALUE, response.getBody().getValue());
	}
	
	@Test
	public void testGetEntityFail() {
		ResponseEntity<Entity> response = controller.getEntity(INVALID_KEY);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	public void testDeleteEntitySuccess() {
		controller.createEntity(entity);
		ResponseEntity<String> response = controller.deleteEntity(KEY);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testDeleteEntityFail() {
		controller.createEntity(entity);
		ResponseEntity<String> response = controller.deleteEntity(INVALID_KEY);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	public void testUpdateEntitySuccess() {
		controller.createEntity(entity);
		Entity updatedEntity = new Entity();
		updatedEntity.setKey(KEY);
		updatedEntity.setValue(VALUE_UPDATED);
		ResponseEntity<String> response = controller.updateEntity(KEY, updatedEntity);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	public void testUpdateEntityFail() {
		Entity updatedEntity = new Entity();
		updatedEntity.setKey(KEY);
		updatedEntity.setValue(VALUE_UPDATED);
		ResponseEntity<String> response = controller.updateEntity(KEY, updatedEntity);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
}
