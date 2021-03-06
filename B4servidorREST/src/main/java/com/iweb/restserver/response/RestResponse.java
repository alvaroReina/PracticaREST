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
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author jose
 */
public class RestResponse {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
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
    
    
    
    public RestResponse withAttribute(String k, Object v) {
        this.attributes.put(k, v);
        return this;
    }
    
    public RestResponse withStatus(Response.Status status) {
        this.status = status;
        return this;
    }
    
    public RestResponse withComposedAttribute(Attribute attrb) {
        this.attributes.put(attrb.getName(), attrb.pack());
        return this;
    } 
        
    public Response build() {
        
        this.attributes.put("ok", this.ok);
        
        String json;
        try {
            json = MAPPER.writeValueAsString(this.attributes);
        } catch (JsonProcessingException ex) {
            
            Logger.getLogger(RestResponse.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(RestResponse.class.getName())
                    .log(Level.SEVERE, "SUMARY: There was a problem with the JSON parsing while building this response");
            return Response.serverError().build();
        }
        
        return Response
                .status(this.status)
                .encoding(MediaType.APPLICATION_JSON)
                .entity(json)
                .build();
    }
    
    
}
