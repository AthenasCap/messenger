package com.hizzit.messenger.business.messagehub.boundary;
import com.hizzit.messenger.business.messagehub.control.CommentStore;
import com.hizzit.messenger.business.messagehub.control.MessageFilterBean;
import com.hizzit.messenger.business.messagehub.control.MessageFilters;
import com.hizzit.messenger.business.messagehub.control.MessageStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/messages")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class MessageEndpoint {
    
    @Inject
    MessageStore ms;
    
    @Inject
    CommentStore cs;
    
    @Inject
    MessageFilters mf;
    
    @Inject
    Instance<CommentEndpoint> ce;

    public MessageEndpoint() {
    }
    
    
    
    @GET
    @ApiOperation(value = "Retrieves all messages")
    @ApiResponse(code = 400, message = "Invalid input")
    public List<Message> getMessages(@BeanParam MessageFilterBean filterBean){//Queryparams defined in MessageFilterBean
        if(filterBean.getYear() > 0){
            return mf.getAllMessagesForYear(filterBean.getYear());
        }
        if(filterBean.getStart() >= 0 && filterBean.getSize() > 0){
            return mf.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
        }
        return ms.getAllMessages();
    }
    
    @GET
    @Path("/{messageId}")
    @ApiOperation(value = "Retrieves a message by id")
    @ApiResponse(code = 400, message = "Invalid input")
    public Message getMessage(@PathParam("messageId") String id){
        return ms.getMessage(id);
    }
    
    @PUT
    @Path("/{messageId}")
    @ApiOperation(value = "Updates a message")
    @ApiResponse(code = 400, message = "Invalid input")
    public Message updateMessage(@PathParam("messageId")String messageId, Message message){
        message.setId(messageId);
        return ms.updateMessage(message);
    }
    
    @POST
    @ApiOperation(value = "Excepts a new message")
    @ApiResponse(code = 201, message = "Created")
    public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException{
        
        message.setId(UUIDgenerator.generate());
        Message newMessage = ms.addMessage(message);
        
        URI uri = uriInfo.getAbsolutePathBuilder().path(newMessage.getId()).build();
        
        return Response
                .created(uri)
                .entity(newMessage)
                .build();
    }
    
    @DELETE
    @Path("/{messageId}")
    @ApiOperation(value = "Deletes a new message")
    @ApiResponse(code = 400, message = "Invalid input")
    public void deleteMessage(@PathParam("messageId") String id){
        ms.removeMessage(id);
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