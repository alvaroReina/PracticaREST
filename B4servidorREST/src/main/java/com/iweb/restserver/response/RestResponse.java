/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author jose
 */
public class RestResponse {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    private static final ResponseBuilder INTERNAL_PROCESSING_ERROR;
        
    static {
        
        INTERNAL_PROCESSING_ERROR = 
            Response.serverError()
                .encoding(MediaType.APPLICATION_JSON)
                .entity(String.format("{%s: {%s}}", "\"error\"", 
                        "\"cause\": \"The server could not process the responseS\""));       
    }
    
    
    protected boolean ok;
    protected Map<String,Object> attributes;
    protected Status status;
    
    public RestResponse (boolean ok) {
        this.attributes = new TreeMap<>();
        this.ok = ok;
        this.status = ok ? Status.OK : Status.INTERNAL_SERVER_ERROR;
    }

    public RestResponse isSuccessful(boolean ok) {
        this.ok = ok;
        return this;
    }
    
    
    
    RestResponse withAttribute(String k, Object v) {
        this.attributes.put(k, v);
        return this;
    }
    
    RestResponse withStatus(Response.Status status) {
        this.status = status;
        return this;
    }
    
    RestResponse withComposedAttribute(Attribute attrb) {
        this.attributes.put(attrb.getName(), attrb.pack());
        return this;
    } 
        
    public Response build() {
        
        
        String json;
        try {
            json = MAPPER.writeValueAsString(this.attributes);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestResponse.class.getName()).log(Level.SEVERE, null, ex);
            return INTERNAL_PROCESSING_ERROR.build();
        }
        
        return Response
                .status(this.status)
                .encoding(MediaType.APPLICATION_JSON)
                .entity(json)
                .build();
    }
    
    
}
