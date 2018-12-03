/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;


/**
 *
 * @author jose
 */
public class SignaturePolicy {

    public static final Algorithm DEFAULT_ALG;
    public static final Map<String, Algorithm> ALGS;
    
    static {
        Algorithm tmp = null;
        try {
            tmp = Algorithm.HMAC256("SECRET FOR DEVELOPMENT");
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            getLogger(SignaturePolicy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DEFAULT_ALG = tmp;
        }

        ALGS = new HashMap<>();
        loadBundledKey("/conf/keys/digitalSignature.json");
    }

    public static void loadKey(InputStream istream) {
       
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String,Object> map = mapper.readValue(istream, Map.class);
            
            String algname = (String) map.get("alg");
            
            Algorithm alg;
            String signKey;
            String verKey;
            String kid;
            
            Object candidate = null;
            
            candidate = map.get("sign");
            if (candidate == null) {
                err("No candidate sign key. Unable to continue.");
                return;
            }
            signKey = (String) candidate;
            
            candidate = map.get("verify");
            if (candidate != null) { //not allways mandatory
                verKey = (String) candidate;
            }
            
            candidate = map.get("kid");
            if (candidate != null) {
                kid = (String) candidate;
            } else {
                info("No algorithm kid provided. This will override any previous 'default'.");
                kid = "default";
            }   
            
            switch(algname){
                case "HS256": 
                    alg = Algorithm.HMAC256(signKey);
                    ALGS.put(kid, alg);
                break;
                case"HS384": 
                    alg = Algorithm.HMAC384(signKey);
                    ALGS.put(kid, alg);
                break;
                case "HS512":
                    alg = Algorithm.HMAC512(signKey);
                    ALGS.put(kid, alg);
                break;
                default:
                    throw new RuntimeException("Unsupported algorithm: " + algname);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SignaturePolicy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             Logger.getLogger(SignaturePolicy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadBundledKey(String path) {
        InputStream istream = SignaturePolicy.class.getResourceAsStream(path);
        info("Adding resources from " + path);
        
        if (istream == null){
            err("Unable to find the resources under " + path);
            return;
        }
        
        loadKey(istream);
    }
    
    
    private static void info(String msg) {
        Logger.getLogger(SignaturePolicy.class.getName()).log(Level.CONFIG, msg);
    }
    
    private static void err(String err) {
        Logger.getLogger(SignaturePolicy.class.getName()).log(Level.WARNING, err);
    }
    
    /* Posible adición en el futuro
    public static SignaturePolicy loadConfig(InputStream istream) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    } 
     */
}
