package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(String title, String contents, String username);

    List<ScheduleTimeIncludedResponseDto> findAllSchedules();

    ScheduleTimeIncludedResponseDto findByIdSchedule(Long id);

    ScheduleResponseDto updatedByIdSchedule(Long id, String title, String contents, String username);

    void deleteSchedule(Long id);
}
