package com.posada.santiago.postscommentseda.routes;

import com.posada.santiago.postscommentseda.domain.Post;
import com.posada.santiago.postscommentseda.domain.commands.CreateCommentCommand;
import com.posada.santiago.postscommentseda.domain.commands.CreatePostCommand;
import com.posada.santiago.postscommentseda.usecases.BringAllPostsUseCase;
import com.posada.santiago.postscommentseda.usecases.BringPostByID;
import com.posada.santiago.postscommentseda.usecases.CreateCommentUseCase;
import com.posada.santiago.postscommentseda.usecases.CreatePostUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Controller {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase useCase){
        Function<CreatePostCommand, Mono<ServerResponse>> executor =
                command -> useCase.apply(command)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(POST("/create/post")
                .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CreatePostCommand.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> createComment(CreateCommentUseCase useCase){
        Function<CreateCommentCommand, Mono<ServerResponse>> executor =
                command -> useCase.apply(command)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(POST("/create/comment")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CreateCommentCommand.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllPosts(BringAllPostsUseCase useCase){
        return route(GET("/bring/all/posts"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), Post.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getPostByID(BringPostByID useCase){
        return route(GET("/bring/{id}"),
                request -> useCase.apply(request.pathVariable("id")).flatMap(post -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(post)));
    }
}
