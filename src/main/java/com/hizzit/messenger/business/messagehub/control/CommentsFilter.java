package com.hizzit.messenger.business.messagehub.control;
import com.hizzit.messenger.business.messagehub.entity.Comment;
import java.io.Serializable;
import java.util.List;

public class CommentsFilter implements Serializable {
    public List<Comment> getCommentsPaginated(List<Comment> comments, int start, int size){
        
        if(start + size > comments.size()){
            return comments.subList(start, comments.size());//if pagesize > remaining listsize -> only show from start to last listelement
        }
        return comments.subList(start, start + size);
    }
}