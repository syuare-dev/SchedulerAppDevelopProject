package com.example.schedulerapp.dto.commentDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentRequestDto {

    @NotNull
    @Length(min = 1, max = 100)
    private final String comment;

    private final String username;

    public CommentRequestDto(String comment, String username) {
        this.comment = comment;
        this.username = username;
    }
}
