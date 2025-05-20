package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.dto.scheduleDto.UpdateScheduleRequestDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(String username, String title, String contents);

    List<ScheduleTimeIncludedResponseDto> findAllSchedule();

    ScheduleTimeIncludedResponseDto findByIdSchedule(Long id);

    ScheduleResponseDto updatedByIdSchedule(Long id, UpdateScheduleRequestDto requestDto);
}
