package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.loginDto.LoginRequestDto;
import com.example.schedulerapp.dto.loginDto.UserDto;
import com.example.schedulerapp.dto.userDto.UserResponseDto;
import com.example.schedulerapp.dto.userDto.UserSignUpResponseDto;
import com.example.schedulerapp.dto.userDto.UserTimeIncludeResponseDto;

import java.util.List;

public interface UserService {

    UserSignUpResponseDto signUp(String name, String email, String password);

    List<UserTimeIncludeResponseDto> findAllUsers();

    UserTimeIncludeResponseDto findByIdUser(Long id);

    UserResponseDto updateByIdUser(Long id, String name);

    void deleteByIdUser(Long id);

    UserDto login(LoginRequestDto requestDto);
}
