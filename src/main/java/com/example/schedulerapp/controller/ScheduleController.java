package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.scheduleDto.*;
import com.example.schedulerapp.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(
            @RequestBody @Valid CreateScheduleRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        ScheduleResponseDto scheduleResponseDto = scheduleService.saveSchedule(requestDto, userId);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleTimeIncludedResponseDto>> findAllSchedules() {
        List<ScheduleTimeIncludedResponseDto> scheduleTimeIncludedResponseDto = scheduleService.findAllSchedules();

        return new ResponseEntity<>(scheduleTimeIncludedResponseDto, HttpStatus.OK);
    }

    @GetMapping("/paging")
    public ResponseEntity<PageScheduleResponseDto<ScheduleTimeIncludedResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        PageScheduleResponseDto<ScheduleTimeIncludedResponseDto> pagingResult = scheduleService.getSchedules(pageable);

        return new ResponseEntity<>(pagingResult, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleTimeIncludedResponseDto> findByIdSchedule(@PathVariable Long scheduleId) {
        ScheduleTimeIncludedResponseDto scheduleTimeIncludedResponseDto = scheduleService.findByIdSchedule(scheduleId);

        return new ResponseEntity<>(scheduleTimeIncludedResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule (
            @PathVariable Long scheduleId,
            @RequestBody @Valid UpdateScheduleRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        ScheduleResponseDto scheduleResponseDto = scheduleService.updatedByIdSchedule(scheduleId, requestDto, userId);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long scheduleId, HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getSession(false).getAttribute("userId");

        scheduleService.deleteSchedule(scheduleId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
