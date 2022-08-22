package com.posada.santiago.postscommentseda.domain;

import com.posada.santiago.postscommentseda.generic.EventChangeSubscriber;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Post extends EventChangeSubscriber {

    protected String id;
    protected String title;
    protected String content;
    protected List<Comment> comments = new ArrayList<>();

    public Post(String title, String content){

    }
}
