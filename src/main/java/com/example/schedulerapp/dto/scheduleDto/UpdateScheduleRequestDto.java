package com.example.schedulerapp.dto.scheduleDto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    private final String username;

    private final String title;

    private final String contents;

    public UpdateScheduleRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
