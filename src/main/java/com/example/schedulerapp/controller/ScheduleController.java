package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.scheduleDto.CreateScheduleRequestDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody CreateScheduleRequestDto requestDto) {

        ScheduleResponseDto scheduleResponseDto =
                scheduleService.saveSchedule(
                        requestDto.getUsername(),
                        requestDto.getTitle(),
                        requestDto.getContents()
                );

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleTimeIncludedResponseDto>> findAll () {
        List<ScheduleTimeIncludedResponseDto> scheduleTimeIncludedResponseDto = scheduleService.findAll();

        return new ResponseEntity<>(scheduleTimeIncludedResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleTimeIncludedResponseDto> findById(@PathVariable Long id) {
        ScheduleTimeIncludedResponseDto scheduleTimeIncludedResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleTimeIncludedResponseDto, HttpStatus.OK);
    }


}
