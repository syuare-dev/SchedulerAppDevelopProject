package com.example.schedulerapp.controller;


import com.example.schedulerapp.dto.loginDto.LoginRequestDto;
import com.example.schedulerapp.dto.loginDto.LoginResponseDto;
import com.example.schedulerapp.dto.loginDto.UserDto;
import com.example.schedulerapp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserDto userDto = userService.login(requestDto);

        HttpSession session = request.getSession();
        session.setAttribute("userId", userDto.getId());

        return new ResponseEntity<>(new LoginResponseDto(userDto.getName(),"로그인 성공"), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}

