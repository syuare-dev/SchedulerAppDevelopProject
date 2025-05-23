package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId);

    List<ScheduleTimeIncludedResponseDto> findAllSchedules();

    PageScheduleResponseDto<ScheduleTimeIncludedResponseDto> getSchedules(Pageable pageable);

    ScheduleTimeIncludedResponseDto findByIdSchedule(Long id);

    ScheduleResponseDto updatedByIdSchedule(Long scheduleId, UpdateScheduleRequestDto requestDto, Long userId);

    void deleteSchedule(Long id);
}
