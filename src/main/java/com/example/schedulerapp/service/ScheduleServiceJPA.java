package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.*;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.ScheduleRepository;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceJPA implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    /**
     * 일정 생성 API
     * @param username 작성 유저명
     * @param title 할 일 제목
     * @param contents 할 일 내용
     * @return schedule Entity 의 id, title, contents 값을 ResponseDto 로 반환
     */
    @Override
    public ScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents());
        schedule.setUser(findUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser().getName());
    }


    @Override
    public List<ScheduleTimeIncludedResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleTimeIncludedResponseDto::toDto).toList();
    }

    @Override
    public PageScheduleResponseDto<ScheduleTimeIncludedResponseDto> getSchedules(Pageable pageable) {
        Page<Schedule> page = scheduleRepository.findAll(pageable);
        Page<ScheduleTimeIncludedResponseDto> dtoPage = page.map(ScheduleTimeIncludedResponseDto::toDto);

        return new PageScheduleResponseDto<>(dtoPage);
    }

    @Override
    public ScheduleTimeIncludedResponseDto findByIdSchedule(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleTimeIncludedResponseDto(findSchedule.getId(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getUser().getName(), findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Transactional
    @Override
    public ScheduleResponseDto updatedByIdSchedule(Long scheduleId, UpdateScheduleRequestDto requestDto, Long userId) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        if (!findSchedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the author of the schedule can modify it.");
        }

        findSchedule.updateSchedule(requestDto.getTitle(), requestDto.getContents());

        return new ScheduleResponseDto(findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getUser().getName());
    }

    @Override
    public void deleteSchedule(Long scheduleId, Long userId) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        if (!findSchedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the author of the schedule can delete it.");
        }

        scheduleRepository.delete(findSchedule);
    }


}
