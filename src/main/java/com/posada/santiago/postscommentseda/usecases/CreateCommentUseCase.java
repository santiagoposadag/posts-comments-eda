package com.posada.santiago.postscommentseda.usecases;

import com.google.gson.Gson;
import com.posada.santiago.postscommentseda.data.StorableEvent;
import com.posada.santiago.postscommentseda.domain.Post;
import com.posada.santiago.postscommentseda.domain.commands.CreateCommentCommand;
import com.posada.santiago.postscommentseda.domain.commands.CreatePostCommand;
import com.posada.santiago.postscommentseda.generic.DomainEvent;
import com.posada.santiago.postscommentseda.repository.StoredEventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class CreateCommentUseCase implements Function<CreateCommentCommand, Mono<StorableEvent>> {
    private final StoredEventRepository eventsRepository;
    private final Gson gson = new Gson();

    public CreateCommentUseCase(StoredEventRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Mono<StorableEvent> apply(CreateCommentCommand createCommentCommand) {
        return Mono.just(createCommentCommand)
                .flatMap(command -> eventsRepository
                        .findAllByParentId(command.getParentId())
                        .collectList()
                        .flatMap(storedEvents ->{
                            List<DomainEvent> events = new ArrayList<>();
                            storedEvents.forEach(
                                    storedEvent -> {
                                        try {
                                            events.add((DomainEvent) gson.fromJson(storedEvent.getBody(), Class.forName(storedEvent.getType())));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );
                            Post post = Post.from(createCommentCommand.getParentId(), events);
                            post.addComment(createCommentCommand.getParentId(), createCommentCommand.getContent());
                            var change = post.getUncommittedChanges().get(0);
                            StorableEvent storableEvent = new StorableEvent(
                                    null,
                                    change.parentId,
                                    gson.toJson(change),
                                    change.getClass().getCanonicalName()
                            );
                            return eventsRepository.save(storableEvent);
                        })
                );
    }
}
