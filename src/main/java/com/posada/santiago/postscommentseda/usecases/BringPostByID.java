package com.posada.santiago.postscommentseda.usecases;

import com.google.gson.Gson;
import com.posada.santiago.postscommentseda.domain.Post;
import com.posada.santiago.postscommentseda.generic.DomainEvent;
import com.posada.santiago.postscommentseda.repository.StoredEventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class BringPostByID implements Function<String, Mono<Post>> {
    private final StoredEventRepository eventsRepository;
    private final Gson gson = new Gson();

    public BringPostByID(StoredEventRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }


    @Override
    public Mono<Post> apply(String aggregateId) {
        return eventsRepository.findAllByParentId(aggregateId)
                .collectList()
                .map(events -> {
                    List<DomainEvent> domainEvents = new ArrayList<>();
                    events.forEach(event -> {
                        try {
                            domainEvents.add((DomainEvent) gson.fromJson(event.getBody(), Class.forName(event.getType())));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    Post post = Post.from(events.get(0).getParentId(), domainEvents);
                    return post;
                });
    }
}
