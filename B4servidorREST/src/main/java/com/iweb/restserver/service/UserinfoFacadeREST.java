/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.iweb.restserver.entity.Userinfo;
import com.iweb.restserver.response.ErrorAttribute;
import com.iweb.restserver.response.RestResponse;
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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.iweb.restserver.exceptions.ValidationException;
import com.iweb.restserver.response.SingleEntryAttribute;
import com.iweb.restserver.security.SignaturePolicy;
import io.fusionauth.jwt.domain.JWT;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

/**
 *
 * @author jose
 */
@Stateless
@Path("/")
public class UserinfoFacadeREST extends AbstractFacade<Userinfo> {

    private static final String CLIENT_ID = "no se que client id tenemos";

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
        RestResponse resp = new RestResponse(true);

        if (tokenID == null || "".equals(tokenID)) {
            ErrorAttribute err = new ErrorAttribute();
            err.withCause("Google token ID not present");
            err.withHint("Please, log in with Google first");
            return resp
                    .isSuccessful(false)
                    .withComposedAttribute(err)
                    .withStatus(Response.Status.BAD_REQUEST)
                    .build();
        }

        GoogleIdToken googleDecoded;
        try {
            googleDecoded = GOOGLE_VERIFIER.verify(tokenID);
        } catch (GeneralSecurityException | IOException ex) {
            Logger.getLogger(UserinfoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return resp
                    .isSuccessful(false)
                    .withStatus(Response.Status.INTERNAL_SERVER_ERROR)
                    .withAttribute("exception", ex)
                    .build();
        }

        if (googleDecoded == null) {
            ErrorAttribute attr = new ErrorAttribute()
                    .withCause("invalid token")
                    .withHint("obtain a valid one");
            return resp
                    .withComposedAttribute(attr)
                    .withStatus(Response.Status.UNAUTHORIZED)
                    .build();
        }

        Payload payload = googleDecoded.getPayload();

        Userinfo incomingUser;

        try {
            incomingUser = extractUser(payload);
        } catch (ValidationException ex) {
            return resp.isSuccessful(false)
                    .withComposedAttribute(new ErrorAttribute().withCause(ex.getMessage()))
                    .withStatus(Response.Status.UNAUTHORIZED)
                    .build();
        }

        try {
            Userinfo user = findByEmail(incomingUser);

            if (user == null) {
                em.persist(incomingUser);
                user = findByEmail(incomingUser);
            }

            JWT jwt = new JWT();
            jwt.setIssuer("iweb-auth");
            jwt.setSubject(user.getEmail());
            jwt.otherClaims.put("user", user);
            jwt.setAudience("iweb");
            jwt.setIssuedAt(ZonedDateTime.now());
            jwt.setExpiration(ZonedDateTime.now().plusWeeks(1));

            String sessionToken = JWT.getEncoder().encode(jwt, getPolicy().signer);

            //Crear la respuesta.
            resp.withAttribute("token", sessionToken)
                    .withComposedAttribute(new SingleEntryAttribute("user", user, "Userinfo"))
                    .withStatus(Response.Status.OK)
                    .withAttribute("role", user.getRole());

        } catch (Exception e) {
            return resp.isSuccessful(false).withStatus(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return resp.build();
    }

    private Userinfo extractUser(Payload payload) {
        Userinfo user;

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");

        user = new Userinfo(-1, name, email);
        user.setPicture(pictureUrl);

        if (user.getName() == null || user.getEmail() == null) {
            throw new ValidationException("invalid token");
        }

        return user;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("users/{id}")
    @RequireAuthentication
    public Userinfo findByID(@PathParam("id") Integer id) {
        return super.find(id);
    }

    private Userinfo findByEmail(Userinfo uinfo) {
        TypedQuery tq;
        tq = em.createNamedQuery("Userinfo.findByEmail", Userinfo.class);
        tq.setParameter("email", uinfo.getEmail());
        return (Userinfo) tq.getSingleResult();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
