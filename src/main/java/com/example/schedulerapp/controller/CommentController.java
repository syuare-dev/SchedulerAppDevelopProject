package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;
import com.example.schedulerapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<CommentResponseDto> saveComment (@PathVariable Long scheduleId, @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto commentResponseDto = commentService.saveComment(scheduleId, requestDto);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

}
