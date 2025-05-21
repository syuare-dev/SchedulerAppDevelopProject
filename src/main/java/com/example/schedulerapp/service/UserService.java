package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.userDto.UserSignUpResponseDto;

public interface UserService {

    UserSignUpResponseDto signUp(String name, String email);
}
