package com.hizzit.messenger.business.messagehub.boundary;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class DemoEndpoint {

    public DemoEndpoint() {
    }

    @GET
    @Path("annotations")
    public String getParamsUsingAnnotations(@MatrixParam("matrixparam") String matrixParam,
                                            @HeaderParam("headerparam") String headerParam,
                                            @CookieParam("cookieparam") String cookieParam){
        
        return "Matrix param: " + matrixParam + " Header param: " + headerParam + " Cookie param: " + cookieParam;
    }
    
    
    
    @GET
    @Path("context")
    public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){
        String path = uriInfo.getAbsolutePath().toString();
        String cookies = headers.getCookies().toString();
        return "path: " + path + " Cookies: " + cookies;
    }
}