package com.hizzit.messenger.business.messagehub.entity;

import com.hizzit.messenger.business.messagehub.entity.Message;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-13T12:35:36")
@StaticMetamodel(Profile.class)
public class Profile_ { 

    public static volatile SingularAttribute<Profile, String> profileName;
    public static volatile SingularAttribute<Profile, String> firstName;
    public static volatile SingularAttribute<Profile, String> lastName;
    public static volatile SingularAttribute<Profile, Date> created;
    public static volatile ListAttribute<Profile, Message> messages;
    public static volatile SingularAttribute<Profile, String> id;

}