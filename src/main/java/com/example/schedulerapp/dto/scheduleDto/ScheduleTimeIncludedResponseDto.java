package com.example.schedulerapp.dto.scheduleDto;

import com.example.schedulerapp.dto.commentDto.CommentEntityDto;
import com.example.schedulerapp.entity.CommentEntity;
import com.example.schedulerapp.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleTimeIncludedResponseDto {
    private final Long id;

    private final String title;

    private final String contents;

    private final String username;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    // 해당 일정에 저장된 댓글 리스트
    private final List<CommentEntityDto> comments;

    public static ScheduleTimeIncludedResponseDto toDto (Schedule schedule) {
        return new ScheduleTimeIncludedResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getComments().stream().map(CommentEntity::toDto).toList()
        );
    }
}
