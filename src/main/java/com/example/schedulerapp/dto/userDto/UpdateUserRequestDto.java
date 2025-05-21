package com.example.schedulerapp.dto.userDto;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String name;

    private final String email;

    public UpdateUserRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
