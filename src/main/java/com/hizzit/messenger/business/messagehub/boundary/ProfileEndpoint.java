package com.hizzit.messenger.business.messagehub.boundary;
import com.hizzit.messenger.business.messagehub.control.ProfileStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Profile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/profiles")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class ProfileEndpoint {
    
    @Inject
    ProfileStore ps;

    public ProfileEndpoint() {
    }
    
    
    @GET
    @ApiOperation(value = "Retrieves all profiles")
    @ApiResponse(code = 400, message = "Invalid input")
    public List<Profile> getProfiles(){
        return ps.getAllProfiles();
    }
    
    @GET
    @Path("/{profileId}")
    @ApiOperation(value = "Retrieves a profile by profileId")
    @ApiResponse(code = 400, message = "Invalid input")
    public Profile getProfile(@PathParam("profileId") String profileId){
        return ps.getProfileById(profileId);
    }
    
    @PUT
    @Path("/{profileName}")
    @ApiOperation(value = "Updates a profile by profileName")
    @ApiResponse(code = 400, message = "Invalid input")
    public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile){
        profile.setProfileName(profileName);
        return ps.updateProfile(profile);
    }
    
    @POST
    @ApiOperation(value = "Excepts a new profile")
    @ApiResponse(code = 201, message = "Created")
    public Response addProfile(Profile profile, @Context UriInfo uriInfo){
        profile.setId(UUIDgenerator.generate());
        
       
        Profile newProfile = ps.addProfile(profile);
        
        URI uri = uriInfo.getAbsolutePathBuilder().path(newProfile.getId()).build();
        
        return Response
                .created(uri)
                .entity(newProfile)
                .build();
    }
    
    @DELETE
    @Path("/{profileId}")
    @ApiOperation(value = "Deletes a profile by profileId")
    @ApiResponse(code = 400, message = "Invalid input")
    public Profile deleteProfile(@PathParam("profileId") String profileId){
        return ps.removeProfile(profileId);
    }
}
