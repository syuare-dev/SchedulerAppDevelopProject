package com.example.schedulerapp.dto.scheduleDto;

import com.example.schedulerapp.entity.User;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    private final User user;

    private final String title;

    private final String contents;

    public UpdateScheduleRequestDto(String title, String contents, User user) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }
}
