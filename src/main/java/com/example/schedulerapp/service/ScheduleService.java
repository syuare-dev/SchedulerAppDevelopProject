package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.CreateScheduleRequestDto;
import com.example.schedulerapp.dto.scheduleDto.PageScheduleResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId);

    List<ScheduleTimeIncludedResponseDto> findAllSchedules();

    PageScheduleResponseDto<ScheduleTimeIncludedResponseDto> getSchedules(Pageable pageable);

    ScheduleTimeIncludedResponseDto findByIdSchedule(Long id);

    ScheduleResponseDto updatedByIdSchedule(Long id, String title, String contents, String username);

    void deleteSchedule(Long id);
}
