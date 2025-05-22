package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.userDto.*;
import com.example.schedulerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> UserSignUp(@RequestBody UserSignUpRequestDto requestDto) {

        UserSignUpResponseDto userResponseDto = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserTimeIncludeResponseDto>> findByAllUsers() {
        List<UserTimeIncludeResponseDto> userTimeIncludeResponseDto = userService.findAllUsers();

        return new ResponseEntity<>(userTimeIncludeResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTimeIncludeResponseDto> findByIdUser(@PathVariable Long id) {
        UserTimeIncludeResponseDto userTimeIncludeResponseDto = userService.findByIdUser(id);

        return new ResponseEntity<>(userTimeIncludeResponseDto, HttpStatus.OK);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<UserResponseDto> updateByIdUser(@PathVariable Long id, @RequestBody @Validated UpdateUserRequestDto requestDto) {

        UserResponseDto updateUser = userService.updateByIdUser(id, requestDto.getName());

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByIdUser(@PathVariable Long id) {

        userService.deleteByIdUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
