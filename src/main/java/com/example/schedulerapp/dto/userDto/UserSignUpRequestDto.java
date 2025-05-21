package com.example.schedulerapp.dto.userDto;

import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    private final String name;

    private final String email;

    public UserSignUpRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
