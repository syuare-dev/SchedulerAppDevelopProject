package com.example.schedulerapp.controller;

import com.example.schedulerapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule/{scheduleId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;
}
