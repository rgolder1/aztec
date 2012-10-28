package com.aztec.jaxrsdemo.client;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientIntegrationTst {

	private final static String WEB_APP_URL = "http://localhost:8080/jaxrsdemo/item";
	
	private final static Long KEY = 111l;
	private final static String VALUE = "test value";
	private final static String POST_REQUEST = "{\"key\":"+KEY+",\"value\":\""+VALUE+"\"}";
	
	@Test
	public void testJaxRsDemo() {
		Client client = Client.create();
		WebResource webResource = client.resource(WEB_APP_URL);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, POST_REQUEST);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
}
