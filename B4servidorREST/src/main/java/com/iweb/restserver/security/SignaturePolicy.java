/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import com.auth0.jwt.algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jose
 */
public class SignaturePolicy {
    
    
    public static final Algorithm DEFAULT_ALG;
       
    static {
        Algorithm tmp = null;
        try {
            tmp = Algorithm.HMAC256("SECRET FOR DEVELOPMENT");
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            Logger.getLogger(SignaturePolicy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DEFAULT_ALG = tmp;
        }
    }

    /* Posible adición en el futuro
    public static SignaturePolicy loadConfig(InputStream istream) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    } 
    */
}
