/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.exceptions;

/**
 *
 * @author jose
 */
public class ValidationException extends RuntimeException {
    
    public String[] fields;
        
    public ValidationException(String msg) {
        this(msg, new String[]{});
    }
    
    public ValidationException(String msg, String[] fields) {
        super(msg);
        this.fields = fields;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
 
    
    
}
