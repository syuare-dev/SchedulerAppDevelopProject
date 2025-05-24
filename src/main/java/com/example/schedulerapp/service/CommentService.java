package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;
import com.example.schedulerapp.dto.commentDto.CommentTimeIncludeResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto saveComment(Long scheduleId, CommentRequestDto requestDto, Long userId);

    List<CommentTimeIncludeResponseDto> findAllComments(Long scheduleId);

    CommentResponseDto updateCommentById(Long scheduleId, Long commentId, CommentRequestDto requestDto, Long userId);

    void deleteCommentById(Long scheduleId, Long commentId, Long userId);

}
