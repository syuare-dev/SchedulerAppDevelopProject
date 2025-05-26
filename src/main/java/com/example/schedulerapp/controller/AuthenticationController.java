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

    /**
     * 유저 로그인 요청 처리 기능
     * email 과 비밀번호를 검증 > 로그인 성공 시 Session에 유저 ID 저장
     * 로그인 성공 시 유저 이름과 "로그인 성공" 메시지를 반환
     * @param requestDto 로그인 요청 정보를 담은 요청 DTO
     * @param request Session 생성 및 유저 ID 저장하기 위한 HTTP 요청 객체
     * @return 유저 이름과 로그인 메시지, 그리고 200 상태 코드 반환
     */
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

    /**
     * 유저 로그아웃 요청 처리 기능
     * 현재 Session 종료 및 JSESSIONID 쿠키 제거하여 클라이언트의 로그인 상태를 초기화 한다
     * @param request 현재 유저의 HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그아웃 완료 메시지와 200 상태 코드 반환
     */
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

