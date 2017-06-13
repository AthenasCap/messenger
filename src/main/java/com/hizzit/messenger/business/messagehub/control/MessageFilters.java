package com.hizzit.messenger.business.messagehub.control;

import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

public class MessageFilters {
    
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
    
    public List<Message> getAllMessagesPaginated(int start, int size){
        ArrayList<Message> list = new ArrayList<Message>(ms.getAllMessages());  
        if(start + size > list.size()){
            //return new ArrayList<Message>();
            return list.subList(start, list.size());//if pagesize > remaining listsize -> only show from start to last listelement
        }
        return list.subList(start, start + size);
    }
}
