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

    private final Long scheduleId;

    private final String scheduleTitle;

    private final String comment;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    /**
     * CommentEntity 객체 데이터를 CommentTimeIncludeResponseDto 객체로 변환하는 메서드(toDto)
     * @param comment 변환할 CommentEntity 객체
     * @return 변환된 CommentTimeIncludeResponseDto 객체
     */

    public static CommentTimeIncludeResponseDto toDto (CommentEntity comment) {
        return new CommentTimeIncludeResponseDto(
                comment.getId(),
                comment.getUser().getName(),
                comment.getSchedule().getId(),
                comment.getSchedule().getTitle(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
