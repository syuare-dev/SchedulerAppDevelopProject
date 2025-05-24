package com.example.schedulerapp.dto.scheduleDto;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private final String title;

    private final String contents;

    private final String username;

    public ScheduleResponseDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
