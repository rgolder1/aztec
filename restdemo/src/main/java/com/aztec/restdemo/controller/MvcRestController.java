package com.aztec.restdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriTemplate;

import com.aztec.common.constants.AztecConstants;
import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemLookupResponse;
import com.aztec.common.types.ItemResultResponse;
import com.aztec.common.types.ItemUpdateRequest;

@Controller
@RequestMapping("/mvc/rest")
public class MvcRestController {

    final static Logger LOG = LoggerFactory.getLogger(MvcRestController.class.getName());
    
    private final static String URI_ROOT = "/mvc/rest";
    private final static String URI_ENTITY = "/item";
    
    private ItemService service;
    
    @Autowired
    public MvcRestController(ItemService service) {
    	this.service = service;
    }
    
    @RequestMapping(value=URI_ENTITY, method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ItemResultResponse> createItem(@RequestBody ItemCreateRequest item) {
        LOG.info("MvcRestController hit.  createItem()");
        ResponseEntity<ItemResultResponse> responseEntity = null;
        ItemResultResponse resultResponse = new ItemResultResponse();
        if(item!=null && item.getKey()!=0 && item.getValue()!=null) {
        	service.put(item.getKey(), item.getValue());
        	HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new UriTemplate(URI_ROOT + URI_ENTITY + "/" + item.getKey()).expand());
            resultResponse.setResult(AztecConstants.RESPONSE_SUCCESS);
        	responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, httpHeaders, HttpStatus.CREATED);	
        } else {
        	resultResponse.setResult(AztecConstants.RESPONSE_FAILED);
        	responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
        }    	

        return responseEntity;
    }
    
    @RequestMapping(value=URI_ENTITY+"/{key}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<ItemLookupResponse> getItem(@PathVariable Long key) {
    	LOG.info("MvcRestController hit.  getItem()");
    	String value = service.get(key);
    	ResponseEntity<ItemLookupResponse> responseEntity = null;
    	if(value!=null) {
    		ItemLookupResponse itemLookupResponse = new ItemLookupResponse();
    		itemLookupResponse.setValue(value);
    		responseEntity = new ResponseEntity<ItemLookupResponse>(itemLookupResponse, HttpStatus.OK);    			
    	} else {
    		// Key not found.
    		responseEntity = new ResponseEntity<ItemLookupResponse>(HttpStatus.NOT_FOUND);
    	}
    	return responseEntity;
    }
    
    @RequestMapping(value=URI_ENTITY+"/{key}", method=RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ItemResultResponse> updateItem(@PathVariable Long key, @RequestBody ItemUpdateRequest item) {
    	LOG.info("MvcRestController hit.  updateItem()");
    	ResponseEntity<ItemResultResponse> responseEntity = null;
        ItemResultResponse resultResponse = new ItemResultResponse();
    	if(item==null || item.getValue()==null) {
    		resultResponse.setResult(AztecConstants.RESPONSE_FAILED);
        	responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);    		
    	}
    	if(service.containsKey(key)) {
    		service.put(key, item.getValue());
    		resultResponse.setResult(AztecConstants.RESPONSE_SUCCESS);
    		responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, HttpStatus.OK);
    	} else {
    		// Key not found.
    		resultResponse.setResult(AztecConstants.RESPONSE_FAILED);
    		responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, HttpStatus.NOT_FOUND);
    	}
    	return responseEntity;
    }    
    
    @RequestMapping(value=URI_ENTITY+"/{key}", method=RequestMethod.DELETE)
    public ResponseEntity<ItemResultResponse> deleteItem(@PathVariable Long key) {
    	LOG.info("MvcRestController hit.  deleteItem()");
    	ResponseEntity<ItemResultResponse> responseEntity = null;
        ItemResultResponse resultResponse = new ItemResultResponse();
    	if(service.containsKey(key)) {
    		service.remove(key);
    		resultResponse.setResult(AztecConstants.RESPONSE_SUCCESS);
    		responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, HttpStatus.OK);
    	} else {
    		// Key not found.
    		resultResponse.setResult(AztecConstants.RESPONSE_FAILED);
    		responseEntity = new ResponseEntity<ItemResultResponse>(resultResponse, HttpStatus.NOT_FOUND);
    	}
    	return responseEntity;
    }
}
