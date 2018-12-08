/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.iweb.restserver.entity.Userinfo;
import com.iweb.restserver.exceptions.ValidationException;
import com.iweb.restserver.response.ErrorAttribute;
import com.iweb.restserver.response.ResponseFactory;
import com.iweb.restserver.response.RestResponse;
import com.iweb.restserver.response.SingleEntryAttribute;
import com.iweb.restserver.security.RequireAuthentication;
import com.iweb.restserver.security.SignaturePolicy;
import io.fusionauth.jwt.domain.JWT;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import static com.iweb.restserver.response.ResponseFactory.*;
import javax.json.stream.JsonParsingException;

/**
 *
 * @author jose
 */
@Stateless
@Path("/")
public class UserinfoFacadeREST extends AbstractFacade<Userinfo> {

    private static final String CLIENT_ID = "676906808110-oml9olnm30pdcvk3jsjl9e9pp5hdof9j.apps.googleusercontent.com";

    private static SignaturePolicy singleton;

    private static final NetHttpTransport TRANSPORT;
    private static final GoogleIdTokenVerifier GOOGLE_VERIFIER;

    static {
        TRANSPORT = new NetHttpTransport();
        GOOGLE_VERIFIER = new GoogleIdTokenVerifier.Builder(TRANSPORT, JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singleton(CLIENT_ID))
                .build();
    }

    private static SignaturePolicy getPolicy() {
        if (singleton == null) {
            singleton = SignaturePolicy.PCYS.get("session-token");
        }
        return singleton;
    }

    
    
    @PersistenceContext(unitName = "com.iweb_B4servidorREST_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserinfoFacadeREST() {
        super(Userinfo.class);
    }

    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Response signin(@FormParam("Gtoken") String tokenID) {

        if (tokenID == null || "".equals(tokenID)) {
            return newError(BAD_REQUEST, "Google token ID not present", null, "Please, log in with Google first").build();
        }

        GoogleIdToken googleDecoded;
        try {
            googleDecoded = GOOGLE_VERIFIER.verify(tokenID);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(UserinfoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return newError(INTERNAL_SERVER_ERROR, "Security violation attempt").build();
           
        } catch (Exception ex) { //NEVER REPLACE WITH IOException IllegalArgumenException can be thrown
            Logger.getLogger(UserinfoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return newError(INTERNAL_SERVER_ERROR, "malformed token").build();
        }

        if (googleDecoded == null) {
            return newError(UNAUTHORIZED, "invalid token").build();
        }

        GoogleIdToken.Payload payload = googleDecoded.getPayload();
        Userinfo incomingUser;

        try {
            incomingUser = extractUser(payload);
        } catch (ValidationException ex) {
             Logger.getLogger(UserinfoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return newError(UNAUTHORIZED, "invalid token").build();
        }

        try {
            Userinfo user = findByEmail(incomingUser);
            if (user == null) {
                incomingUser.setUserrole("USER");
                user = register(incomingUser);
            }

            JWT jwt = new JWT();
            jwt.setIssuer("iweb-auth");
            jwt.setSubject(user.getEmail());
            jwt.otherClaims.put("user", user);
            jwt.setAudience("iweb");
            jwt.setIssuedAt(ZonedDateTime.now());
            jwt.setExpiration(ZonedDateTime.now().plusWeeks(1));

            String sessionToken = JWT.getEncoder().encode(jwt, getPolicy().signer);
            return newSingleEntity(user, "user", "Userinfo")
                    .withAttribute("session-token", sessionToken)
                    .build();


        } catch (PersistenceException | JsonParsingException ex) {
             Logger.getLogger(UserinfoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return newError(INTERNAL_SERVER_ERROR, "We could retrieve your information").build();
        }
    }

    private Userinfo extractUser(GoogleIdToken.Payload payload) {
        Userinfo user;

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");

        user = new Userinfo(0, name, email, null, pictureUrl);
        user.setPicture(pictureUrl);

        if (user.getFullname()== null || user.getEmail() == null) {
            throw new ValidationException("invalid token");
        }

        return user;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("users/{id}")
    @RequireAuthentication
    public Userinfo findByID(@PathParam("id") Integer id) {
        return super.find(id);
    }

    private Userinfo findByEmail(Userinfo uinfo) {
        TypedQuery tq;
        tq = em.createNamedQuery("Userinfo.findByEmail", Userinfo.class);
        tq.setParameter("email", uinfo.getEmail());
        try {
            return (Userinfo) tq.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Userinfo register(Userinfo uinfo) {
        try {
            getEntityManager().persist(uinfo);
            Userinfo ret = findByEmail(uinfo);
            return ret;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return uinfo;
        }
    }

    
    
    
    @POST
    @Override
    @Consumes({  MediaType.APPLICATION_JSON})
    public void create(Userinfo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({  MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Userinfo entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({  MediaType.APPLICATION_JSON})
    public Userinfo find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({  MediaType.APPLICATION_JSON})
    public List<Userinfo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({  MediaType.APPLICATION_JSON})
    public List<Userinfo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
