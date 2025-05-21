package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.userDto.UpdateUserRequestDto;
import com.example.schedulerapp.dto.userDto.UserResponseDto;
import com.example.schedulerapp.dto.userDto.UserSignUpResponseDto;
import com.example.schedulerapp.dto.userDto.UserTimeIncludeResponseDto;

import java.util.List;

public interface UserService {

    UserSignUpResponseDto signUp(String name, String email);

    List<UserTimeIncludeResponseDto> findAllUsers();

    UserTimeIncludeResponseDto findByIdUser(Long id);

    UserResponseDto updateByIdUser(Long id, UpdateUserRequestDto requestDto);

    void deleteByIdUser(Long id);
}
