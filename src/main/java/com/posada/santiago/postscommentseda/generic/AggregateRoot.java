package com.posada.santiago.postscommentseda.generic;

import java.util.*;
import java.util.function.Consumer;

public abstract class AggregateRoot {
    private final List<DomainEvent> changes = new LinkedList();
    private final Set<Consumer<? super DomainEvent>> observables = new HashSet();
    /**
    (parametro)=> return response Function
     (parametro)=> {} Consumer
     ()=> return response Supplier
     (parametro) => return boolean

     **/



    public final void subscribe(EventChange eventChange) {
        this.observables.addAll(eventChange.listeners);
    }

    public final AggregateRoot.ChangeApply appendChange(DomainEvent event) {
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
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changes);
    }
    public void markChangesAsCommitted() {
        changes.clear();
    }

    @FunctionalInterface
    public interface ChangeApply {
        void apply();
    }
}
