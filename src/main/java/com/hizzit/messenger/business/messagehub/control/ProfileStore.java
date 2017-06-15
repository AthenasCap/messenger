package com.hizzit.messenger.business.messagehub.control;

import com.hizzit.messenger.business.messagehub.entity.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ProfileStore {

    @PersistenceContext
    EntityManager em;
    
    public ProfileStore() {
    }
    
    public Profile getProfileById(String profileId){
        Query query = em.createNamedQuery("Profile.findById");
        query.setParameter("profileId", profileId);
        
        try {
            List rl = query.getResultList();
            for(Object m : rl){
                System.out.println("resultlist: " + ((Profile)m).getId() + " Fname:" + ((Profile)m).getFirstName() + " Lname:" + ((Profile)m).getLastName());
            }
            return (Profile) rl.get(0);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }
    
    public Profile getProfileByName(String profileName){
        Query query = em.createNamedQuery("Profile.findByProfileName");
        query.setParameter("profileName", profileName);
        
        try {
            List rl = query.getResultList();
            for(Object m : rl){
                System.out.println("resultlist: Fname:" + ((Profile)m).getFirstName() + " Lname:" + ((Profile)m).getLastName());
            }
            return (Profile) rl.get(0);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    public List<Profile> getAllProfiles(){
        List<Profile> profiles = new ArrayList<>();
        Query query = em.createNamedQuery("Profile.findAll");
        List resultList = query.getResultList();
        resultList.forEach(element -> profiles.add((Profile)element));
        return profiles;
    }
    
    public Profile addProfile(Profile profile){
        em.persist(profile);
        return profile;  
    }
    
    public Profile updateProfile(Profile profile){
        Profile foundProfile = em.merge(profile);
        return foundProfile; 
    }
    
    public Profile removeProfile(String profileId){
        Profile profile = getProfileById(profileId);
        em.remove(profile);
        return profile;
    }
}
