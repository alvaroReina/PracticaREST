/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.response;

import java.util.Map;

/**
 *
 * @author jose
 */
public abstract class Attribute {
    
    private String name;
    
    protected Attribute(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public abstract Map<String,Object> pack();
    
}
