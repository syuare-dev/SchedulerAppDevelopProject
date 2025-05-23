package com.example.schedulerapp.dto.scheduleDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank
    private final String username;

    @NotEmpty
    @Range(max=16, message = "title 은 최대 16자까지 작성할 수 있습니다.")
    private final String title;

    private final String contents;

    public CreateScheduleRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
