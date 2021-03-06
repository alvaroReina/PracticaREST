/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Alvaro
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.iweb.restserver.config.CORSFilter.class);
        resources.add(com.iweb.restserver.security.AuthenticationFilter.class);
        resources.add(com.iweb.restserver.service.SerieFacadeREST.class);
        resources.add(com.iweb.restserver.service.SketchFacadeREST.class);
        resources.add(com.iweb.restserver.service.UserinfoFacadeREST.class);
    }
    
}
