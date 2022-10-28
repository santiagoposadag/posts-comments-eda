package com.posada.santiago.postscommentseda.domain;

import com.posada.santiago.postscommentseda.domain.event.CommentCreated;
import com.posada.santiago.postscommentseda.domain.event.PostCreated;
import com.posada.santiago.postscommentseda.generic.DomainEvent;
import com.posada.santiago.postscommentseda.generic.AggregateRoot;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Post extends AggregateRoot {

    protected String id;
    protected String title;
    protected String content;
    protected List<Comment> comments = new ArrayList<>();

    public Post(String title, String content){
        subscribe(new PostChange(this));
        appendChange(new PostCreated(title, content)).apply();
    }

    private Post(String id){
        this.id = id;
        subscribe(new PostChange(this));
    }

    public static Post from(String id, List<DomainEvent> events){
        Post post = new Post(id);
        events.forEach(event -> post.applyEvent(event));
        return post;
    }

    public void addComment(String parentId, String content){
        appendChange(new CommentCreated(parentId, content)).apply();
    }
}

