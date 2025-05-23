package com.example.schedulerapp.dto.commentDto;

import com.example.schedulerapp.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentTimeIncludeResponseDto {

    private final Long id;

    private final String username;

    private final String scheduleTitle;

    private final String comment;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public static CommentTimeIncludeResponseDto toDto (CommentEntity comment) {
        return new CommentTimeIncludeResponseDto(
                comment.getId(),
                comment.getUser().getName(),
                comment.getSchedule().getTitle(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
