/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.response;

import com.iweb.restserver.entity.Userinfo;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response.Status;
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
        r.withStatus(Status.CREATED).withAttribute(key, val);

        assertEquals(Status.CREATED, r.status);
        assertTrue(r.ok);
        assertTrue(r.attributes.containsKey(key));
        assertEquals(val, (String) r.attributes.get(key));
    }

    @Test
    public void overrideAttributes() {
        System.out.println("overrideAttributes");

        RestResponse r = new RestResponse(true);
        String key = "message", v1 = "A", v2 = "B";
        r.withAttribute(key, v1);
        assertTrue(r.attributes.containsKey(key));
        assertEquals(v1, (String) r.attributes.get(key));
        r.withAttribute(key, v2);
        assertTrue(r.attributes.containsKey(key));
        assertEquals(v2, (String) r.attributes.get(key));
    }

    @Test
    public void composeError() {
        System.out.println("composeError");

        String cause = "invalid params";
        String[] fields = new String[]{"name", "email", "password"};
        String hint = "Fill these fields";

        String defName = "error";
        String name = "err";

        ErrorAttribute errNoName = new ErrorAttribute();
        ErrorAttribute errNamed = new ErrorAttribute(name);

        errNamed.withCause(cause).withFields(fields).withHint(hint);
        errNoName.withCause(cause).withFields(fields).withHint(hint);

        RestResponse def = new RestResponse(false)
                .withComposedAttribute(errNoName)
                .withComposedAttribute(errNamed)
                .withStatus(Status.BAD_REQUEST);

        assertTrue(def.attributes.containsKey(defName));
        assertTrue(def.attributes.containsKey(name));

        Map<String, Object> err = (Map<String, Object>) def.attributes.get(name);
        Map<String, Object> error = (Map<String, Object>) def.attributes.get(defName);

        assertEquals(hint, (String) err.get("hint"));
        assertEquals(cause, (String) err.get("cause"));

        List<Object> list = (List<Object>) err.get("fields");
        assertEquals(fields.length, list.size());
        for (Object o : fields) {
            assertTrue(list.contains(o));
        }

        assertEquals(err, error);
    }
    
    @Test
    public void composeList(){
        
        Userinfo[] elements = new Userinfo[] {
            new Userinfo(0, "pepe","pepe@mail", null, null),
            new Userinfo(1, "fido", "fido@mail", null, null),
            new Userinfo(2, "frog", "frog@mail", null, null)
        };
        
        String name = "lista-usuarios";
        String token = "ejemplo de token";
        RestResponse r = new RestResponse(true);
        ListAttribute lista = new ListAttribute(name);
        
        lista.of("Userinfo").withElements(elements);
        r.withComposedAttribute(lista).withAttribute("token", token);
        
        assertTrue(r.attributes.containsKey("token"));
        assertTrue(r.attributes.containsKey(name));
        
        assertEquals(token, r.attributes.get("token"));
        
        Map<String,Object> objetoLista = (Map<String, Object>)r.attributes.get(name);
        
        assertTrue(objetoLista.containsKey("type"));
        assertTrue(objetoLista.containsKey("elements"));
        
        assertEquals("Userinfo", objetoLista.get("type"));
        
        List<Object> list = (List<Object>)objetoLista.get("elements");
        
        for (int i = 0; i < elements.length; ++i) {
            assertEquals(elements[i].getFullname(), ((Userinfo) list.get(i)).getFullname());
        }
        
        
    }
    
}
