package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.userDto.UserResponseDto;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto signUp(String name, String email) {

        User user = new User(name, email);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
}
