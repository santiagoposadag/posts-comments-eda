package com.posada.santiago.postscommentseda.domain.commands;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateCommentCommand {

    private final String parentId;
    private final String content;
}
