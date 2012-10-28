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

import com.aztec.common.constants.AztecConstants;
import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemDeleteRequest;
import com.aztec.common.types.ItemGetRequest;
import com.aztec.common.types.ItemLookupResponse;
import com.aztec.common.types.ItemResultResponse;
import com.aztec.common.types.ItemUpdateRequest;

public class MvcRestControllerTest {
	
	private static final long KEY = 111;
	private static final String VALUE = "value";
	private static final String VALUE_UPDATED = "value_updated";
	private static final long INVALID_KEY = 999;
	
	private static final String URI_CREATED = "/mvc/rest/item/";

	private ItemService service;
	private MvcRestController controller;
	private ItemCreateRequest itemCreateRequest;
	private ItemUpdateRequest itemUpdateRequest;
	private ItemGetRequest itemGetRequest;
	private ItemDeleteRequest itemDeleteRequest;
	
	@Before
	public void setUp() {
		service = new ItemService();
		controller = new MvcRestController(service);

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
		ResponseEntity<ItemResultResponse> response = controller.createItem(itemCreateRequest);
		assertNotNull(response);
		assertEquals(URI.create(URI_CREATED+KEY), response.getHeaders().getLocation());
		assertEquals(AztecConstants.RESPONSE_SUCCESS, response.getBody().getResult());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(1, service.size());
		assertTrue(service.containsKey(KEY));
		assertTrue(service.containsValue(VALUE));
	}
	
	@Test
	public void testCreateItemFail() {
		ResponseEntity<ItemResultResponse> response = controller.createItem(new ItemCreateRequest());
		assertNotNull(response);
		assertEquals(AztecConstants.RESPONSE_FAILED, response.getBody().getResult());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(service.isEmpty());
	}
	
	@Test
	public void testGetItemSuccess() {
		controller.createItem(itemCreateRequest);
		ResponseEntity<ItemLookupResponse> response = controller.getItem(KEY);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(VALUE, response.getBody().getValue());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetItemFail() {
		ResponseEntity<ItemLookupResponse> response = controller.getItem(INVALID_KEY);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	public void testDeleteItemSuccess() {
		controller.createItem(itemCreateRequest);
		ResponseEntity<ItemResultResponse> response = controller.deleteItem(KEY);
		assertNotNull(response);
		assertEquals(AztecConstants.RESPONSE_SUCCESS, response.getBody().getResult());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ResponseEntity<ItemLookupResponse> responseFromGet = controller.getItem(INVALID_KEY);
		assertEquals(HttpStatus.NOT_FOUND, responseFromGet.getStatusCode());
	}

	@Test
	public void testDeleteItemFail() {
		controller.createItem(itemCreateRequest);
		ResponseEntity<ItemResultResponse> response = controller.deleteItem(INVALID_KEY);
		assertNotNull(response);
		assertEquals(AztecConstants.RESPONSE_FAILED, response.getBody().getResult());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void testUpdateItemSuccess() {
		controller.createItem(itemCreateRequest);
		ItemUpdateRequest updatedItem = new ItemUpdateRequest();
		updatedItem.setKey(KEY);
		updatedItem.setValue(VALUE_UPDATED);
		ResponseEntity<ItemResultResponse> response = controller.updateItem(KEY, updatedItem);
		assertNotNull(response);
		assertEquals(AztecConstants.RESPONSE_SUCCESS, response.getBody().getResult());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testUpdateItemFail() {
		ItemUpdateRequest updatedItem = new ItemUpdateRequest();
		updatedItem.setKey(KEY);
		updatedItem.setValue(VALUE_UPDATED);
		ResponseEntity<ItemResultResponse> response = controller.updateItem(KEY, updatedItem);
		assertNotNull(response);
		assertEquals(AztecConstants.RESPONSE_FAILED, response.getBody().getResult());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
