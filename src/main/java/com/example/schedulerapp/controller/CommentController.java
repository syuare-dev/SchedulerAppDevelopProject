package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;
import com.example.schedulerapp.dto.commentDto.CommentTimeIncludeResponseDto;
import com.example.schedulerapp.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public ResponseEntity<CommentResponseDto> saveComment (
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        CommentResponseDto commentResponseDto = commentService.saveComment(scheduleId, requestDto, userId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<List<CommentTimeIncludeResponseDto>> findAllComments(@PathVariable Long scheduleId) {

        List<CommentTimeIncludeResponseDto> commentTimeIncludeResponseDtoList = commentService.findAllComments(scheduleId);
        
        return new ResponseEntity<>(commentTimeIncludeResponseDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateCommentById(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        CommentResponseDto commentResponseDto = commentService.updateCommentById(scheduleId, commentId, requestDto, userId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long scheduleId, @PathVariable Long commentId) {
        commentService.deleteCommentById(scheduleId, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
