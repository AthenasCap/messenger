package com.hizzit.messenger.business.messagehub.boundary;

import com.hizzit.messenger.business.messagehub.control.CommentStore;
import com.hizzit.messenger.business.messagehub.control.MessageStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.net.URI;
import java.util.ArrayList;
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

/**
 * A subresource, mapped by getCommentEndpoint() in MessageEndpoint. 
 * To achieve a nice, self explaining appearance for api-users.
 * We want to achieve with this subresource URIs like (for e.g.):
 * /messages/1/comments/1 or messages/1/comments
 * @author jan
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class CommentEndpoint {
   
    @Inject
    CommentStore cs;
    
    @Inject
    MessageStore ms;

    public CommentEndpoint() {
    }

    @GET
    public List<Comment> getAllComments(@PathParam("messageId") String messageId){
        ArrayList<Comment> comments = new ArrayList<>(cs.getAllCommentsFromMessageId(messageId));

        return comments;
    }
    
    @GET
    @Path("{commentId}")
    public Response getComment(@PathParam("messageId") String messageId, @PathParam("commentId") String commentId){
        Comment comment = cs.getComment(messageId, commentId);
        if(comment == null){
            System.out.println("CommentEndpoint----Comment is null!");
            Response
                .status(Response.Status.NOT_FOUND)
                .build();
        }
        return Response
                .ok(comment)
                .build();
    }
    
    @ApiOperation(value = "Retrieves all comments of a message by messageId")
    @ApiResponse(code = 201, message = "Created")
    @POST
    public Response addComment(@PathParam("messageId") String messageId, Comment comment, @Context UriInfo uriInfo){
       comment.setId(UUIDgenerator.generate()); 
       Comment newComment = cs.addComment(messageId, comment);
       
       URI uri = uriInfo.getAbsolutePathBuilder().path(newComment.getId()).build();

       return Response
               .created(uri)
               .build();
    }

    @PUT
    @Path("/{commentId}")
    public Comment updateComment(@PathParam("messageId") String messageId, @PathParam("commentId") String commentId, Comment comment){
        return cs.updateComment(messageId, comment);
    }

    @DELETE
    public void deleteComment(@PathParam("messageId") String messageId, @PathParam("commentId}") String commentId){
        cs.removeComment(messageId, commentId);
    }
}