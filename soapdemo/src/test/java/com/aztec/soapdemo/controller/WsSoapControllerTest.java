package com.aztec.soapdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aztec.soapdemo.service.EntityService;
import com.aztec.soapdemo.types.EntityCreateRequest;
import com.aztec.soapdemo.types.EntityDeleteRequest;
import com.aztec.soapdemo.types.EntityGetRequest;
import com.aztec.soapdemo.types.EntityLookupResponse;
import com.aztec.soapdemo.types.EntityResultResponse;
import com.aztec.soapdemo.types.EntityUpdateRequest;

public class WsSoapControllerTest {

	private static final long KEY = 111;
	private static final String VALUE = "value";
	private static final String VALUE_UPDATED = "value_updated";
	private static final long INVALID_KEY = 999;
	
	private EntityService service;
	private WsSoapController controller;
	private EntityCreateRequest entityCreateRequest;
	private EntityUpdateRequest entityUpdateRequest;
	private EntityGetRequest entityGetRequest;
	private EntityDeleteRequest entityDeleteRequest;
	
	@Before
	public void setUp() {
		service = new EntityService();
		controller = new WsSoapController(service);
		
		entityCreateRequest = new EntityCreateRequest();
		entityCreateRequest.setKey(KEY);
		entityCreateRequest.setValue(VALUE);

		entityUpdateRequest = new EntityUpdateRequest();
		entityUpdateRequest.setKey(KEY);
		entityUpdateRequest.setValue(VALUE);
		
		entityGetRequest = new EntityGetRequest();
		entityGetRequest.setKey(KEY);
		
		entityDeleteRequest = new EntityDeleteRequest();
		entityDeleteRequest.setKey(KEY);
	}

	@Test
	public void testCreateEntitySuccess() {
		EntityResultResponse response = controller.createEntity(entityCreateRequest);
		assertNotNull(response);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}

	@Test
	public void testCreateEntityFail_MissingKey() {
		EntityResultResponse response = controller.createEntity(new EntityCreateRequest());
		assertNotNull(response);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
		assertFalse(service.containsKey(KEY));
		assertFalse(service.containsValue(VALUE));
	}

	@Test
	public void testCreateEntityFail_DuplicateCreate() {
		EntityResultResponse response = controller.createEntity(entityCreateRequest);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		response = controller.createEntity(entityCreateRequest);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
	}
	
	@Test
	public void testGetEntitySuccess() {
		controller.createEntity(entityCreateRequest);
		EntityLookupResponse response = controller.getEntity(entityGetRequest);
		assertNotNull(response);
		assertEquals(VALUE, response.getValue());
	}

	@Test
	public void testGetEntityFail() {
		EntityLookupResponse response = controller.getEntity(entityGetRequest);
		assertNotNull(response);
		assertNull(response.getValue());
	}

	@Test
	public void testUpdateEntitySuccess() {
		controller.createEntity(entityCreateRequest);
		EntityUpdateRequest updatedEntity = new EntityUpdateRequest();
		updatedEntity.setKey(KEY);
		updatedEntity.setValue(VALUE_UPDATED);
		EntityResultResponse response = controller.updateEntity(updatedEntity);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE_UPDATED));
		assertFalse(service.containsValue(VALUE));
	}

	@Test
	public void testUpdateEntityFail() {
		controller.createEntity(entityCreateRequest);
		EntityUpdateRequest updatedEntity = new EntityUpdateRequest();
		updatedEntity.setKey(INVALID_KEY);
		updatedEntity.setValue(VALUE_UPDATED);
		EntityResultResponse response = controller.updateEntity(updatedEntity);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
		assertFalse(service.containsValue(VALUE_UPDATED));
	}
	
	@Test
	public void testDeleteEntitySuccess() {
		controller.createEntity(entityCreateRequest);
		EntityResultResponse response = controller.deleteEntity(entityDeleteRequest);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
	}

	@Test
	public void testDeleteEntityFail() {
		controller.createEntity(entityCreateRequest);
		EntityDeleteRequest invalidKeyEntity = new EntityDeleteRequest();
		invalidKeyEntity.setKey(INVALID_KEY);
		EntityResultResponse response = controller.deleteEntity(invalidKeyEntity);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
	}
}
