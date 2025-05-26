package com.example.schedulerapp.dto.commentDto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentEntityDto {

    private Long id;
    private String comment;
    private String username;
    private Long scheduleId;

}
