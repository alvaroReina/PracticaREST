/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.iweb.restserver.entity.Serie;
import com.iweb.restserver.entity.Sketch;
import com.iweb.restserver.response.ErrorAttribute;
import com.iweb.restserver.response.RestResponse;
import java.sql.Date;
import java.util.List;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response create(Serie entity) {
        RestResponse resp = new RestResponse(true);

        if (entity.getId() == null && "".equals(entity.getTitle())) {
            ErrorAttribute err = new ErrorAttribute();
            err.withCause("IDSERIE or title not present");
            err.withHint("Please, insert IDSERIE or title first");
            return resp
                    .isSuccessful(false)
                    .withComposedAttribute(err)
                    .withStatus(Response.Status.BAD_REQUEST)
                    .build();
        } else if (entity.getPicture() == null || "".equals(entity.getPicture())) {
            entity.setPicture("assets/img/default.jpg");
        }

        entity.setViews(0);
        entity.setScore(5);
        super.create(entity);

        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);

        return resp.build();

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Serie entity) {
        RestResponse resp = new RestResponse(true);

        if (id == null) {
            ErrorAttribute err = new ErrorAttribute();
            err.withCause("ID not present");
            err.withHint("Please, insert ID first");
            return resp
                    .isSuccessful(false)
                    .withComposedAttribute(err)
                    .withStatus(Response.Status.BAD_REQUEST)
                    .build();
        }
        super.edit(entity);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);

        return resp.build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        RestResponse resp = new RestResponse(true);

        if (id == null) {
            ErrorAttribute err = new ErrorAttribute();
            err.withCause("ID not present");
            err.withHint("Please, insert ID first");
            return resp
                    .isSuccessful(false)
                    .withComposedAttribute(err)
                    .withStatus(Response.Status.BAD_REQUEST)
                    .build();
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
            ErrorAttribute err = new ErrorAttribute();
            err.withCause("ID not present");
            err.withHint("Please, insert ID first");
            return resp
                    .isSuccessful(false)
                    .withComposedAttribute(err)
                    .withStatus(Response.Status.BAD_REQUEST)
                    .build();
        }

        Serie s = super.find(id);
        Integer v = s.getViews() + 1;
        s.setViews(v);
        Query q = em.createQuery("UPDATE Serie SET views =: v WHERE id =: id");
        q.setParameter("v", v);
        q.setParameter("id", id);

        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);

        return resp.build();
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
        RestResponse resp = new RestResponse(true);

        Query q = em.createQuery("SELECT s FROM Serie s ORDER By s.score DESC");
        q.setMaxResults(5);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK)
                .withAttribute("list", q.getResultList());

        return resp.build();
    }

    @GET
    @Path("mostviewed")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response mostViewed() {
        RestResponse resp = new RestResponse(true);

        Query q = em.createQuery("SELECT s FROM Serie s ORDER By s.views DESC");
        q.setMaxResults(5);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK)
                .withAttribute("list", q.getResultList());

        return resp.build();
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response searchWithFilter(@QueryParam("title") String title,
            @QueryParam("from") Integer from, @QueryParam("to") Integer to) {
        if (from == null || to == null || title.equals("")) {
            throw new RuntimeException("Null filter");
        }

        RestResponse resp = new RestResponse(true);

        Query q = em.createQuery("SELECT s FROM Serie s WHERE s.title BETWEEN :from AND :to LIKE :%title%");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("tittle", "%" + title + "%");

        resp.isSuccessful(true)
                .withStatus(Response.Status.OK)
                .withAttribute("list", q.getResultList());

        return resp.build();
    }

    @GET
    @Path("{id}/sketches")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSerieSketches(@PathParam("id") Integer serieID) {
        if (serieID == 0 || serieID == null) {
            throw new RuntimeException("wrong serieID");
        }

        RestResponse resp = new RestResponse(true);

        Query q = em.createQuery("SELECT s FROM Sketch s WHERE s.idserie =: serieID");
        q.setParameter("serieID", serieID);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK)
                .withAttribute("list", q.getResultList());

        return resp.build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
