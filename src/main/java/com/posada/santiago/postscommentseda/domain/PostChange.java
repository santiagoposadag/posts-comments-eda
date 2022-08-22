package com.posada.santiago.postscommentseda.domain;

import com.posada.santiago.postscommentseda.domain.event.CommentCreated;
import com.posada.santiago.postscommentseda.domain.event.PostCreated;
import com.posada.santiago.postscommentseda.routes.DomainEvent;
import com.posada.santiago.postscommentseda.generic.EventChange;

import java.util.function.Consumer;

public class PostChange extends EventChange {
    public PostChange(Post post){
        Consumer<PostCreated> postCreated = (PostCreated event) -> {
            post.id = event.parentId;
            post.title = event.getTitle();
            post.content = event.getContent();
        };

        Consumer<CommentCreated> commentCreated = (CommentCreated event) -> {
            Comment comment = new Comment(event.parentId, event.getContent());
            post.comments.add(comment);
        };

        DomainEvent event = new PostCreated("title", "content");


        listen(postCreated);

        listen(commentCreated);
    }
}
