package com.posada.santiago.postscommentseda.usecases;


import com.google.gson.Gson;
import com.posada.santiago.postscommentseda.domain.Post;
import com.posada.santiago.postscommentseda.generic.DomainEvent;
import com.posada.santiago.postscommentseda.generic.Serializer;
import com.posada.santiago.postscommentseda.repository.PostCreatedRepository;
import com.posada.santiago.postscommentseda.repository.StoredEventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class BringAllPostsUseCase implements Supplier<Flux<Post>> {
    private final StoredEventRepository eventsRepository;
    private final PostCreatedRepository postsRepository;
    private final Gson gson = new Gson();

    public BringAllPostsUseCase(StoredEventRepository eventsRepository, PostCreatedRepository postsRepository) {
        this.eventsRepository = eventsRepository;
        this.postsRepository = postsRepository;
    }


    @Override
    public Flux<Post> get() {
        return postsRepository.findAll()
//                .switchIfEmpty(Mono.error(new Throwable("you haven't created any post")))
                .flatMap(postsCreatedData -> eventsRepository
                .findAllByParentId(postsCreatedData.getPostCreatedId())
                .collectList()
                .map(listOfStorableEvents -> {
                    List<DomainEvent> domainEvents = new ArrayList<>();
                    listOfStorableEvents.forEach(storableEvent -> {
                        try {
                            domainEvents.add((DomainEvent) gson.fromJson(storableEvent.getBody(), Class.forName(storableEvent.getType())));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    Post post = Post.from(listOfStorableEvents.get(0).getParentId(), domainEvents);
                    return post;
                }));
    }
}
