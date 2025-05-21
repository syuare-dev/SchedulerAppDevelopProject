package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.userDto.UserResponseDto;

public interface UserService {

    UserResponseDto signUp(String name, String email);
}
