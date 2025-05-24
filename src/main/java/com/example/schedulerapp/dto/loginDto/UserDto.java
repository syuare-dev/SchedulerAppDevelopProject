package com.example.schedulerapp.dto.loginDto;

import lombok.Getter;

@Getter
public class UserDto {
    private final Long id;
    private final String name;

    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
