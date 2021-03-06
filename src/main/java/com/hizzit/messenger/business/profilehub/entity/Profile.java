package com.hizzit.messenger.business.profilehub.entity;
import com.hizzit.messenger.business.messagehub.entity.Message;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
@NamedQuery(name="Profile.findAll", query="SELECT p FROM Profile p order by p.created desc"), 
@NamedQuery(name="Profile.findById", query="SELECT p FROM Profile p WHERE p.id = :profileId"),
@NamedQuery(name="Profile.findByProfileName", query="SELECT p FROM Profile p WHERE p.profileName = :profileName")
})
@Entity
public class Profile implements Serializable{
    
    @Id
    private String id;
    @Column(unique = true)
    private String profileName;
    private String firstName;
    private String lastName;
    private String profileText;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
     
    @XmlElement
    //@XmlInverseReference(mappedBy="profile")
    @XmlTransient //ignore for xml/json-representations
    @OneToMany(cascade=ALL, mappedBy = "profile")
    private List<Message> messages;

    public Profile() {
    
    }

    public Profile(String id, String profileName, String firstName, String lastName) {
        this.id = id;
        this.profileName = profileName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
 
    
    /**
     * Keeps the relation between Message and Profile
     * in sync.
     * @param message 
     */
    public void addMessage(Message message) {
        this.messages.add(message);
        if(message.getProfile()!= this){
            message.setProfile(this);
        }
    }

    @PrePersist
    public void createdAt(){
        this.created = new Date();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }  

    public String getProfileText() {
        return profileText;
    }

    public void setProfileText(String profileText) {
        this.profileText = profileText;
    }
    
    
}