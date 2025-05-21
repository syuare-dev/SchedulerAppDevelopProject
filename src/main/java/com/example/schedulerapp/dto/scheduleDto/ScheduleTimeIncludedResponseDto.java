package com.example.schedulerapp.dto.scheduleDto;

import com.example.schedulerapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleTimeIncludedResponseDto {
    private final Long id;

    private final String title;

    private final String contents;

    private final String username;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;


    public ScheduleTimeIncludedResponseDto
            (Long id, String title, String contents, String username,
            LocalDateTime createdAt, LocalDateTime modifiedAt)
    {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ScheduleTimeIncludedResponseDto toDto (Schedule schedule) {
        return new ScheduleTimeIncludedResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
