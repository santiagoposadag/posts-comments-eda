package com.posada.santiago.postscommentseda.domain;

import com.posada.santiago.postscommentseda.domain.event.CommentCreated;
import com.posada.santiago.postscommentseda.domain.event.PostCreated;
import com.posada.santiago.postscommentseda.generic.EventChange;

public class PostChange extends EventChange {
    public PostChange(Post post){

        listen((PostCreated event) -> {
            post.id = event.aggregateRootId;
            post.title = event.getTitle();
            post.content = event.getContent();
        });

        listen((CommentCreated event) -> {
            Comment comment = new Comment(event.aggregateRootId, event.getContent());
            post.comments.add(comment);
        });
    }
}
