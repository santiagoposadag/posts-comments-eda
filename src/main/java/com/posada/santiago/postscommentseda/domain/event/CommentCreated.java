package com.posada.santiago.postscommentseda.domain.event;


import com.posada.santiago.postscommentseda.generic.DomainEvent;

public class CommentCreated extends DomainEvent {

    private final String content;

    public CommentCreated(String id, String content) {
        super(id);
        this.content = content;

    }

    public String getContent() {
        return content;
    }
}
