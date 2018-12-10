/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.security;

import com.iweb.restserver.entity.Userinfo;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

public class AuthContext implements SecurityContext {

    private Userinfo principal;

    public AuthContext(Userinfo uinfo) {
        this.principal = uinfo;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.principal.getUserrole().equals(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "oauth2.0";
    }

}
