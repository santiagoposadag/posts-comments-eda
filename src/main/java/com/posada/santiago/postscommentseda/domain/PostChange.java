package com.posada.santiago.postscommentseda.domain;

import com.posada.santiago.postscommentseda.domain.event.CommentCreated;
import com.posada.santiago.postscommentseda.domain.event.PostCreated;
import com.posada.santiago.postscommentseda.generic.DomainEvent;
import com.posada.santiago.postscommentseda.generic.EventChange;

import java.util.function.Consumer;

public class PostChange extends EventChange {
    public PostChange(Post post){

        listen((PostCreated event) -> {
            post.id = event.parentId;
            post.title = event.getTitle();
            post.content = event.getContent();
        });

        listen((CommentCreated event) -> {
            Comment comment = new Comment(event.parentId, event.getContent());
            post.comments.add(comment);
        });
    }
}
