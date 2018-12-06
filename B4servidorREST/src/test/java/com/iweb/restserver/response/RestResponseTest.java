/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.response;

import javax.ws.rs.core.Response.Status;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jose
 */
public class RestResponseTest {
    
    public RestResponseTest() {
    }
    
 

    /**
     * Test of isSuccessful method, of class RestResponse.
     */
    @org.junit.Test
    public void testIsSuccessful() {
        System.out.println("isSuccessful");
        
        RestResponse instance = new RestResponse(false);
        assertFalse(instance.ok);
        assertEquals(instance.status, Status.INTERNAL_SERVER_ERROR); 
        instance.isSuccessful(true);
        assertTrue(instance.ok);
        
        //Must use withStatus
        assertEquals(instance.status, Status.INTERNAL_SERVER_ERROR); 
        
        instance = new RestResponse(true);
        assertTrue(instance.ok);
        assertEquals(instance.status, Status.OK);
       
        instance.withStatus(Status.NOT_FOUND).isSuccessful(false);
        assertFalse(instance.ok);
        assertEquals(instance.status, Status.NOT_FOUND);
    }

    @Test 
    public void testAttributes() {
        System.out.println("testAttributes");
        
        RestResponse r = new RestResponse(true);
        String key = "message", val = "fido";
        r.withStatus(Status.CREATED).withAttribute(key,val);
        
        assertEquals(Status.CREATED, r.status);
        assertTrue(r.ok);
        assertTrue(r.attributes.containsKey(key));
        assertEquals(val, (String) r.attributes.get(key));
    }
    
    @Test
    public void overrideAttributes() {
        System.out.println("overrideAttributes");
        
        RestResponse r = new RestResponse(true);
        String key = "message", v1 = "A", v2 ="B";
        r.withAttribute(key, v1);
        assertTrue(r.attributes.containsKey(key));
        assertEquals(v1, (String) r.attributes.get(key));
        r.withAttribute(key, v2);
        assertTrue(r.attributes.containsKey(key));
        assertEquals(v2, (String) r.attributes.get(key));
    }
   
}
