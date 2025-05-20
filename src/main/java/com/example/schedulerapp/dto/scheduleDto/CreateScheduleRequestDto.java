package com.example.schedulerapp.dto.scheduleDto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private final String username;

    private final String title;

    private final String contents;

    public CreateScheduleRequestDto(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
