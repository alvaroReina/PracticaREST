/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.fusionauth.jwt.json.Mapper;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author jose
 */
public class ResponseFactory {

    private static ResponseBuilder AUTH_ERR = Response.status(Response.Status.UNAUTHORIZED);
    private static ResponseBuilder NOT_FOUND_ERR = Response.status(Response.Status.NOT_FOUND);
    
    private static final ObjectMapper mapper = new ObjectMapper();
   
    
    public static Response authenticationFailed(String details) throws JsonProcessingException {
        if (details == null) {
            details = "You must be authenticated";
        }
        
        Map<String, Object> map = new HashMap<>();
        map.put("ok", false);
        map.put("details", details);

        String resp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        return AUTH_ERR.entity(resp).build();
    }
}
