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
public class ListAttribute extends Attribute{
    
    private List elements;
    private String type;
    
    public ListAttribute() {
        this("list");
    }
        
    public ListAttribute(String name) {
        super(name);
        this.elements = new ArrayList<>();
        this.type = "any";
    }
    
    public ListAttribute of(String elementType) {
        this.type = elementType;
        return this;
    }
   
    
   /**
    * Overrides the existing elements!
     * @param elements
     * @return 
    */
    public ListAttribute withElements(List<Object> elements) {
        this.elements = elements;
        return this;
    }
    
     /**
    * Overrides the existing elements!
     * @param elements
     * @return 
    */
    public ListAttribute withElements(Object[] elements) {
        this.elements.clear();
        this.elements.addAll(Arrays.asList(elements));
        
        return this;
    }

    public ListAttribute addElement(Object o) {
        this.elements.add(o);
        return this;
    }
    
    public ListAttribute addElements(List<Object> elements) {
        this.elements.addAll(elements);
        return this;
    }
    
    
    public ListAttribute addElements(Object[] elements) {
        this.elements.addAll(Arrays.asList(elements));
        return this;
    }
    
    
    @Override
    public Map<String, Object> pack() {
        
        Map<String,Object> map = new HashMap<>();
        if (type == null){
            type = "any";
        }
        map.put("type", type); 
        map.put("elements", this.elements);
        
        return map;
    }
    
}
