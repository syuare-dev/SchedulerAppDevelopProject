package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.userDto.*;
import com.example.schedulerapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> UserSignUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {

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

    @PatchMapping
    public ResponseEntity<UserResponseDto> updateMyInfo(
            @RequestBody @Valid UpdateUserRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        UserResponseDto updateMyInfo = userService.updateByMyInfo(requestDto, userId);

        return new ResponseEntity<>(updateMyInfo, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByIdUser(@PathVariable Long id) {

        userService.deleteByIdUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
