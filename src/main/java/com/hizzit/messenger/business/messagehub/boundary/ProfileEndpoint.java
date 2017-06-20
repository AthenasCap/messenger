package com.hizzit.messenger.business.messagehub.boundary;
import com.hizzit.messenger.business.messagehub.control.ProfileStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Profile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/profiles")
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
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No profiles found yet") })
    public Response getProfiles(){
        List<Profile> profiles = ps.getAllProfiles();
        GenericEntity< List<Profile> > entity;
        
        if(profiles.isEmpty()){
            return Response
                    .noContent()
                    .build();
        }else{
            entity = new GenericEntity< List<Profile> >(profiles){};
            return Response
                .ok(entity)
                .build();
        }
    }
    
    @GET
    @Path("/{profileId}")
    @ApiOperation(value = "Retrieves a profile by profileId")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "No profile with given id was found") })
    public Response getProfile(@PathParam("profileId") String profileId){
        Profile profile = ps.getProfileById(profileId);
        if(profile == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            return Response
                    .ok(profile)
                    .build();
        }
    }
    
    /**
     * Because of safety, we won't allow users to
     * change their core-attributes yet. only the profileText can be updated.
     * @param profileName
     * @param profile
     * @return 
     */
    @PUT
    @Path("/{profileId}")
    @ApiOperation(value = "Updates a profile by it's given id")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok, profile updated with new text"),
                    @ApiResponse(code = 404, message = "No profile with given id was found")})
    public Response updateProfile(@PathParam("profileId") String profileId, Profile profile){
        Profile originalProfile = ps.getProfileById(profileId);
        if(originalProfile == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            originalProfile.setProfileText(profile.getProfileText());
            Profile updatedProfile = ps.updateProfile(originalProfile);
            return Response
                    .ok(updatedProfile)
                    .build();
        }
    }
    
    @POST
    @ApiOperation(value = "Accepts a new profile")
    @ApiResponse(code = 201, message = "New profile created")
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
    @ApiResponses({ @ApiResponse(code = 204, message = "Delete successful, no content in return"),
                    @ApiResponse(code = 404, message = "Nothing found to delete")})
    public Response deleteProfile(@PathParam("profileId") String profileId){
        Profile deletedProfile = ps.removeProfile(profileId);
        if(deletedProfile == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            return Response
                    .noContent()
                    .build();
        }
    }
}
