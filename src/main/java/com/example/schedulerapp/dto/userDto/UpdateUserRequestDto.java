package com.example.schedulerapp.dto.userDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = false) // 정의되지 않은 필드가 있을 경우 예외 발생
public class UpdateUserRequestDto {

    @NotBlank(message = "수정할 name 은 필수값 입니다.")
    private String name;

    public UpdateUserRequestDto(String name) {
        this.name = name;
    }

    public UpdateUserRequestDto() {
    }
}
