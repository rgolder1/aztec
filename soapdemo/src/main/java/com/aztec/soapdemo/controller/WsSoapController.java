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
import com.aztec.soapdemo.types.EntityDeleteRequest;
import com.aztec.soapdemo.types.EntityGetRequest;
import com.aztec.soapdemo.types.EntityLookupResponse;
import com.aztec.soapdemo.types.EntityResultResponse;
import com.aztec.soapdemo.types.EntityUpdateRequest;

@Endpoint
public class WsSoapController {

    final static Logger LOG = LoggerFactory.getLogger(WsSoapController.class.getName());
    
    // The namespace of both request and response as declared in the XSD file
    private static final String NAMESPACE_URI = "http://www.aztec.com/soapdemo/types";

    // The local name of the expected request.
    private static final String REQUEST_CREATE_NAME = "EntityCreateRequest";
    private static final String REQUEST_GET_NAME = "EntityGetRequest";
    private static final String REQUEST_UPDATE_NAME = "EntityUpdateRequest";
    private static final String REQUEST_DELETE_NAME = "EntityDeleteRequest";
    
    public static final String RESPONSE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_FAILED = "FAILED";
        
    private EntityService service;
    
    @Autowired
    public WsSoapController(EntityService service) {
    	this.service = service;
    }
    
    @PayloadRoot(localPart = REQUEST_CREATE_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public EntityResultResponse createEntity(@RequestPayload EntityCreateRequest request) {
    	LOG.info("WsSoapController hit.  createEntity()");
    	EntityResultResponse response = new EntityResultResponse();
    	if(request.getKey()!=0 && request.getValue()!=null && !service.containsKey(request.getKey())) {
	    	service.put(request.getKey(), request.getValue());
	    	response.setResult(RESPONSE_SUCCESS);
    	} else {
	    	response.setResult(RESPONSE_FAILED);

    	}
    	return response;
    }
    
    @PayloadRoot(localPart = REQUEST_GET_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public EntityLookupResponse getEntity(@RequestPayload EntityGetRequest request) {
    	LOG.info("WsSoapController hit.  getEntity()");
    	EntityLookupResponse response = new EntityLookupResponse();
    	String value = null;
    	if(request.getKey()!=0 ) {
    		value = service.get(request.getKey());
    	}
    	response.setValue(value);
    	return response;
    }

    @PayloadRoot(localPart = REQUEST_UPDATE_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public EntityResultResponse updateEntity(@RequestPayload EntityUpdateRequest request) {
    	LOG.info("WsSoapController hit.  updateEntity()");
    	EntityResultResponse response = new EntityResultResponse();
    	if(request.getKey()!=0 && request.getValue()!=null && service.containsKey(request.getKey())) {
	    	service.put(request.getKey(), request.getValue());
	    	response.setResult(RESPONSE_SUCCESS);
    	} else {
	    	response.setResult(RESPONSE_FAILED);
    	}
    	return response;
    }

    @PayloadRoot(localPart = REQUEST_DELETE_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public EntityResultResponse deleteEntity(@RequestPayload EntityDeleteRequest request) {
    	LOG.info("WsSoapController hit.  deleteEntity()");
    	EntityResultResponse response = new EntityResultResponse();
    	if(request.getKey()!=0 && service.containsKey(request.getKey())) {
	    	service.remove(request.getKey());
	    	response.setResult(RESPONSE_SUCCESS);
    	} else {
	    	response.setResult(RESPONSE_FAILED);
    	}
    	return response;
    }
}
