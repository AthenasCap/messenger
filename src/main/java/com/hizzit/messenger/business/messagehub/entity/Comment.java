package com.hizzit.messenger.business.messagehub.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@NamedQueries({
@NamedQuery(name="Comment.findAllByAuthor", query="SELECT c FROM Comment c WHERE c.author = :author"), 
@NamedQuery(name="Comment.findById", query="SELECT c FROM Comment c WHERE c.id = :id"),
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
})
@Entity
public class Comment implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String commentText;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private String author;
    
    @XmlTransient
    private Message message;
    
    public Comment(){
        
    }

    public Comment(long id, String commentText, Date created, String author) {
        this.id = id;
        this.commentText = commentText;
        this.created = created;
        this.author = author;
    }
    
    @PrePersist
    public void createdAt(){
        this.created = new Date();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
