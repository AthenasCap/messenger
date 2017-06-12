package com.hizzit.messenger.business.messagehub.control;
import java.util.UUID;


public class UUIDgenerator {
    
    private UUIDgenerator() {
    }
    
    public static String generate(){
        return UUID.randomUUID().toString();
    }  
}
