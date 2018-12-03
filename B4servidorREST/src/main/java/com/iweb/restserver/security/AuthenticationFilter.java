/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jose
 */
@Provider
@RequireAuthentication
public class AuthenticationFilter implements ContainerRequestFilter {

    public static final ResponseBuilder RESP_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("You must be authenticated to have access to this resource");
    public static final ResponseBuilder RESP_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized to have access to this resource");
    public static final ResponseBuilder RESP_BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).entity("Your request was invalid");

    public static final String AUTH_TOKEN = "Authorization";

    @Override
    public void filter(ContainerRequestContext requestContext) {

        Algorithm alg = null;//SignaturePolicy.ALGS.get("session-token");
        /*
        String token = requestContext.getHeaderString(AUTH_TOKEN);
        if (token == null || "".equals(token)) {
            requestContext.abortWith(RESP_UNAUTHORIZED.build());
            return;
        }
         */

        String token = null;
        try {
            alg = Algorithm.HMAC256("secret");
            token = JWT.create()
                    .withIssuer("iweb").sign(alg);
            
            System.out.println("Generated token: " + token);
            
           JWTVerifier verifier = JWT
                   .require(alg)
                   .build();
           
           DecodedJWT jwt = verifier.verify(token);
           
            System.out.println(jwt.getHeader());
        } catch (JWTCreationException ex) {
            System.out.println("Creation exception: " + ex.getMessage());
        } catch (JWTVerificationException ex) {
            System.out.println("Verification exception: " + ex.getMessage());
        }
        
        /*
            TODO
         */
        System.out.println("El filtro ha pasado esta request, pero no comprueba nada!");
    }

}
