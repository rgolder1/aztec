package com.aztec.soapdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.aztec.common.constants.AztecConstants;
import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemDeleteRequest;
import com.aztec.common.types.ItemGetRequest;
import com.aztec.common.types.ItemLookupResponse;
import com.aztec.common.types.ItemResultResponse;
import com.aztec.common.types.ItemUpdateRequest;

@Endpoint
public class WsSoapController {

    final static Logger LOG = LoggerFactory.getLogger(WsSoapController.class.getName());
    
    // The namespace of both request and response as declared in the XSD file
    private static final String NAMESPACE_URI = "http://www.aztec.com/common/types";

    // The local name of the expected request.
    private static final String REQUEST_CREATE_NAME = "ItemCreateRequest";
    private static final String REQUEST_GET_NAME = "ItemGetRequest";
    private static final String REQUEST_UPDATE_NAME = "ItemUpdateRequest";
    private static final String REQUEST_DELETE_NAME = "ItemDeleteRequest";
        
    private ItemService service;
    
    @Autowired
    public WsSoapController(ItemService service) {
    	this.service = service;
    }
    
    @PayloadRoot(localPart = REQUEST_CREATE_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public ItemResultResponse createItem(@RequestPayload ItemCreateRequest request) {
    	LOG.info("WsSoapController hit.  createItem()");
    	ItemResultResponse response = new ItemResultResponse();
    	if(request.getKey()!=0 && request.getValue()!=null && !service.containsKey(request.getKey())) {
	    	service.put(request.getKey(), request.getValue());
	    	response.setResult(AztecConstants.RESPONSE_SUCCESS);
    	} else {
	    	response.setResult(AztecConstants.RESPONSE_FAILED);

    	}
    	return response;
    }
    
    @PayloadRoot(localPart = REQUEST_GET_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public ItemLookupResponse getItem(@RequestPayload ItemGetRequest request) {
    	LOG.info("WsSoapController hit.  getItem()");
    	ItemLookupResponse response = new ItemLookupResponse();
    	String value = null;
    	if(request.getKey()!=0 ) {
    		value = service.get(request.getKey());
    	}
    	response.setValue(value);
    	return response;
    }

    @PayloadRoot(localPart = REQUEST_UPDATE_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public ItemResultResponse updateItem(@RequestPayload ItemUpdateRequest request) {
    	LOG.info("WsSoapController hit.  updateItem()");
    	ItemResultResponse response = new ItemResultResponse();
    	if(request.getKey()!=0 && request.getValue()!=null && service.containsKey(request.getKey())) {
	    	service.put(request.getKey(), request.getValue());
	    	response.setResult(AztecConstants.RESPONSE_SUCCESS);
    	} else {
	    	response.setResult(AztecConstants.RESPONSE_FAILED);
    	}
    	return response;
    }

    @PayloadRoot(localPart = REQUEST_DELETE_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public ItemResultResponse deleteItem(@RequestPayload ItemDeleteRequest request) {
    	LOG.info("WsSoapController hit.  deleteItem()");
    	ItemResultResponse response = new ItemResultResponse();
    	if(request.getKey()!=0 && service.containsKey(request.getKey())) {
	    	service.remove(request.getKey());
	    	response.setResult(AztecConstants.RESPONSE_SUCCESS);
    	} else {
	    	response.setResult(AztecConstants.RESPONSE_FAILED);
    	}
    	return response;
    }
}
