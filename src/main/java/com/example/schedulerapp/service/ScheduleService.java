package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(String username, String title, String contents);

    List<ScheduleTimeIncludedResponseDto> findAll();

    ScheduleTimeIncludedResponseDto findById(Long id);
}
