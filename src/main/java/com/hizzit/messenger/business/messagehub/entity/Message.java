package com.hizzit.messenger.business.messagehub.entity;

import com.hizzit.messenger.business.profilehub.entity.Profile;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m order by m.created desc"), 
@NamedQuery(name="Message.findById", query="SELECT m FROM Message m WHERE m.id = :id")
})
@Entity
public class Message implements Serializable{
    
    @Id
    private String id;
    private String messageText;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private String author;
    
    @XmlElement
    //@XmlInverseReference(mappedBy="messages") //only needed to map jpa relations in the json objects..
    @XmlTransient //ignore for xml/json-representations
    @ManyToOne
    private Profile profile;
    
    @XmlElement
    //@XmlInverseReference(mappedBy="messageText")
    @XmlTransient //ignore for xml/json-representations
    @OneToMany(cascade=ALL, mappedBy = "message")
    private List<Comment> comments;

    public Message() {
   
    }

    public Message(String id, String message, String author) {
        this.id = id;
        this.messageText = message;
        this.author = author;
    }
    
    @PrePersist
    public void createdAt(){
        this.created = new Date();
    }
    
    /**
     * Keeps the relation between messageText and comment in sync.
     * @param comment 
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        if(comment.getMessage()!= this){
            comment.setMessage(this);
        }
    }  
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String message) {
        this.messageText = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}