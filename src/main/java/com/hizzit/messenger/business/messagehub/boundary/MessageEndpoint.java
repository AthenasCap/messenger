package com.hizzit.messenger.business.messagehub.boundary;
import com.hizzit.messenger.business.messagehub.control.MessageFilterBean;
import com.hizzit.messenger.business.messagehub.control.MessageFilters;
import com.hizzit.messenger.business.messagehub.control.MessageStore;
import com.hizzit.messenger.business.messagehub.control.UUIDgenerator;
import com.hizzit.messenger.business.messagehub.entity.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.util.List;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.MediaType;

@Path("/messages")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api
public class MessageEndpoint {
    
    @Inject
    MessageStore ms;
    
    @Inject
    MessageFilters mf;
    
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
    @ApiResponse(code = 400, message = "Invalid input")
    public Message addMessage(Message message){
        message.setId(UUIDgenerator.generate());
        return ms.addMessage(message);
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
    /*
    @Path("/{messageId}/comments")
    public CommentEndpoint getCommentEndpoint(){
        return new CommentEndpoint();
    }
    */
}