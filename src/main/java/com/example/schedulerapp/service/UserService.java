package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.userDto.UserSignUpResponseDto;
import com.example.schedulerapp.dto.userDto.UserTimeIncludeResponseDto;

import java.util.List;

public interface UserService {

    UserSignUpResponseDto signUp(String name, String email);

    List<UserTimeIncludeResponseDto> findAllUsers();

    UserTimeIncludeResponseDto findByIdUser(Long id);
}
