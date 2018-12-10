/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import com.iweb.restserver.entity.Sketch;
import com.iweb.restserver.response.ErrorAttribute;
import com.iweb.restserver.response.ResponseFactory;
import static com.iweb.restserver.response.ResponseFactory.newError;
import com.iweb.restserver.response.RestResponse;
import java.sql.Date;
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
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

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
    public Response create(Sketch entity) {
        
        RestResponse resp = new RestResponse(true);
        
        if (entity.getIdserie() == null && "".equals(entity.getTitle())){            
            return newError(BAD_REQUEST, "IDSERIE or title not present", null, "Please, insert IDSERIE or title first").build();
        }
        
        entity.setCreatedat(new Date(System.currentTimeMillis()));
        entity.setScore(5);        
        super.create(entity);
        
        resp.isSuccessful(true)
                    .withStatus(Response.Status.OK);        
               
        return resp.build();
        
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Sketch entity) {
        
        RestResponse resp = new RestResponse(true);
        
        if (id == null){
            return newError(BAD_REQUEST, "ID not present", null, "Please, insert ID first").build();
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
            return newError(BAD_REQUEST, "ID not present", null, "Please, insert ID first").build();
        }
            super.remove(super.find(id));
            resp.isSuccessful(true)
                    .withStatus(Response.Status.OK);        
               
        return resp.build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Integer id) {
        
        RestResponse resp = new RestResponse(true); 
         
        if (id == null) {
            return newError(BAD_REQUEST, "ID not present", null, "Please, insert ID first").build();
        }
        
        Sketch s = super.find(id);            
        getEntityManager().merge(s);
        resp.isSuccessful(true)
                .withStatus(Response.Status.OK);
        return ResponseFactory.newSingleEntity(s, "sketch").build();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        
        RestResponse resp = new RestResponse(true); 
         
        if (from == null || to == null) {            
            return newError(BAD_REQUEST, "Range not present", null, "Please, insert range first").build();
        }
            super.findRange(new int[]{from, to});
            resp.isSuccessful(true)
                    .withStatus(Response.Status.OK);        
               
        return resp.build();
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
                
            Query q =em.createQuery("SELECT s FROM Sketch s ORDER By s.score DESC");
            q.setMaxResults(5);  
            
        return ResponseFactory.newList(q.getResultList()).build();
    }

    @GET
    @Path("latest")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response latest() {        
        
        Query q =em.createQuery("SELECT s FROM Sketch s ORDER By s.createdat DESC");
        q.setMaxResults(5);
        
        return ResponseFactory.newList(q.getResultList()).build();  
                   
    }

    @GET
    @Path("betweendates")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findBetweenDates(@QueryParam("from") String from, @QueryParam("to") String to) {
        
        
        if (from == null || to == null) {            
            return newError(BAD_REQUEST, "Range not present", null, "Please, insert range first").build();
        }
        
        Query q = em.createQuery("SELECT s FROM Sketch s WHERE s.createdat BETWEEN :from AND :to ORDER BY s.createdat DESC");
        q.setParameter("from", from);
        q.setParameter("to", to);
        
        return ResponseFactory.newList(q.getResultList()).build(); 
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
