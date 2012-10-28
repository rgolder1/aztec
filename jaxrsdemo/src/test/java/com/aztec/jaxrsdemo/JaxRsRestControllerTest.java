package com.aztec.jaxrsdemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemDeleteRequest;
import com.aztec.common.types.ItemGetRequest;
import com.aztec.common.types.ItemUpdateRequest;
import com.aztec.jaxrsdemo.controller.JaxRsRestController;

public class JaxRsRestControllerTest {
	private static final long KEY = 111;
	private static final String VALUE = "value";
	private static final String VALUE_UPDATED = "value_updated";
	private static final long INVALID_KEY = 999;
	
	private ItemService service;
	private JaxRsRestController controller;
	private ItemCreateRequest itemCreateRequest;
	private ItemUpdateRequest itemUpdateRequest;
	private ItemGetRequest itemGetRequest;
	private ItemDeleteRequest itemDeleteRequest;
	
	@Before
	public void setUp() {
		service = new ItemService();
		controller = new JaxRsRestController(service);
		
		itemCreateRequest = new ItemCreateRequest();
		itemCreateRequest.setKey(KEY);
		itemCreateRequest.setValue(VALUE);

		itemUpdateRequest = new ItemUpdateRequest();
		itemUpdateRequest.setKey(KEY);
		itemUpdateRequest.setValue(VALUE);
		
		itemGetRequest = new ItemGetRequest();
		itemGetRequest.setKey(KEY);
		
		itemDeleteRequest = new ItemDeleteRequest();
		itemDeleteRequest.setKey(KEY);
	}

	@Test
	public void testCreateItemSuccess() {
		Response response = controller.createItem(itemCreateRequest);
		assertNotNull(response);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}

	@Test
	public void testCreateItemFail_MissingKey() {
		Response response = controller.createItem(new ItemCreateRequest());
		assertNotNull(response);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertFalse(service.containsKey(KEY));
		assertFalse(service.containsValue(VALUE));
	}

	@Test
	public void testCreateItemFail_DuplicateCreate() {
		Response response = controller.createItem(itemCreateRequest);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		response = controller.createItem(itemCreateRequest);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
}
