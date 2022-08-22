package com.posada.santiago.postscommentseda.repository;

import com.posada.santiago.postscommentseda.data.StorableEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StoredEventRepository extends ReactiveMongoRepository<StorableEvent, String> {

    Flux<StorableEvent> findAllByParentId(String parentId);
}
