package com.hizzit.messenger.business.messagehub.entity;

import com.hizzit.messenger.business.messagehub.entity.Comment;
import com.hizzit.messenger.business.messagehub.entity.Profile;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-12T11:55:49")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile ListAttribute<Message, Comment> comments;
    public static volatile SingularAttribute<Message, Date> created;
    public static volatile SingularAttribute<Message, String> author;
    public static volatile SingularAttribute<Message, Profile> profile;
    public static volatile SingularAttribute<Message, Long> id;
    public static volatile SingularAttribute<Message, String> message;

}