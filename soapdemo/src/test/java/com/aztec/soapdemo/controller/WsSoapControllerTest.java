package com.aztec.soapdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aztec.soapdemo.service.EntityService;
import com.aztec.soapdemo.types.EntityCreateRequest;
import com.aztec.soapdemo.types.EntityCreateResponse;

public class WsSoapControllerTest {

	private static final long KEY = 111;
	private static final String VALUE = "value";
	
	private EntityService service;
	private WsSoapController controller;
	private EntityCreateRequest entityCreateRequest;
	
	@Before
	public void setUp() {
		service = new EntityService();
		controller = new WsSoapController(service);
		
		entityCreateRequest = new EntityCreateRequest();
		entityCreateRequest.setKey(KEY);
		entityCreateRequest.setValue(VALUE);
	}

	@Test
	public void testCreateEntitySuccess() {
		EntityCreateResponse response = controller.createEntity(entityCreateRequest);
		assertNotNull(response);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}

	@Test
	public void testCreateEntityFail() {
		EntityCreateResponse response = controller.createEntity(new EntityCreateRequest());
		assertNotNull(response);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
		assertFalse(service.containsKey(KEY));
		assertFalse(service.containsValue(VALUE));
	}
}
