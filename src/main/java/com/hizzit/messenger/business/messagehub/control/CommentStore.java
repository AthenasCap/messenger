package com.hizzit.messenger.business.messagehub.control;

import com.hizzit.messenger.business.messagehub.entity.Comment;
import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class CommentStore {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    MessageStore ms;

    public CommentStore() {
    }
    
    public String test(){
        return "test die bohne wech";
    }
    
    public List<Comment> getAllComments(){
        Query query = em.createNamedQuery("Comment.findAll"); 
        return query.getResultList();
    }
    
    public List<Comment> getAllCommentsFromMessageId(long messageId){
        Message message = em.find(Message.class, messageId);
        List<Comment> comments = message.getComments();
        
        return comments;
    }
    
    public Comment getComment(long messageId, long commentId){
        List<Comment> comments = ms.getMessage(messageId).getComments();
        
        for(Comment c : comments){
            if(c.getId() == commentId){
                return c;
            }
        }
        return null;
    }
    
    public Comment addComment(long messageId, Comment comment){
        em.persist(comment);
        Message message = ms.getMessage(messageId);
        message.addComment(comment);
        //ms.updateMessage(message);
        
        
        return comment;
    }
    
    public Comment updateComment(long messageId, Comment comment){
         this.getComment(messageId, comment.getId());
         return em.merge(comment);
    }
    
    public Comment removeComment(long messageId, long commentId){
        Comment comment = this.getComment(messageId, commentId);
        em.remove(comment);
        return comment;
    }
}
