package com.example.schedulerapp.service;

import com.example.schedulerapp.config.PasswordEncoder;
import com.example.schedulerapp.dto.loginDto.LoginRequestDto;
import com.example.schedulerapp.dto.loginDto.UserDto;
import com.example.schedulerapp.dto.userDto.UpdateUserRequestDto;
import com.example.schedulerapp.dto.userDto.UserResponseDto;
import com.example.schedulerapp.dto.userDto.UserSignUpResponseDto;
import com.example.schedulerapp.dto.userDto.UserTimeIncludeResponseDto;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 생성 기능 (=회원 가입)
     * 유저 이름, 이메일, 비밀번호 값을 받아 저장하고
     * 저장된 유저 정보를 포함한 응답 DTO 반환
     * @param name 유저 이름
     * @param email 유저 이메일(ID로 사용)
     * @param password  유저 비밀번호
     * @return 저장된 유저 정보를 UserSignUpResponseDto 로 반환
     */
    @Override
    public UserSignUpResponseDto signUp(String name, String email, String password) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, encodedPassword);

        User savedUser = userRepository.save(user);

        return new UserSignUpResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    /**
     * 저장된 모든 유저 정보 조회 기능
     * @return 저잗된 모든 유저 정보를 UserTimeIncludeResponseDto 의 toDto 로 반환
     */
    @Override
    public List<UserTimeIncludeResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserTimeIncludeResponseDto::toDto).toList();
    }

    /**
     * 요청받은 id 값에 해당하는 유저 정보 조회 기능
     * @param id 조회할 유저 ID
     * @return ID 값으로 조회된 유저 정보를 반환
     */
    @Override
    public UserTimeIncludeResponseDto findByIdUser(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        return new UserTimeIncludeResponseDto(findUser.getId(), findUser.getName(), findUser.getEmail(), findUser.getCreatedAt(), findUser.getModifiedAt());
    }

    /**
     * 로그인한 유저의 정보 수정
     * 요청 DTO 를 통해 수정이 필요한 username 값을 받아 수정 진행
     * @param requestDto 수정할 유저 이름이 담긴 요청 DTO
     * @param userid 수정할 대상 유저의 ID
     * @return 수정된 유저 정보 데이터를 반환
     */
    @Transactional // 트랜잭션 안에서 실행 > 유저 정보 수정이 DB 에도 반영되도록 함
    @Override
    public UserResponseDto updateByMyInfo(UpdateUserRequestDto requestDto, Long userid) {

        User findUser = userRepository.findByIdOrElseThrow(userid);

        findUser.updateUser(requestDto.getName());

        return new UserResponseDto(findUser.getName(), findUser.getEmail());
    }

    /**
     * 유저 정보 삭제 기능
     * 요청한 유저 ID와 삭제할 유저 ID가 일치할 경우 유저 정보 삭제 진행
     * @param id 삭제할 유저 ID
     * @param userId 현재 로그인한 유저 ID
     * @throws ResponseStatusException 요청한/로그인한 유저 ID와 삭제할 유저 ID를 비교 > 불일치 시 403 상태 코드로 예외 처리
     */
    @Override
    public void deleteByIdUser(Long id, Long userId) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own account.");
        }

        userRepository.delete(findUser);
    }

    /**
     * 유저 로그인 요청 처리 기능
     * email 로 유저 조회 + 입력한 비밀번호가 해당 유저 데이터에 저장된 비밀번호와 일치하는지 확인
     * 일치 시 로그인 성공, 해당 유저 정보 반환
     * @param requestDto 로그인 요청 정보(email, password)를 담은 요청 DTO
     * @return 로그인한 사용자의 ID 와 이름 반환
     * @throws ResponseStatusException
     *         - 로그인 요청한 유저의 email 값이 저장된 유저 DB에 없을 경우 404 상태 코드로 예외처리
     *         - 비밀빈호가 일치하지 않을 경우 401 상태 코드로 예외 처리
     */
    @Override
    public UserDto login(LoginRequestDto requestDto) {

        User user = userRepository.findUserByEmailOrElseThrow(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The password you entered is incorrect.");
        }

        return new UserDto(user.getId(), user.getName());
    }
}
