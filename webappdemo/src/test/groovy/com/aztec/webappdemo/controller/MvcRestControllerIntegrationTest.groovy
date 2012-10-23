package com.aztec.webappdemo.controller;

import groovy.util.GroovyTestCase;

class MvcRestControllerIntegrationTest extends GroovyTestCase {
	
	def URL = "http://localhost:9090/webappdemo/mvc/rest/";
	
	/**
	 * Create an entity.  201 CREATED expected.
	 */
	def void test_CreateEntity() {
		long key = 111;
		String value = "value";
		
        def body = '{"key": ' + key + ', "value": "' + value + '"}' 
		println("createEntityBody=" + body)
		
		def response = new groovyx.net.http.RESTClient(URL).post(path: "entity/", contentType: groovyx.net.http.ContentType.JSON, body: body)

		assertEquals(201, response.status)

		/* check that the resource location is returned */
		assertTrue(response.headers["Location"].getValue().contains("/mvc/rest/entity/"+key))
	}
}