package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceJPA implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Override
    public ScheduleResponseDto saveSchedule(String username, String title, String contents) {

        Schedule schedule = new Schedule(username, title, contents);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents());
    }
}
