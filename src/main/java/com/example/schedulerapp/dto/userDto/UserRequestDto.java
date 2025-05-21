package com.example.schedulerapp.dto.userDto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private final String name;

    private final String email;

    public UserRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
