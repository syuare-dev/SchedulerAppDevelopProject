package com.example.schedulerapp.dto.commentDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    @NotNull
    @Length(min = 1, max = 255)
    private final String comment;

}
