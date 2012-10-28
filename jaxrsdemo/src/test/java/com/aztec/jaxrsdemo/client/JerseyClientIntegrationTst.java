package com.aztec.jaxrsdemo.client;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.aztec.common.types.ItemLookupResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientIntegrationTst {

	private final static String WEB_APP_URL = "http://localhost:8080/jaxrsdemo/item/";
	
	private final static Long KEY = 111l;
	private final static String VALUE = "test value";
	private final static String UPDATED_VALUE = "test updated value";
	private final static String POST_REQUEST = "{\"key\":"+KEY+",\"value\":\""+VALUE+"\"}";
	private final static String UPDATE_REQUEST = "{\"key\":"+KEY+",\"value\":\""+UPDATED_VALUE+"\"}";
	
	@Test
	public void testJaxRsDemo() {
		Client client = Client.create();
		WebResource webResource = client.resource(WEB_APP_URL);
		
		// 1. Create Item.
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, POST_REQUEST);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(URI.create(WEB_APP_URL+KEY), response.getLocation());
		URI location = response.getLocation();

		// 2. Get Item.
		response = webResource.uri(response.getLocation()).get(ClientResponse.class);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		ItemLookupResponse itemLookupResponse = response.getEntity(ItemLookupResponse.class);
		assertEquals(VALUE, itemLookupResponse.getValue());

		// 3. Update Item.
		response = webResource.uri(location).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, UPDATE_REQUEST);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		// 4. Get Updated Item.
		response = webResource.uri(location).get(ClientResponse.class);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		itemLookupResponse = response.getEntity(ItemLookupResponse.class);
		assertEquals(UPDATED_VALUE, itemLookupResponse.getValue());

		// 5. Create Duplicated Item.
		response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, POST_REQUEST);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		// 6. Delete Item.
		response = webResource.uri(location).delete(ClientResponse.class);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		// 7. Get Deleted Item.
		response = webResource.uri(location).get(ClientResponse.class);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		
		// 8. Delete Deleted Item.
		response = webResource.uri(location).delete(ClientResponse.class);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

		// 9. Update Deleted Item.
		response = webResource.uri(location).get(ClientResponse.class);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
}
