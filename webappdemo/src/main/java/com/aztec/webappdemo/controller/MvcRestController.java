package com.aztec.webappdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriTemplate;

import com.aztec.webappdemo.service.EntityService;
import com.aztec.webappdemo.types.EntityCreation;

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
    
    @RequestMapping(value = URI_ENTITY, method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createEntity(@RequestBody EntityCreation entityCreation) {
    	service.put(entityCreation.getKey(), entityCreation.getValue());
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new UriTemplate(URI_ROOT + URI_ENTITY + "/" + entityCreation.getKey()).expand());

        return new ResponseEntity<String>(null, httpHeaders, HttpStatus.CREATED);
    }
}
