/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.iweb.restserver.entity.Serie;
import com.iweb.restserver.response.ErrorAttribute;
import com.iweb.restserver.response.ResponseFactory;
import static com.iweb.restserver.response.ResponseFactory.newError;
import com.iweb.restserver.response.RestResponse;
import com.iweb.restserver.security.AuthContext;
import com.iweb.restserver.security.RequireAuthentication;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author jose
 */
@Stateless
@Path("series")
public class SerieFacadeREST extends AbstractFacade<Serie> {

    @PersistenceContext(unitName = "com.iweb_B4servidorREST_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public SerieFacadeREST() {
        super(Serie.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    @RequireAuthentication
    public Response create(Serie entity) {
        RestResponse resp = new RestResponse(true);

        entity.setId(0);
        if ("".equals(entity.getTitle())) {
            return ResponseFactory.newError(Response.Status.BAD_REQUEST, "Title required").build();
        } else if (entity.getPicture() == null || "".equals(entity.getPicture())) {
            entity.setPicture("assets/img/default.jpg");
        }

        entity.setViews(0);
        if (entity.getScore() == null) {
            entity.setScore(5);
        }

        super.create(entity);
        return resp.build();

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @RequireAuthentication
    public Response edit(@PathParam("id") Integer id, Serie entity, @Context SecurityContext sc) {
        RestResponse resp = new RestResponse(true);

        if (!sc.isUserInRole("ADMIN") && !sc.getUserPrincipal().getName().equals(entity.getAuthor().getEmail())) {
            return ResponseFactory.newError(Response.Status.FORBIDDEN, "you cannot access to this resource").build();
        }

        if (id == null) {
            return newError(BAD_REQUEST, "ID not present", null, "Please, insert ID first").build();
        }
        super.edit(entity);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);

        return resp.build();
    }

    @DELETE
    @Path("{id}")
    @RequireAuthentication
    public Response remove(@PathParam("id") Integer id, @Context SecurityContext sc) {
        RestResponse resp = new RestResponse(true);

        Serie s;
        try {
            s = em.find(Serie.class, id);
        } catch(Exception e) {
            return newError(Response.Status.NOT_FOUND, "no such serie").build();
        }
        
        if (!sc.isUserInRole("ADMIN") && !sc.getUserPrincipal().getName().equals(s.getAuthor().getEmail())) {
            return ResponseFactory.newError(Response.Status.FORBIDDEN, "you cannot access to this resource").build();
        }

        if (id == null) {
            return newError(BAD_REQUEST, "ID not present", null, "Please, insert ID first").build();
        }
        super.remove(super.find(id));
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);

        return resp.build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Integer id) {
        RestResponse resp = new RestResponse(true);

        if (id == null) {
            return newError(BAD_REQUEST, "ID not present", null, "Please, insert ID first").build();
        }

        Serie s = super.find(id);
        s.setViews(s.getViews() + 1);
        getEntityManager().merge(s);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);
        return ResponseFactory.newSingleEntity(s, "serie").build();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        return super.findAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST() {
        RestResponse resp = new RestResponse(true);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK)
                .withAttribute("value", String.valueOf(super.count()));
        return resp.build();
    }

    @GET
    @Path("top")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response topScore() {

        Query q = em.createQuery("SELECT s FROM Serie s ORDER By s.score DESC");
        q.setMaxResults(5);
        return ResponseFactory.newList(q.getResultList()).build();
    }

    @GET
    @Path("mostviewed")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response mostViewed() {

        Query q = em.createQuery("SELECT s FROM Serie s ORDER By s.views DESC");
        q.setMaxResults(5);
        return ResponseFactory.newList(q.getResultList()).build();
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RequireAuthentication
    public Response searchWithFilter(@QueryParam("title") String title,
            @QueryParam("from") Integer from, @QueryParam("to") Integer to, @Context SecurityContext sc) {

        boolean esUser = sc.isUserInRole("USER");
        boolean esAdmin = sc.isUserInRole("ADMIN");
        if (!esUser && !esAdmin) {
            return ResponseFactory.newError(Response.Status.FORBIDDEN, "you cannot access to this resource").build();
        }

        if (from == null || to == null || title.equals("")) {
            return newError(BAD_REQUEST, "Null filter", null, "Please, insert filter first").build();
        }

        if (from < 0 || to < 0 || from > to) {
            return newError(BAD_REQUEST, "Bad filter", null, "Please, insert valid filter first").build();
        }

        Query q = em.createQuery("SELECT s FROM Serie s WHERE s.title LIKE :title");

        if (from >= 0) {
            q.setFirstResult(from);
        }

        int limit = to - from + 1;
        if (limit > 100) {
            limit = 100;
        }

        q.setMaxResults(limit);

        q.setParameter("title", "%" + title + "%");
        return ResponseFactory.newList(q.getResultList()).build();
    }

    @GET
    @Path("{id}/sketches")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSerieSketches(@PathParam("id") Integer serieID) {

        if (serieID == 0 || serieID == null) {
            return newError(BAD_REQUEST, "Wrong ID", null, "Please, insert id first").build();
        }

        Query q = em.createQuery("SELECT s FROM Sketch s WHERE s.idserie.id = :serieID");
        q.setParameter("serieID", serieID);
        return ResponseFactory.newList(q.getResultList()).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
