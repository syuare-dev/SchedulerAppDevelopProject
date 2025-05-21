package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.userDto.UpdateUserRequestDto;
import com.example.schedulerapp.dto.userDto.UserResponseDto;
import com.example.schedulerapp.dto.userDto.UserSignUpResponseDto;
import com.example.schedulerapp.dto.userDto.UserTimeIncludeResponseDto;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserSignUpResponseDto signUp(String name, String email) {

        User user = new User(name, email);
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
    public UserResponseDto updateByIdUser(Long id, UpdateUserRequestDto requestDto) {

        User findUser = userRepository.findByIdOrElseThrow(id);
        findUser.updateUser(requestDto.getName(), requestDto.getEmail());

        return new UserResponseDto(findUser.getName(), findUser.getEmail());
    }

    @Override
    public void deleteByIdUser(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }
}
