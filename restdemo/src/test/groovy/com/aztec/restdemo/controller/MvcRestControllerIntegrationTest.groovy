package com.aztec.restdemo.controller;

import groovy.util.GroovyTestCase;

import groovyx.net.http.HttpResponseException;
import com.aztec.util.MarshallingUtil;
import com.aztec.restdemo.types.Entity;

class MvcRestControllerIntegrationTest extends GroovyTestCase {
	
	def URL = "http://localhost:9090/restdemo/";
	def PATH = "mvc/rest/entity/";
	
	/**
	 * Create an entity.  201 CREATED expected.
	 */
	def void test_CreateEntity() {
		long key = 111;
		String value = "value";
		
        def body = '{"key": ' + key + ', "value": "' + value + '"}' 
		println("createEntityBody=" + body)
		
		def response = new groovyx.net.http.RESTClient(URL).post(path: PATH, contentType: groovyx.net.http.ContentType.JSON, body: body)

		assertEquals(201, response.status)

		/* check that the resource location is returned */
		assertTrue(response.headers["Location"].getValue().contains(PATH+key))
	}
	
	/**
	 * Get an entity. 200 OK expected.
	 */
	def void testGetEntity() {
		// First create the entity.
		long key = 111;
		String value = "value";
		def body = '{"key": ' + key + ', "value": "' + value + '"}'		
		def responseFromCreate = new groovyx.net.http.RESTClient(URL).post(path: PATH, contentType: groovyx.net.http.ContentType.JSON, body: body)
		assertEquals(201, responseFromCreate.status)
		
		// Now test the GET.
		def currentUrl = responseFromCreate.headers["Location"].getValue()
		currentUrl = currentUrl.substring(1)
		println "currentUrl:" + currentUrl
		def responseFromGet = new groovyx.net.http.RESTClient(URL).get(path: currentUrl);
		assertEquals(200, responseFromGet.status)
		Entity entity = MarshallingUtil.unmarshalJson(responseFromGet.data.toString(), Entity.class);
		assertEquals(key, entity.getKey())
		assertEquals(value, entity.getValue())
	}
	
	/**
	 * Update an entity.  200 OK expected.
	 */
	def void testUpdateEntity() {
		// First create the entity.
		long key = 111;
		String value = "value";
		def body = '{"key": ' + key + ', "value": "' + value + '"}'
		def responseFromCreate = new groovyx.net.http.RESTClient(URL).post(path: PATH, contentType: groovyx.net.http.ContentType.JSON, body: body)
		assertEquals(201, responseFromCreate.status)
		
		// Now test the UPDATE.
		def currentUrl = responseFromCreate.headers["Location"].getValue()
		currentUrl = currentUrl.substring(1)
		String valueUpdated = "value_updated";
		body = '{"key": ' + key + ', "value": "' + valueUpdated + '"}'
		def responseFromUpdate = new groovyx.net.http.RESTClient(URL).put(path: currentUrl, contentType: groovyx.net.http.ContentType.JSON, body: body)
		assertEquals(200, responseFromUpdate.status)
		
		// GET the updated record to assert.
		def responseFromGet = new groovyx.net.http.RESTClient(URL).get(path: currentUrl);
		assertEquals(200, responseFromGet.status)
		Entity entity = MarshallingUtil.unmarshalJson(responseFromGet.data.toString(), Entity.class);
		assertEquals(key, entity.getKey())
		assertEquals(valueUpdated, entity.getValue())
	}
	
	/**
	 * Delete an entity.  200 OK expected.
	 */
	def void testDeleteEntity() {
		// First create the entity.
		long key = 111;
		String value = "value";
		def body = '{"key": ' + key + ', "value": "' + value + '"}'
		def responseFromCreate = new groovyx.net.http.RESTClient(URL).post(path: PATH, contentType: groovyx.net.http.ContentType.JSON, body: body)
		assertEquals(201, responseFromCreate.status)
		
		// Assert the entity is found.
		def currentUrl = responseFromCreate.headers["Location"].getValue()
		currentUrl = currentUrl.substring(1)
		def responseFromGet = new groovyx.net.http.RESTClient(URL).get(path: currentUrl);
		assertEquals(200, responseFromGet.status)
		
		// Now DELETE the entity.
		def responseFromDelete = new groovyx.net.http.RESTClient(URL).delete(path: currentUrl)
		assertEquals(200, responseFromDelete.status)
		
		// Assert the entity is now not found.
		try {
			def responseFromNextGet = new groovyx.net.http.RESTClient(URL).get(path: currentUrl);
			fail("Exception should be thrown.");
		} catch(HttpResponseException e){
			assertEquals(404, e.getResponse().status);
		}
	}
}