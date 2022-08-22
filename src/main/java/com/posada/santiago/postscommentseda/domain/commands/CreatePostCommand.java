package com.posada.santiago.postscommentseda.domain.commands;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreatePostCommand {
    private final String title;
    private final String content;
}
