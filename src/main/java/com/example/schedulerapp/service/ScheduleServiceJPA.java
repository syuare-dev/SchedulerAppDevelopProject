package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.ScheduleResponseDto;
import com.example.schedulerapp.dto.scheduleDto.ScheduleTimeIncludedResponseDto;
import com.example.schedulerapp.dto.scheduleDto.UpdateScheduleRequestDto;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.ScheduleRepository;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ScheduleResponseDto saveSchedule(String title, String contents, String username) {

        User findUser = userRepository.findUserByUsernameOrElseThrow(username);

        Schedule schedule = new Schedule(title, contents);
        schedule.setUser(findUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser().getName());
    }


    @Override
    public List<ScheduleTimeIncludedResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleTimeIncludedResponseDto::toDto).toList();
    }

    @Override
    public ScheduleTimeIncludedResponseDto findByIdSchedule(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleTimeIncludedResponseDto(findSchedule.getId(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getUser(), findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Transactional
    @Override
    public ScheduleResponseDto updatedByIdSchedule(Long id, UpdateScheduleRequestDto requestDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        findSchedule.updateSchedule(requestDto.getUser(), requestDto.getTitle(), requestDto.getContents());

        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getUser().getName());
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        scheduleRepository.delete(findSchedule);
    }


}
