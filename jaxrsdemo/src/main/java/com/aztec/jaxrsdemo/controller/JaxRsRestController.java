package com.aztec.jaxrsdemo.controller;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;

@Path("item")
@Component
public class JaxRsRestController {

	private ItemService service;

    @Autowired
    public JaxRsRestController(ItemService service) {
    	this.service = service;
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createItem(ItemCreateRequest request) {
		Response response = null;
		if(request.getKey()!=0 && request.getValue()!=null && !service.containsKey(request.getKey())) {
			service.put(request.getKey(), request.getValue());
			response = Response.created(URI.create(String.valueOf(request.getKey()))).build(); 
		} else {
			response = Response.status(Status.BAD_REQUEST).build();
		}
        return response;
	}
}
