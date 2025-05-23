package com.example.schedulerapp.dto.scheduleDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank
    private final String username;

    @NotNull
    @Length(min=1, max=16)
    private final String title;

    private final String contents;

    public CreateScheduleRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
