package com.example.schedulerapp.dto.userDto;

import lombok.Getter;

@Getter
public class UserSignUpResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    public UserSignUpResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
