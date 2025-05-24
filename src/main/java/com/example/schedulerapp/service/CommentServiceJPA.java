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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceJPA implements CommentService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto saveComment(Long scheduleId, CommentRequestDto requestDto, Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        CommentEntity comment = new CommentEntity(requestDto.getComment());
        comment.setUser(findUser); // 로그인 유저 설정
        comment.setSchedule(findSchedule); // 조회한 일정 설정

        CommentEntity savedComment = commentRepository.save(comment); // 댓글 저장

        return new CommentResponseDto(savedComment.getId(), savedComment.getComment(), savedComment.getUser().getName());
    }

    @Override
    public List<CommentTimeIncludeResponseDto> findAllComments(Long scheduleId) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return findSchedule.getComments().stream().map(CommentTimeIncludeResponseDto::toDto).toList();
    }

    @Transactional
    @Override
    public CommentResponseDto updateCommentById(Long scheduleId, Long commentId, CommentRequestDto requestDto, Long userId) {

        scheduleRepository.findByIdOrElseThrow(scheduleId); // 일정 조회

        CommentEntity findComment = commentRepository.findByIdCommentOrElseThrow(commentId); // 댓글 조회

        // 조회한 댓글이 조회한 일정에 속하는지 확인 절차
        if (!findComment.getSchedule().getId().equals(scheduleId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Does not exist Comment");
        }

        // 댓글 작성자가 본인인지 확인 절차
        if (!findComment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only modify your own comment.");
        }

        findComment.updateComment(requestDto.getComment());

        return new CommentResponseDto(findComment.getId(), findComment.getComment(), findComment.getUser().getName());
    }

    @Override
    public void deleteCommentById(Long scheduleId, Long commentId, Long userId) {

        CommentEntity findComment = commentRepository.findByIdCommentOrElseThrow(commentId); // 댓글 조회

        // 조회한 댓글이 조회한 일정에 속하는지 확인 절차
        if (!findComment.getSchedule().getId().equals(scheduleId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Does not exist Comment");
        }

        // 댓글 작성자가 본인인지 확인
        if (!findComment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only modify your own comment.");
        }

        commentRepository.delete(findComment);
    }
}
