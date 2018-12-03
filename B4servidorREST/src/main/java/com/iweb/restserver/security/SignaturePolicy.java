/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.io.IOException;
import java.io.InputStream;
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

    public Signer signer;
    public Verifier verifier;

    public static final Map<String, SignaturePolicy> PCYS;
    public static final SignaturePolicy DEFAULT_PCY;

    static {
        SignaturePolicy tmp = new SignaturePolicy();
        try {
            String key = "SECRET FOR DEVELOPMENT";
            tmp.signer = HMACSigner.newSHA256Signer(key);
            tmp.verifier = HMACVerifier.newVerifier(key);
        } catch (IllegalArgumentException ex) {
            getLogger(SignaturePolicy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DEFAULT_PCY = tmp;
        }

        PCYS = new HashMap<>();
        loadBundledKey("/conf/keys/digitalSignature.json");
    }

    public static void loadKey(InputStream istream) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = mapper.readValue(istream, Map.class);

            String algname = (String) map.get("alg");
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

            SignaturePolicy pcy = new SignaturePolicy();

            switch (algname) {
                case "HS256":
                    pcy.signer = HMACSigner.newSHA256Signer(signKey);
                    pcy.verifier = HMACVerifier.newVerifier(signKey);
                    break;
                case "HS384":
                    pcy.signer = HMACSigner.newSHA384Signer(signKey);
                    pcy.verifier = HMACVerifier.newVerifier(signKey);
                    break;
                case "HS512":
                    pcy.signer = HMACSigner.newSHA512Signer(signKey);
                    pcy.verifier = HMACVerifier.newVerifier(signKey);
                    break;
                //case "EC256": //En un futuro
                default:
                    throw new RuntimeException("Unsupported algorithm: " + algname);
            }
            
            PCYS.put(kid,pcy);
        } catch (IOException | RuntimeException ex) {
            Logger.getLogger(SignaturePolicy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadBundledKey(String path) {
        InputStream istream = SignaturePolicy.class.getResourceAsStream(path);
        info("Adding resources from " + path);

        if (istream == null) {
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

}
