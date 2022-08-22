package com.posada.santiago.postscommentseda.generic;

import com.posada.santiago.postscommentseda.routes.DomainEvent;

import java.util.*;
import java.util.function.Consumer;

public abstract class EventChangeSubscriber {
    private final List<DomainEvent> changes = new LinkedList();
    private final Set<Consumer<? super DomainEvent>> observables = new HashSet();


    public final void subscribe(EventChange eventChange) {
        this.observables.addAll(eventChange.listeners);
    }

    public final EventChangeSubscriber.ChangeApply appendChange(DomainEvent event) {
        this.changes.add(event);
        return () -> {
            this.applyEvent(event);
        };
    }

    public final void applyEvent(DomainEvent domainEvent) {
        this.observables.forEach((consumer) -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException var6) {
            }

        });
    }

    @FunctionalInterface
    public interface ChangeApply {
        void apply();
    }
}
