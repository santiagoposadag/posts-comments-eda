package com.posada.santiago.postscommentseda.data;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class PostsCreatedData {
    @Id
    private String id;
    private String postCreatedId;

    public PostsCreatedData(String postCreatedId){
        this.postCreatedId = postCreatedId;
    }
}
