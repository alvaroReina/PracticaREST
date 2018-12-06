package com.iweb.restserver.response;

import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jose
 */
public class SingleEntryAttribute extends Attribute{

    private String type;
    private Object value;
   
    public SingleEntryAttribute(String name, Object value) {
        this(name, value, "any");
    }
    
    
    public SingleEntryAttribute(String name, Object value, String type) {
        super(name);
        this.value = value;
        this.type = type;
    }
    
    @Override
    public Map<String, Object> pack() {
        Map<String,Object> map = new HashMap<>();
        if(type == null) {
            type = "any";
        }
        map.put("value", value);
        map.put("type", type);
        return map;
    }
    
}
