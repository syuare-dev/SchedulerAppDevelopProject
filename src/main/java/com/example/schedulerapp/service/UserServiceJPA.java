package com.example.schedulerapp.service;

import com.example.schedulerapp.config.PasswordEncoder;
import com.example.schedulerapp.dto.loginDto.LoginRequestDto;
import com.example.schedulerapp.dto.loginDto.UserDto;
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

    @Override
    public UserSignUpResponseDto signUp(String name, String email, String password) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, encodedPassword);

        User savedUser = userRepository.save(user);

        return new UserSignUpResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public List<UserTimeIncludeResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserTimeIncludeResponseDto::toDto).toList();
    }

    @Override
    public UserTimeIncludeResponseDto findByIdUser(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        return new UserTimeIncludeResponseDto(findUser.getId(), findUser.getName(), findUser.getEmail(), findUser.getCreatedAt(), findUser.getModifiedAt());
    }

    @Transactional
    @Override
    public UserResponseDto updateByIdUser(Long id, String name) {

        User findUser = userRepository.findByIdOrElseThrow(id);
        findUser.updateUser(name);

        return new UserResponseDto(findUser.getName(), findUser.getEmail());
    }

    @Override
    public void deleteByIdUser(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }

    @Override
    public UserDto login(LoginRequestDto requestDto) {

        User user = userRepository.findUserByEmailOrElseThrow(requestDto.getEmail());

//        if (!requestDto.getPassword().equals(user.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The password you entered is incorrect.");
//        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The password you entered is incorrect.");
        }

        return new UserDto(user.getId(), user.getName());
    }
}
