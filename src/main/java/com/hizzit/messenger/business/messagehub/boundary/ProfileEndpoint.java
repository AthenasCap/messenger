package com.hizzit.messenger.business.messagehub.boundary;
import com.hizzit.messenger.business.messagehub.control.ProfileStore;
import com.hizzit.messenger.business.messagehub.entity.Profile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
import javax.ws.rs.core.MediaType;

@Path("/profiles")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class ProfileEndpoint {
    
    @Inject
    ProfileStore ps;
    
    @GET
    @ApiOperation(value = "Retrieves all profiles")
    @ApiResponse(code = 400, message = "Invalid input")
    public List<Profile> getProfiles(){
        return ps.getAllProfiles();
    }
    
    @GET
    @Path("/{profileName}")
    @ApiOperation(value = "Retrieves a profile by profileName")
    @ApiResponse(code = 400, message = "Invalid input")
    public Profile getProfile(@PathParam("profileName") String profileName){
        return ps.getProfile(profileName);
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
    @ApiResponse(code = 400, message = "Invalid input")
    public Profile addProfile(Profile profile){
        return ps.addProfile(profile);
    }
    
    @DELETE
    @Path("/{profileName}")
    @ApiOperation(value = "Deletes a profile by profileName")
    @ApiResponse(code = 400, message = "Invalid input")
    public Profile deleteProfile(@PathParam("profileName") String profileName){
        return ps.removeProfile(profileName);
    }
}
