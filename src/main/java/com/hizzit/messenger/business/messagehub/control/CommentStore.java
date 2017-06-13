package com.hizzit.messenger.business.messagehub.control;

import com.hizzit.messenger.business.messagehub.entity.Comment;
import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.ArrayList;
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
        List<Comment> comments = new ArrayList<>();
        Query query = em.createNamedQuery("Comment.findAll");
        List resultList = query.getResultList();
        resultList.forEach(element -> comments.add((Comment)element));
        System.out.println("CommentStore----getAllComments()----return: " + comments.toString());
        return comments;
    }
    
    public List<Comment> getAllCommentsFromMessageId(String messageId){
        Message message = em.find(Message.class, messageId);
        List<Comment> comments = new ArrayList<>(message.getComments());
        
        return comments;
    }
    
    public Comment getComment(String messageId, String commentId){
        List<Comment> comments = ms.getMessage(messageId).getComments();
        
        for(Comment c : comments){
            if(c.getId().equals(commentId)){
                return c;
            }
        }
        return null;
    }
    
    public Comment addComment(String messageId, Comment comment){
        Message message = ms.getMessage(messageId);
        message.addComment(comment);
        ms.updateMessage(message);
        return comment;
    }
    
    public Comment updateComment(String messageId, Comment comment){
         this.getComment(messageId, comment.getId());
         return em.merge(comment);
    }
    
    public Comment removeComment(String messageId, String commentId){
        Comment comment = this.getComment(messageId, commentId);
        em.remove(comment);
        return comment;
    }
}
