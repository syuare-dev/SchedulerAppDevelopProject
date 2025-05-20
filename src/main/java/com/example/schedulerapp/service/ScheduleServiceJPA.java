package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceJPA implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    /**
     * 일정 생성 API
     * @param username 작성 유저명
     * @param title 할 일 제목
     * @param contents 할 일 내용
     * @return schedule Entity 의 id, title, contents 값을 ResponseDto 로 반환
     */
    @Override
    public ScheduleResponseDto saveSchedule(String username, String title, String contents) {

        Schedule schedule = new Schedule(username, title, contents);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUsername());
    }


    @Override
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }
}
