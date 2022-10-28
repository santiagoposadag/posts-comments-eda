package com.posada.santiago.postscommentseda.generic;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent implements Serializable {

    public final Instant when;
    public final String aggregateRootId;

    public DomainEvent() {
        this.when = Instant.now();
        this.aggregateRootId = UUID.randomUUID().toString();
    }

    public DomainEvent(String uuid, Instant when) {
        this.when = when;
        this.aggregateRootId = uuid;
    }

    public DomainEvent(String uuid) {
        this.when = Instant.now();
        this.aggregateRootId = uuid;
    }
}

