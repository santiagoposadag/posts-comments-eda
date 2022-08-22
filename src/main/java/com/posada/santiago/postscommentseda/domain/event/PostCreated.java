package com.posada.santiago.postscommentseda.domain.event;

import com.posada.santiago.postscommentseda.routes.DomainEvent;

public class PostCreated extends DomainEvent {

    private final String title;
    private final String content;

    public PostCreated(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
