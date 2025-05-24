package com.example.schedulerapp.dto.scheduleDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class CreateScheduleRequestDto {

    @NotNull
    @Length(min=1, max=16)
    private final String title;

    private final String contents;

}
