package com.posada.santiago.postscommentseda.repository;

import com.posada.santiago.postscommentseda.data.PostsCreatedData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCreatedRepository extends ReactiveCrudRepository<PostsCreatedData, String> {
}
