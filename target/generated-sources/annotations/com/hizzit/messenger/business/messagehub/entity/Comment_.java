package com.hizzit.messenger.business.messagehub.entity;

import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-20T12:47:58")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, Date> created;
    public static volatile SingularAttribute<Comment, String> author;
    public static volatile SingularAttribute<Comment, String> id;
    public static volatile SingularAttribute<Comment, Message> message;
    public static volatile SingularAttribute<Comment, String> commentText;

}