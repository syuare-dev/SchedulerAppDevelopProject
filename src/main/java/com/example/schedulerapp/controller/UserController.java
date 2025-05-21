package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.userDto.UserRequestDto;
import com.example.schedulerapp.dto.userDto.UserResponseDto;
import com.example.schedulerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> UserSignUp(@RequestBody UserRequestDto requestDto) {

        UserResponseDto userResponseDto = userService.signUp(requestDto.getName(), requestDto.getEmail());

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
}
