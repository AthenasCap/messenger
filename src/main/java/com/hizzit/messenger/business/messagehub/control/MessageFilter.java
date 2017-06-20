package com.hizzit.messenger.business.messagehub.control;

import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

public class MessageFilter {
    
    @Inject
    MessageStore ms;
    
    public List<Message> getAllMessagesForYear(int year){
        List<Message> messagesForYear = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(Message message : ms.getAllMessages()){
            cal.setTime(message.getCreated());
            if(cal.get(Calendar.YEAR) == year){
                messagesForYear.add(message);
            }         
        }
        return messagesForYear;
    }  
    
    public List<Message> getMessagesPaginated(List<Message> messages, int start, int size){
        if(start + size > messages.size()){
            return messages.subList(start, messages.size());//if pagesize > remaining listsize -> only show from start to last listelement
        }
        return messages.subList(start, start + size);
    }
}
