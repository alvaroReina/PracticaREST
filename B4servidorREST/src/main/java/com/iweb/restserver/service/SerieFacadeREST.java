/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.iweb.restserver.entity.Serie;
import com.iweb.restserver.entity.Sketch;
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
    public void create(Serie entity) {
        
        if (entity.getPicture() == null || "".equals(entity.getPicture()))
            entity.setPicture("DEFAULTO");
        
        
        
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Serie entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Serie find(@PathParam("id") Integer id) {
        Serie s = super.find(id);
        Integer v = s.getViews() + 1;
        s.setViews(v);
        return s;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Serie> findAll() {
        return super.findAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("top")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Serie> topScore() {
        Query q = em.createQuery("SELECT s FROM Serie s ORDER By s.score DESC");
        q.setMaxResults(5);
        return q.getResultList();
    }

    @GET
    @Path("mostviewed")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Serie> mostViewed() {
        Query q = em.createQuery("SELECT s FROM Serie s ORDER By s.views DESC");
        q.setMaxResults(5);
        return q.getResultList();
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Serie> searchWithFilter(@QueryParam("title") String title,
            @QueryParam("from") Integer from, @QueryParam("to") Integer to) {
        if (from == null || to == null) {
            throw new RuntimeException("Null filter");
        }

        Query q = em.createQuery("SELECT s FROM Serie s WHERE s.title BETWEEN :from AND :to LIKE :%title%");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("tittle", "%" + title + "%");
        return q.getResultList();
    }

    @GET
    @Path("{id}/sketches")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Sketch> getSerieSketches(@PathParam("id") Integer serieID) {
        Query q = em.createQuery("SELECT s FROM Sketch s WHERE s.idserie =: serieID");
        q.setParameter("serieID", serieID);
        return q.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
