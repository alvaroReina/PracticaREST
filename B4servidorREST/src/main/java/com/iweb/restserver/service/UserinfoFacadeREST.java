/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iweb.restserver.entity.Userinfo;
import com.iweb.restserver.security.RequireAuthentication;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jose
 */
@Stateless
@Path("/")
public class UserinfoFacadeREST extends AbstractFacade<Userinfo> {

    @PersistenceContext(unitName = "com.iweb_B4servidorREST_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserinfoFacadeREST() {
        super(Userinfo.class);
    }

    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Response signin(@FormParam("Gtoken") String tokenID) throws JsonProcessingException {
        return null;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("users/{id}")
    @RequireAuthentication
    public Userinfo findByID(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
