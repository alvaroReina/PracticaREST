/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.iweb.restserver.entity.Sketch;
import com.iweb.restserver.response.ErrorAttribute;
import com.iweb.restserver.response.RestResponse;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
@Path("sketches")
public class SketchFacadeREST extends AbstractFacade<Sketch> {

    @PersistenceContext(unitName = "com.iweb_B4servidorREST_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public SketchFacadeREST() {
        super(Sketch.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Sketch entity) {
                
        entity.setCreatedat(new Date(System.currentTimeMillis()));
        entity.setScore(5);
        
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Sketch entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Sketch find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sketch> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sketch> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
    public Response topScore() {
        
        RestResponse resp = new RestResponse(true);
        
            Query q =em.createQuery("SELECT s FROM Sketch s ORDER By s.score DESC");
            q.setMaxResults(5);
            resp.isSuccessful(true)
                    .withStatus(Response.Status.OK)
                    .withAttribute("list", q.getResultList());   
                   
        return resp.build();
    }

    @GET
    @Path("latest")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response latest() {
        
        RestResponse resp = new RestResponse(true);
        
        Query q =em.createQuery("SELECT s FROM Sketch s ORDER By s.createdat DESC");
        q.setMaxResults(5);
        resp.isSuccessful(true)
                    .withStatus(Response.Status.OK)
                    .withAttribute("list", q.getResultList());   
                   
        return resp.build();
    }

    @GET
    @Path("betweendates")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findBetweenDates(@QueryParam("from") String from, @QueryParam("to") String to) {
        
        RestResponse resp = new RestResponse(true);
        
        if (from == null || to == null) {
            ErrorAttribute err = new ErrorAttribute();
            return resp
                    .isSuccessful(false)
                    .withComposedAttribute(err)
                    .withStatus(Response.Status.BAD_REQUEST)
                    .build();
        }
        
        Query q = em.createQuery("SELECT s FROM Sketch s WHERE s.createdat BETWEEN :from AND :to ORDER BY s.createdat DESC");
        q.setParameter("from", from);
        q.setParameter("to", to);
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
