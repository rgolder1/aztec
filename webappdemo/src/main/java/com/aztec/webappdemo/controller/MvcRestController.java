package com.aztec.webappdemo.controller;

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

import com.aztec.webappdemo.service.EntityService;
import com.aztec.webappdemo.types.Entity;

@Controller
@RequestMapping("/mvc/rest")
public class MvcRestController {

    final static Logger LOG = LoggerFactory.getLogger(MvcRestController.class.getName());
    
    private final static String URI_ROOT = "/mvc/rest";
    private final static String URI_ENTITY = "/entity";
    
    private EntityService service;
    
    @Autowired
    public MvcRestController(EntityService service) {
    	this.service = service;
    }
    
    @RequestMapping(value=URI_ENTITY, method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<String> createEntity(@RequestBody Entity entity) {
        LOG.info("MvcRestController hit.  createEntity()");
        ResponseEntity<String> responseEntity = null;
        if(entity!=null && entity.getKey()!=0 && entity.getValue()!=null) {
        	service.put(entity.getKey(), entity.getValue());
        	HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new UriTemplate(URI_ROOT + URI_ENTITY + "/" + entity.getKey()).expand());
        	responseEntity = new ResponseEntity<String>(httpHeaders, HttpStatus.CREATED);	
        } else {
        	responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }    	

        return responseEntity;
    }
    
    @RequestMapping(value=URI_ENTITY+"/{key}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<Entity> getEntity(@PathVariable Long key) {
    	LOG.info("MvcRestController hit.  getEntity()");
    	String value = service.get(key);
    	ResponseEntity<Entity> responseEntity = null;
    	if(value!=null) {
        	Entity entity = new Entity();
        	entity.setKey(key);
        	entity.setValue(value);
    		responseEntity = new ResponseEntity<Entity>(entity, HttpStatus.OK);    			
    	} else {
    		// Key not found.
    		responseEntity = new ResponseEntity<Entity>(HttpStatus.NOT_FOUND);
    	}
    	return responseEntity;
    }
    
    @RequestMapping(value=URI_ENTITY+"/{key}", method=RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<String> updateEntity(@PathVariable Long key, @RequestBody Entity entity) {
    	LOG.info("MvcRestController hit.  updateEntity()");
    	ResponseEntity<String> responseEntity = null;
    	if(entity==null || entity.getValue()==null) {
        	responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);    		
    	}
    	if(service.containsKey(key)) {
    		service.put(key, entity.getValue());
    		responseEntity = new ResponseEntity<String>(HttpStatus.OK);
    	} else {
    		// Key not found.
    		responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    	}
    	return responseEntity;
    }    
    
    @RequestMapping(value=URI_ENTITY+"/{key}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteEntity(@PathVariable Long key) {
    	LOG.info("MvcRestController hit.  deleteEntity()");
    	ResponseEntity<String> responseEntity = null;
    	if(service.containsKey(key)) {
    		service.remove(key);
    		responseEntity = new ResponseEntity<String>(HttpStatus.OK);
    	} else {
    		// Key not found.
    		responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    	}
    	return responseEntity;
    }
}
