/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import io.fusionauth.jwt.domain.InvalidJWTSignatureException;
import io.fusionauth.jwt.domain.JWT;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
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
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    public static final ResponseBuilder RESP_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("You must be authenticated to have access to this resource");
    public static final ResponseBuilder RESP_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized to have access to this resource");
    public static final ResponseBuilder RESP_BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).entity("Your request was invalid");

    public static final String AUTH_TOKEN = "Authorization";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        
       /*
        String token = requestContext.getHeaderString(AUTH_TOKEN);
        if (token == null || "".equals(token)) {
            requestContext.abortWith(RESP_UNAUTHORIZED.build());
            return;
        }
        
        SignaturePolicy pcy = SignaturePolicy.PCYS.get("session-token");
        
        try {
            JWT jwt = JWT.getDecoder().decode(token,pcy.verifier);    
            System.out.println("Funcionó: " + jwt.issuer );
        } catch(InvalidJWTSignatureException ex) {
            requestContext.abortWith(RESP_UNAUTHORIZED.build());
            return;
        }
        */
    }

    
    /* Ejemplo código
        
    Signer signer = HMACSigner.newSHA256Signer("secret");

        JWT jwt = new JWT().setIssuer("iweb");
        String encoded = JWT.getEncoder().encode(jwt, signer);
        Verifier verifier = HMACVerifier.newVerifier("secret");

        try {
            JWT decoded = JWT.getDecoder().decode(encoded, verifier);
            if (decoded == null && decoded.issuer != null) {
                System.out.println("Issuer: " + decoded.issuer);
            }
        } catch (InvalidJWTSignatureException e) {
            System.out.println(e);
            requestContext.abortWith(RESP_UNAUTHORIZED.build());
        }

    */
    
}
