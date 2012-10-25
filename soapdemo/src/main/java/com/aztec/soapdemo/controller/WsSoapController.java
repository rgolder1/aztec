package com.aztec.soapdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.aztec.soapdemo.service.EntityService;
import com.aztec.soapdemo.types.EntityCreateRequest;
import com.aztec.soapdemo.types.EntityCreateResponse;

@Endpoint
public class WsSoapController {

    final static Logger LOG = LoggerFactory.getLogger(WsSoapController.class.getName());
    
    // The namespace of both request and response as declared in the XSD file
    public static final String NAMESPACE_URI = "http://www.aztec.com/soapdemo/types";

    // The local name of the expected request.
    public static final String REQUEST_LOCAL_NAME = "EntityCreateRequest";
        
    private EntityService service;
    
    @Autowired
    public WsSoapController(EntityService service) {
    	this.service = service;
    }
    
    @PayloadRoot(localPart = REQUEST_LOCAL_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public EntityCreateResponse createEntityValue(@RequestPayload EntityCreateRequest request) {
    	LOG.info("WsSoapController hit.  getEntityValue()");
    	String value = service.put(request.getKey(), request.getValue());
    	EntityCreateResponse response = null;
    	if(value!=null) {
        	response = new EntityCreateResponse();
        	response.setKey(request.getKey());  
        	response.setValue(value);    			
    	}
    	return response;
    }
}
