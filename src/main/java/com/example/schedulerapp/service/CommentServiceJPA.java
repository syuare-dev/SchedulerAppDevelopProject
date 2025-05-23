package com.example.schedulerapp.service;

import com.example.schedulerapp.repository.CommentRepository;
import com.example.schedulerapp.repository.ScheduleRepository;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceJPA {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
}
