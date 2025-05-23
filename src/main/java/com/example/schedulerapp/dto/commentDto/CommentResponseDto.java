package com.example.schedulerapp.dto.commentDto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String comment;

    private final String username;

    public CommentResponseDto(Long id, String comment, String username) {
        this.id = id;
        this.comment = comment;
        this.username = username;
    }
}
