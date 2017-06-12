package com.hizzit.messenger.business.messagehub.boundary;

import com.hizzit.messenger.business.messagehub.control.CommentStore;
import com.hizzit.messenger.business.messagehub.control.MessageStore;
import com.hizzit.messenger.business.messagehub.entity.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A subresource, mapped by getCommentEndpoint() in MessageEndpoint. 
 * To achieve a nice, self explaining appearance for api-users.
 * We want to achieve with this subresource URIs like (for e.g.):
 * /messages/1/comments/1 or messages/1/comments
 * @author jan
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
@Api
@Path("/comments")
public class CommentEndpoint {
    
    @Inject
    CommentStore cs;
    
    @Inject
    MessageStore ms;

    public CommentEndpoint() {
        
    }
    /*
    @GET
    public List<Comment> getAllComments(@PathParam("messageId") long messageId){
        return cs.getAllComments(messageId);
    }
    */
    
    @ApiOperation(value = "Retrieves all comments")
    @ApiResponse(code = 400, message = "Invalid input")
    @GET
    public List<Comment> getAllComments(){
        return cs.getAllComments();
    }
    
    @ApiOperation(value = "Retrieves all comments of a message by messageId")
    @ApiResponse(code = 400, message = "Invalid input")
    @POST
    @Path("/{messageId}")
    public Comment addComment(@PathParam("messageId") long messageId, Comment comment){
       Comment persistedComment = cs.addComment(messageId, comment);
       
        return persistedComment;
    }
    
    /*
    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
        return cs.getComment(messageId, commentId);
    }
    
    @PUT
    @Path("/{commentId}")
    public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId, Comment comment){
        return cs.updateComment(messageId, comment);
    }
    
    
    @POST
    public Comment addComment(@PathParam("messageId") long messageId, Comment comment){
        return cs.addComment(messageId, comment);
    }
    
    @DELETE
    public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId}") long commentId){
        cs.removeComment(messageId, commentId);
    }
*/
}