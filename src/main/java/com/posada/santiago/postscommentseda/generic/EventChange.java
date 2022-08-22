package com.posada.santiago.postscommentseda.generic;

import com.posada.santiago.postscommentseda.routes.DomainEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class EventChange{
    protected Set<Consumer<? super DomainEvent>> listeners = new HashSet();

    public EventChange() {
    }

    protected void listen(Consumer<? super DomainEvent> listener){
        this.listeners.add(listener);
    }
}
