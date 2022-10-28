package com.posada.santiago.postscommentseda.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class EventChange {
    protected Set<Consumer<? super DomainEvent>> listeners = new HashSet();

    public EventChange() {
    }

    protected void listen(Consumer<? extends DomainEvent> changeEvent) {
        this.listeners.add((Consumer<? super DomainEvent>) changeEvent);
    }
}
