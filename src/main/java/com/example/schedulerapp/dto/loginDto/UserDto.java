package com.example.schedulerapp.dto.loginDto;

import lombok.Getter;

@Getter
public class UserDto {
    private Long id;
    private String name;

    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
