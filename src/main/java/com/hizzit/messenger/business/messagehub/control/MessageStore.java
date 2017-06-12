
package com.hizzit.messenger.business.messagehub.control;

import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class MessageStore {
    
    @PersistenceContext
    EntityManager em;

    @Inject 
    ProfileStore ps;
    
    public MessageStore() {

    }

    public Message getMessage(String id){
        Query query = em.createNamedQuery("Message.findById");
        query.setParameter("id", id);
        
        try {
            List rl = query.getResultList();
            for(Object m : rl){
                System.out.println("resultlist: Author:" + ((Message)m).getAuthor() + " Message:" + ((Message)m).getMessage());
            }
            return (Message) rl.get(0);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }
    
    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        Query query = em.createNamedQuery("Message.findAll");
        List resultList = query.getResultList();
        resultList.forEach(element -> messages.add((Message)element));
        return messages;
    }
    
    public Message addMessage(Message message){
        ps.getProfile(message.getAuthor()).addMessage(message);
        em.persist(message);
        return message;
    }
    
    public Message updateMessage(Message message){
        Message foundMessage = em.merge(message);
        return foundMessage; 
    }
    
    public Message removeMessage(String id){
        Message message = this.getMessage(id);
        em.remove(message);
        return message;
    }
}