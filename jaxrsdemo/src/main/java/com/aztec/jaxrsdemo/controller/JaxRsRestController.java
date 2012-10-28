package com.aztec.jaxrsdemo.controller;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aztec.common.service.ItemService;
import com.aztec.common.types.ItemCreateRequest;
import com.aztec.common.types.ItemDeleteRequest;
import com.aztec.common.types.ItemLookupResponse;
import com.aztec.common.types.ItemUpdateRequest;

@Path("item")
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class JaxRsRestController {

	private ItemService service;
    
    @Autowired
    public JaxRsRestController(ItemService service) {
    	this.service = service;
    }
	
	@POST
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
	
	@GET
	@Path("/{key}")
	public Response getItem(@PathParam("key") Long key) {
		Response response = null;
		if(key!=0) {
			String value = service.get(key);
			if(value!=null) {
				ItemLookupResponse itemLookupResponse = new ItemLookupResponse();
				itemLookupResponse.setValue(value);
				response = Response.ok(itemLookupResponse).build();
			} else {
				response  = Response.status(Status.NOT_FOUND).build();
			}
		} else {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}
	
	@PUT
	@Path("/{key}")
	public Response updateItem(@PathParam("key") Long key, ItemUpdateRequest request) {
		Response response = null;
		if(request==null || key==0 || request.getValue()==null) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else if(service.containsKey(key)){
			service.put(key, request.getValue());
			response = Response.ok().build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}
	
	@DELETE
	@Path("/{key}")
	public Response deleteItem(@PathParam("key") Long key) {
		Response response = null;
		if(key==0) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else if(service.containsKey(key)) {
			service.remove(key);
			response = Response.ok().build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}
}
