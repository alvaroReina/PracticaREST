/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jose
 */
public class ErrorAttribute extends Attribute{

    private String cause, hint;
    private List<String> fields;
    
    public ErrorAttribute() {
        this("error");
    }
    
    public ErrorAttribute(String name) {
        super(name);
        this.fields = new ArrayList<>();
    }
    
    public ErrorAttribute withCause(String cause) {
        this.cause = cause;
        return this;
    }
    
    public ErrorAttribute withHint(String hint) {
        this.hint = hint;
        return this;
    }
    
    public ErrorAttribute addField(String field) {
        this.fields.add(field);
        return this;
    }
    
    public ErrorAttribute withFields(String[] fields) {
        if (fields != null)
            this.fields.addAll(Arrays.asList(fields));
        return this;
    }
    
    public ErrorAttribute withFields(List<String> fields) {
        if (fields != null) 
            this.fields.addAll(fields);
        return this;
    }
       
    @Override
    public Map<String, Object> pack() {
        Map<String,Object> map = new HashMap<>();
        if (this.cause != null) 
            map.put("cause", cause);
        if (this.fields != null && this.fields.size() > 0)
            map.put("fields", fields);
        if (this.hint != null)
            map.put("hint", hint);
        return map;
    }
    
}
