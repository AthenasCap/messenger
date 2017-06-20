package com.hizzit.messenger.business.messagehub.boundary;

import com.hizzit.messenger.business.messagehub.control.CommentStore;
import com.hizzit.messenger.business.messagehub.control.MessageStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Comment;
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
    @ApiOperation(value = "commentEndpoint-subressource - (messages/{messageId}/comments/) Retrieves all comments for a message by messageId")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No comments found yet") })
    public Response getAllComments(@PathParam("messageId") String messageId){
        List<Comment> comments = cs.getAllCommentsFromMessageId(messageId);
        GenericEntity< List<Comment> > entity;

         if(comments.isEmpty()){
            return Response
                    .noContent()
                    .build();
        }else{
            entity = new GenericEntity< List<Comment> >(comments){};
            return Response
                .ok(entity)
                .build();
        }
    }
    
    @GET
    @Path("{commentId}")
    @ApiOperation(value = "commentEndpoint-subressource - Retrieves a single comment by it's owning message's id and the commentId")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "No comment with given messageId/commentId was found") })
    public Response getComment(@PathParam("messageId") String messageId, @PathParam("commentId") String commentId){
        Comment comment = cs.getComment(messageId, commentId);
        if(comment == null){
            Response
                .status(Response.Status.NOT_FOUND)
                .build();
        }
        return Response
                .ok(comment)
                .build();
    }
    
    @PUT
    @Path("/{commentId}")
    @ApiOperation(value = "commentEndpoint-subressource - Updates a single comment by it's owning message's id and the commentId")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok, comment updated with new text"),
                    @ApiResponse(code = 404, message = "No Comment with given ids was found")})
    public Response updateComment(@PathParam("messageId") String messageId, @PathParam("commentId") String commentId, Comment comment){
        Comment originalComment = cs.getComment(messageId, commentId);
        
        if(originalComment == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            originalComment.setCommentText(comment.getCommentText());
            Comment updatedComment = cs.updateComment(messageId, originalComment);
            return Response
                    .ok(updatedComment)
                    .build();
        }
    }
    
    @POST
    @ApiOperation(value = "commentEndpoint-subressource - Accepts a new comment by messageId to indentify the owning message.")
    @ApiResponse(code = 201, message = "Created")
    public Response addComment(@PathParam("messageId") String messageId, Comment comment, @Context UriInfo uriInfo){
       comment.setId(UUIDgenerator.generate());
       Comment newComment = cs.addComment(messageId, comment);
       URI uri = uriInfo.getAbsolutePathBuilder().path(newComment.getId()).build();

       return Response
               .created(uri)
               .build();
    }

    @DELETE
    @Path("/{commentId}")
    @ApiOperation(value = "commentEndpoint-subressource - Deletes a single comment by messageId and commentId")
    @ApiResponses({ @ApiResponse(code = 204, message = "Delete successful, no content in return"),
                    @ApiResponse(code = 404, message = "Nothing found to delete")})
    public Response deleteComment(@PathParam("messageId") String messageId, @PathParam("commentId}") String commentId){
        cs.removeComment(messageId, commentId);
        
         Comment deletedComment = cs.removeComment(messageId, commentId);
        if(deletedComment == null){
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