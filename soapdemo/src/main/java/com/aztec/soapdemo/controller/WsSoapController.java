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
//@RequestMapping("/mvc/soap")
public class WsSoapController {

    final static Logger LOG = LoggerFactory.getLogger(WsSoapController.class.getName());
    
    private final static String URI_ROOT = "/ws/soap";
    private final static String URI_ENTITY = "/entity";
    
    // The namespace of both request and response as declared in the XSD file
    public static final String NAMESPACE_URI = "http://www.aztec.com/soapdemo/types";
        
    private EntityService service;
    
    @Autowired
    public WsSoapController(EntityService service) {
    	this.service = service;
    }
    
    @PayloadRoot(localPart = "createEntity", namespace = NAMESPACE_URI)
    @ResponsePayload
    public EntityCreateResponse getEntityValue(@RequestPayload EntityCreateRequest request) {
    	LOG.info("WsSoapController hit.  getEntityValue()");
    	String value = service.get(request.getKey());
    	EntityCreateResponse response = null;
    	if(value!=null) {
        	response = new EntityCreateResponse();
        	response.setValue(value);    			
    	}
    	return response;
    }
}
