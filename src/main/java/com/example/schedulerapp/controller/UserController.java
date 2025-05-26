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

    /**
     * 유저 생성(회원가입) API
     * 클라이언트로부터 요청 전달된 데이터(username, email, password)로 유저 정보를 저장한다
     * @param requestDto 유저 생성 요청 DTO
     * @return 생성된 유저 정보 데이터와 201 상태 코드 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> UserSignUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {

        UserSignUpResponseDto userResponseDto = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    /**
     * 전체 유저 정보 조회 API
     * 각 유저별 정보와 생성 및 수정 시간을 포함한 데이터를 반환한다.
     * @return 전체 유저 정보 데이터와 200 상태 코드 반환
     */
    @GetMapping
    public ResponseEntity<List<UserTimeIncludeResponseDto>> findByAllUsers() {
        List<UserTimeIncludeResponseDto> userTimeIncludeResponseDto = userService.findAllUsers();

        return new ResponseEntity<>(userTimeIncludeResponseDto, HttpStatus.OK);
    }

    /**
     * ID 기반 특정 유저 조회 API
     * @param id 조회할 유저 ID
     * @return 조회된 유저 정보 데이터와 200 상태 코드 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserTimeIncludeResponseDto> findByIdUser(@PathVariable Long id) {
        UserTimeIncludeResponseDto userTimeIncludeResponseDto = userService.findByIdUser(id);

        return new ResponseEntity<>(userTimeIncludeResponseDto, HttpStatus.OK);
    }

    /**
     * 특정 유저 정보 수정 API
     * 현재 로그인한 유저의 정보를 수정할 수 있다.
     * 현재 로그인한 유저는 Session 을 활용 > 확인된 유저 ID 값을 참고한다.
     * @param requestDto 수정할 유저 이름을 담은 요청 DTO
     * @param servletRequest HTTP 요청 객체로부터 Session 을 통해 유저 ID를 확인
     * @return 수정된 유저 이름과 email 데이터와 200 상태 코드 반환
     */
    @PatchMapping
    public ResponseEntity<UserResponseDto> updateMyInfo(
            @RequestBody @Valid UpdateUserRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        UserResponseDto updateMyInfo = userService.updateByMyInfo(requestDto, userId);

        return new ResponseEntity<>(updateMyInfo, HttpStatus.OK);
    }

    /** 특정 유저 삭제 API
     * Session 에서 로그인한 유저 ID를 확인 > 저장된 유저 ID와 비교 후 삭제 로직 수행
     * 삭제 완료 시 세션 종료를 통해 로그아웃하고 및 204 상태 코드를 반환
     * @param id 삭제할 유저 ID
     * @param servletRequest HTTP 요청 객체로부터 Session 을 통해 유저 ID를 확인
     * @return 삭제 성공 시 204 상태 코드 반환
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByIdUser(@PathVariable Long id, HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        userService.deleteByIdUser(id, userId);

        servletRequest.getSession(false).invalidate();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
