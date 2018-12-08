/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iweb.restserver.response;

import java.util.List;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author jose
 */
public class ResponseFactory {

    public static RestResponse newSingleEntity(Object o, String entityName) {
        return newSingleEntity(o, entityName, null);
    }
    
    public static RestResponse newSingleEntity(Object o, String entityName, String entityType) {
        SingleEntryAttribute attrb;
        if (entityType == null || "".equals(entityType)) {
            attrb = new SingleEntryAttribute(entityName, o);
        }else {
            attrb = new SingleEntryAttribute(entityName, o, entityType);
        }
        return new RestResponse(true).withStatus(Status.OK).withComposedAttribute(attrb);
    } 
    
    public static RestResponse newList(List<Object> list) {
        return newList(list, null, null);
    }
    
    public static RestResponse newList(List<Object> list, String listName, String elementType) {
        RestResponse resp = new RestResponse(true);
        ListAttribute listAttribute;
        
        if (listName == null || "".equals(listName)) {
            listAttribute = new ListAttribute();
        } else {
            listAttribute = new ListAttribute(listName);
        }

        listAttribute.withElements(list).of(elementType);

        return resp.withStatus(Status.OK).withComposedAttribute(listAttribute);
    }

    public static RestResponse newError(Status code, String cause, String[] fields, String hint) {
        RestResponse resp = new RestResponse(false);
        ErrorAttribute err = new ErrorAttribute()
                .withFields(fields)
                .withHint(hint)
                .withCause(cause);
        return resp.withStatus(code).withComposedAttribute(err);
    }

    public static RestResponse newError(Status code, String cause) {
        String hint = deriveHintFromStatus(code);
        return newError(code, cause, null, hint);
    }

    private static String deriveHintFromStatus(Status code) {
        switch (code) {
            case BAD_REQUEST:
                return "you sent an invalid request";
            case NOT_FOUND:
                return "the resource you were looking for does not exist";
            case UNAUTHORIZED:
                return "you must be authenticated to do so";
            case FORBIDDEN:
                return "you are not allowed to access to this resource";
            case INTERNAL_SERVER_ERROR:
            case SERVICE_UNAVAILABLE:
                return "the service is unavailabe, try again later";
            case UNSUPPORTED_MEDIA_TYPE:
                return "the type of content you sent is not allowed here";
            default:
                return null;
        }
    }

}
