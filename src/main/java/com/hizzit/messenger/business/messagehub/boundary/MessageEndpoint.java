package com.hizzit.messenger.business.messagehub.boundary;
import com.hizzit.messenger.business.messagehub.control.CommentStore;
import com.hizzit.messenger.business.messagehub.control.PaginationFilterBean;
import com.hizzit.messenger.business.messagehub.control.MessageFilter;
import com.hizzit.messenger.business.messagehub.control.MessageStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
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
 * Master Rest-Resource which has a mapping for the Subresource,
 * CommentEndpoint.
 * @author jan
 */
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class MessageEndpoint {
    
    @Inject
    MessageStore ms;
    
    @Inject
    CommentStore cs;
    
    @Inject
    MessageFilter mf;
    
    @Inject
    Instance<CommentEndpoint> ce;

    public MessageEndpoint() {
    }
    
    /**
     * Retrieves all messages. Available, filtered after year and
     paginated with start and size. 
     QueryParams are defined in PaginationFilterBean Class for clean code.
     All lists of entities are already sorted descending by date (newest up)     
     * @param filterBean
     * @return 
     */
    @GET
    @ApiOperation(value = "Retrieves all messages, (?start=x ?size=x&year=xxxx or paginated only ?start=x&size=x)")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No Messages available yet")})
    public Response getMessages(@BeanParam PaginationFilterBean filterBean){
        List<Message> messages;
        GenericEntity< List<Message> > entity; //with jersey, we can only return entities with Response. https://stackoverflow.com/a/22852881
        
        // messages?start=x&size=x&year=xxxx get messages with pagination and from a specific year
        if(filterBean.getYear() > 0 && filterBean.getStart() >= 0 && filterBean.getSize() > 0){
            List<Message> messagesOfYear = mf.getAllMessagesForYear(filterBean.getYear());
            messages = mf.getMessagesPaginated(messagesOfYear, filterBean.getStart(), filterBean.getSize());
            entity = new GenericEntity< List<Message> >(messages){};
            return Response
                    .ok(entity)
                    .build();
        }
        // messages?start=x&size=x get messages with pagination only
        if(filterBean.getStart() >= 0 && filterBean.getSize() > 0){
            messages = mf.getMessagesPaginated(ms.getAllMessages(),filterBean.getStart(), filterBean.getSize());
            entity = new GenericEntity< List<Message> >(messages){};
            return Response
                    .ok(entity)
                    .build();
        }
        
        messages = ms.getAllMessages();   
        if(messages.isEmpty()){
            return Response
                    .noContent()
                    .build();
        }else{
            entity = new GenericEntity< List<Message> >(messages){};
            return Response
                    .ok(entity)
                    .build();
        }
    }
    
    @GET
    @Path("/{messageId}")
    @ApiOperation(value = "Retrieves a single message by it's id")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "No Message with given id was found")})
    public Response getMessage(@PathParam("messageId") String id, @Context UriInfo uriInfo){
        Message message = ms.getMessage(id);
        
        if(message == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            URI uri = uriInfo.getAbsolutePathBuilder().path(message.getId()).build();
            return Response
                    .ok(message)
                    .contentLocation(uri)
                    .build();
        }
    }
    
    /**
     * Updates only the messageText.
     * @param messageId
     * @param message
     * @return 
     */
    @PUT
    @Path("/{messageId}")
    @ApiOperation(value = "Updates a single message by it's id")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ok, message updated with new text"),
                    @ApiResponse(code = 404, message = "No message with given id was found")})
    public Response updateMessage(@PathParam("messageId")String messageId, Message message){
        Message originalMessage = ms.getMessage(messageId);
        if(originalMessage == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            originalMessage.setMessageText(message.getMessageText());
            Message updatedMessage = ms.updateMessage(originalMessage);
            return Response
                    .ok(updatedMessage)
                    .build();
        }
    }
    
    @POST
    @Path("/{profileId}")
    @ApiOperation(value = "Accepts a new message")
    @ApiResponse(code = 201, message = "New message created")
    public Response addMessage(@PathParam("profileId") String profileId, Message message, @Context UriInfo uriInfo) throws URISyntaxException{
        message.setId(UUIDgenerator.generate());
        Message newMessage = ms.addMessage(profileId, message);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newMessage.getId()).build();
        
        return Response
                .created(uri)
                .entity(newMessage)
                .build();
    }
    
    @DELETE
    @Path("/{messageId}")
    @ApiOperation(value = "Deletes a single message")
    @ApiResponses({ @ApiResponse(code = 204, message = "Delete successful, no content in return"),
                    @ApiResponse(code = 404, message = "Nothing found to delete")})
    public Response deleteMessage(@PathParam("messageId") String id){
        Message deletedMessage = ms.removeMessage(id);
        if(deletedMessage == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            return Response
                    .noContent()
                    .build();
        }
    }
    
    /**
     * Mapping for the sub-resource CommentEndpoint.
     * All requests to this will be mapped to the resource.
     * @return
     */
    @Path("/{messageId}/comments")
    public CommentEndpoint getCommentEndpoint(){
        return ce.get();
    }

    public MessageStore getMs() {
        return ms;
    }

    public void setMs(MessageStore ms) {
        this.ms = ms;
    }

    public CommentStore getCs() {
        return cs;
    }

    public void setCs(CommentStore cs) {
        this.cs = cs;
    }
}