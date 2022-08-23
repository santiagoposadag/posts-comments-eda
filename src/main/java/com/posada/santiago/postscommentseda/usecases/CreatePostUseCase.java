package com.posada.santiago.postscommentseda.usecases;


import com.google.gson.Gson;
import com.posada.santiago.postscommentseda.data.PostsCreatedData;
import com.posada.santiago.postscommentseda.data.StorableEvent;
import com.posada.santiago.postscommentseda.domain.Post;
import com.posada.santiago.postscommentseda.domain.commands.CreatePostCommand;
import com.posada.santiago.postscommentseda.generic.DomainEvent;
import com.posada.santiago.postscommentseda.generic.Serializer;
import com.posada.santiago.postscommentseda.repository.PostCreatedRepository;
import com.posada.santiago.postscommentseda.repository.StoredEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.PrintStream;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class CreatePostUseCase implements Function<CreatePostCommand, Mono<StorableEvent>>{


    private final StoredEventRepository eventsRepository;
    private final PostCreatedRepository postsRepository;
    private final Gson gson = new Gson();

    public CreatePostUseCase(StoredEventRepository eventsRepository, PostCreatedRepository postsRepository) {
        this.eventsRepository = eventsRepository;
        this.postsRepository = postsRepository;
    }

    @Override
    public Mono<StorableEvent> apply(CreatePostCommand createPostCommand) {
        return Mono.just(createPostCommand).map(command -> new Post(command.getTitle(), command.getContent()))
                .map(post -> post.getUncommittedChanges())
                .flatMap(changes -> {
                    StorableEvent toBeSaved = new StorableEvent(
                            null,
                            changes.get(0).parentId,
                            gson.toJson(changes.get(0)),
                            changes.get(0).getClass().getCanonicalName());
                    return postsRepository.save(new PostsCreatedData(changes.get(0).parentId)).flatMap((post) -> eventsRepository.save(toBeSaved));
                });
    }
}
