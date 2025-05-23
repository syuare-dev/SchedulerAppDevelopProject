package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;
import com.example.schedulerapp.dto.commentDto.CommentTimeIncludeResponseDto;
import com.example.schedulerapp.entity.CommentEntity;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.CommentRepository;
import com.example.schedulerapp.repository.ScheduleRepository;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceJPA implements CommentService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto saveComment(Long id, CommentRequestDto requestDto) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        User findUser = userRepository.findUserByUsernameOrElseThrow(requestDto.getUsername());

        CommentEntity comment = new CommentEntity(requestDto.getComment());
        comment.setSchedule(findSchedule);
        comment.setUser(findUser);

        CommentEntity savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(), savedComment.getComment(), savedComment.getUser().getName());
    }

    @Override
    public List<CommentTimeIncludeResponseDto> findAllComments() {
        return commentRepository.findAll().stream().map(CommentTimeIncludeResponseDto::toDto).toList();
    }
}
