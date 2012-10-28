package com.aztec.soapdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemDeleteRequest;
import com.aztec.common.types.ItemGetRequest;
import com.aztec.common.types.ItemLookupResponse;
import com.aztec.common.types.ItemResultResponse;
import com.aztec.common.types.ItemUpdateRequest;

public class WsSoapControllerTest {

	private static final long KEY = 111;
	private static final String VALUE = "value";
	private static final String VALUE_UPDATED = "value_updated";
	private static final long INVALID_KEY = 999;
	
	private ItemService service;
	private WsSoapController controller;
	private ItemCreateRequest itemCreateRequest;
	private ItemUpdateRequest itemUpdateRequest;
	private ItemGetRequest itemGetRequest;
	private ItemDeleteRequest itemDeleteRequest;
	
	@Before
	public void setUp() {
		service = new ItemService();
		controller = new WsSoapController(service);
		
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
		ItemResultResponse response = controller.createItem(itemCreateRequest);
		assertNotNull(response);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}

	@Test
	public void testCreateItemFail_MissingKey() {
		ItemResultResponse response = controller.createItem(new ItemCreateRequest());
		assertNotNull(response);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
		assertFalse(service.containsKey(KEY));
		assertFalse(service.containsValue(VALUE));
	}

	@Test
	public void testCreateItemFail_DuplicateCreate() {
		ItemResultResponse response = controller.createItem(itemCreateRequest);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		response = controller.createItem(itemCreateRequest);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
	}
	
	@Test
	public void testGetItemSuccess() {
		controller.createItem(itemCreateRequest);
		ItemLookupResponse response = controller.getItem(itemGetRequest);
		assertNotNull(response);
		assertEquals(VALUE, response.getValue());
	}

	@Test
	public void testGetItemFail() {
		ItemLookupResponse response = controller.getItem(itemGetRequest);
		assertNotNull(response);
		assertNull(response.getValue());
	}

	@Test
	public void testUpdateItemSuccess() {
		controller.createItem(itemCreateRequest);
		ItemUpdateRequest updatedItem = new ItemUpdateRequest();
		updatedItem.setKey(KEY);
		updatedItem.setValue(VALUE_UPDATED);
		ItemResultResponse response = controller.updateItem(updatedItem);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE_UPDATED));
		assertFalse(service.containsValue(VALUE));
	}

	@Test
	public void testUpdateItemFail() {
		controller.createItem(itemCreateRequest);
		ItemUpdateRequest updatedItem = new ItemUpdateRequest();
		updatedItem.setKey(INVALID_KEY);
		updatedItem.setValue(VALUE_UPDATED);
		ItemResultResponse response = controller.updateItem(updatedItem);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
		assertFalse(service.containsValue(VALUE_UPDATED));
	}
	
	@Test
	public void testDeleteItemSuccess() {
		controller.createItem(itemCreateRequest);
		ItemResultResponse response = controller.deleteItem(itemDeleteRequest);
		assertEquals(WsSoapController.RESPONSE_SUCCESS, response.getResult());
	}

	@Test
	public void testDeleteItemFail() {
		controller.createItem(itemCreateRequest);
		ItemDeleteRequest invalidKeyItem = new ItemDeleteRequest();
		invalidKeyItem.setKey(INVALID_KEY);
		ItemResultResponse response = controller.deleteItem(invalidKeyItem);
		assertEquals(WsSoapController.RESPONSE_FAILED, response.getResult());
	}
}