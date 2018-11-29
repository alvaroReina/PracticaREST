/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import java.security.Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jose
 */
@Provider
@RequireAuthentication
public class AuthenticationFilter implements ContainerRequestFilter {

    public static final Response RESP_UNAUTHORIZED  = Response.status(Response.Status.UNAUTHORIZED).entity("You must be authenticated to have access to this resource").build();
    public static final Response RESP_FORBIDDEN     = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized to have access to this resource").build();
    public static final Response RESP_BAD_REQUEST   = Response.status(Response.Status.BAD_REQUEST).entity("Your request was invalid").build();
    
    public static final String AUTH_TOKEN           = "Authorization";
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        
        String token = requestContext.getHeaderString(AUTH_TOKEN);
        if(token == null || "".equals(token)) {
            requestContext.abortWith(RESP_UNAUTHORIZED);
            return;
        }
        
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return "Fido";
                    }
                };    
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return "OAuth 2.0";
            }
        });

        System.out.println("EminemEEEEEMINEM");
    }
    
}
