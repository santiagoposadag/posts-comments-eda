package com.posada.santiago.postscommentseda.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    public String postId;
    private String content;
}
