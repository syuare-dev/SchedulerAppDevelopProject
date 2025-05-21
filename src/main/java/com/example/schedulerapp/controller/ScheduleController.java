package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.scheduleDto.CreateScheduleRequestDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.dto.scheduleDto.UpdateScheduleRequestDto;
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
                        requestDto.getTitle(),
                        requestDto.getContents(),
                        requestDto.getUsername()
                );

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleTimeIncludedResponseDto>> findAllSchedules() {
        List<ScheduleTimeIncludedResponseDto> scheduleTimeIncludedResponseDto = scheduleService.findAllSchedules();

        return new ResponseEntity<>(scheduleTimeIncludedResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleTimeIncludedResponseDto> findByIdSchedule(@PathVariable Long id) {
        ScheduleTimeIncludedResponseDto scheduleTimeIncludedResponseDto = scheduleService.findByIdSchedule(id);

        return new ResponseEntity<>(scheduleTimeIncludedResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule (
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto
    ) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.updatedByIdSchedule(id, requestDto);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
