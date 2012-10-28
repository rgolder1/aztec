package com.aztec.jaxrsdemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemLookupResponse;
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
		
	}

	@Test
	public void testCreateItemSuccess() {
		Response response = controller.createItem(itemCreateRequest);
		assertNotNull(response);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals(URI.create(String.valueOf(KEY)), response.getMetadata().getFirst("Location"));
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

	@Test
	public void testGetItemSuccess() {
		controller.createItem(itemCreateRequest);
		Response response = controller.getItem(KEY);
		assertNotNull(response);
		assertNotNull(response.getEntity());
		assertEquals(VALUE, ((ItemLookupResponse)response.getEntity()).getValue());
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetItemFail() {
		Response response = controller.getItem(INVALID_KEY);
		assertNotNull(response);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertNull(response.getEntity());
	}
	
	@Test
	public void testDeleteItemSuccess() {
		controller.createItem(itemCreateRequest);
		Response response = controller.deleteItem(KEY);
		assertNotNull(response);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		response = controller.getItem(KEY);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteItemFail() {
		controller.createItem(itemCreateRequest);
		Response response = controller.deleteItem(INVALID_KEY);
		assertNotNull(response);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUpdateItemSuccess() {
		controller.createItem(itemCreateRequest);
		ItemUpdateRequest updatedItem = new ItemUpdateRequest();
		updatedItem.setKey(KEY);
		updatedItem.setValue(VALUE_UPDATED);
		Response response = controller.updateItem(KEY, updatedItem);
		assertNotNull(response);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUpdateItemFail() {
		ItemUpdateRequest updatedItem = new ItemUpdateRequest();
		updatedItem.setKey(KEY);
		updatedItem.setValue(VALUE_UPDATED);
		Response response = controller.updateItem(KEY, updatedItem);
		assertNotNull(response);
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
