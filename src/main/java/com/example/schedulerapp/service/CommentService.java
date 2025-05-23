package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;

public interface CommentService {

    CommentResponseDto saveComment(Long id, CommentRequestDto requestDto);

}
