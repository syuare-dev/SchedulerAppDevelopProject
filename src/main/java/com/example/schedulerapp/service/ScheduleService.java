package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;

public interface ScheduleService {

    public ScheduleResponseDto saveSchedule(String username, String title, String contents);
}
