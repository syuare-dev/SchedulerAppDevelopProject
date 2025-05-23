package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;
import com.example.schedulerapp.dto.commentDto.CommentTimeIncludeResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto saveComment(Long id, CommentRequestDto requestDto);

    List<CommentTimeIncludeResponseDto> findAllComments(Long scheduleId);

    CommentResponseDto updateCommentById(Long scheduleId, Long commentId, CommentRequestDto requestDto);

}
