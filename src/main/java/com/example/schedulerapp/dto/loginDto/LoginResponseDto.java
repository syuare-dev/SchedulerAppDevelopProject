package com.example.schedulerapp.dto.loginDto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String username;
    private final String message;

    public LoginResponseDto(String username, String massage) {
        this.username = username;
        this.message = massage;
    }
}
