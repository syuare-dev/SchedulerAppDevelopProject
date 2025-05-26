package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.scheduleDto.*;
import com.example.schedulerapp.entity.CommentEntity;
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
     * 신규 일정 생성 기능
     * 요청 DTO 를 통해 받은 일정 제목 + 내용으로 Schedule 객체 생성
     * 해당 일정을 작성한 유저(로그인 유저)와 연관지어 저장한 결과를 반환
     * @param requestDto 생성할 일정 + 내용을 담은 요청 DTO
     * @param userId 일정을 작성하는 유저 ID (로그인 유저)
     * @return 생성한 일정 정보를 담은 데이터(일정 제목, 내용, 유저 이름) 반환
     */
    @Override
    public ScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents());
        schedule.setUser(findUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser().getName());
    }

    /**
     * 전체 일정을 조회하는 기능
     * 각 일정에는 작성한 유저 정보, 일정 제목+내용, 일정 생성/수정 시간, 댓글 목록 데이터 들이 저장되어 있다.
     * @return 전체 일정 정보를 List 타입으로 반환
     */
    @Override
    public List<ScheduleTimeIncludedResponseDto> findAllSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleTimeIncludedResponseDto::toDto).toList();
    }

    /**
     * 페이지 기능 포함된 전체 일정 조회 기능
     * 페이지 정보에 따라 일정들을 조회하고, 조회된 결과 데이터들을 반환
     * @param pageable 페이징 정보를 담은 Pageable 객체(page, size)
     * @return 페이징 처리된 일정 정보를 반환
     */
    @Override
    public PageScheduleResponseDto<ScheduleTimeIncludedResponseDto> getSchedules(Pageable pageable) {
        Page<Schedule> page = scheduleRepository.findAll(pageable);
        Page<ScheduleTimeIncludedResponseDto> dtoPage = page.map(ScheduleTimeIncludedResponseDto::toDto);

        return new PageScheduleResponseDto<>(dtoPage);
    }

    /**
     * 요청받은 id 에 해당하는 일정 조회 기능 (단건)
     * 각 일정에는 제목, 내용, 유저 이름, 생성일, 수정일, 댓글 목록이 포함되어 있다.
      * @param id 조회할 일정 ID
     * @return 조회한 일정 정보를 반환
     * @throws ResponseStatusException 조회 시도한 일정이 존재하지 않을 경우 404 상태 코드로 예외 처리
     */
    @Override
    public ScheduleTimeIncludedResponseDto findByIdSchedule(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleTimeIncludedResponseDto(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContents(),
                findSchedule.getUser().getName(),
                findSchedule.getCreatedAt(),
                findSchedule.getModifiedAt(),
                findSchedule.getComments().stream().map(CommentEntity::toDto).toList()
        );
    }

    /**
     * 조회한 일정 ID의 일정 정보를 수정하는 기능
     * 수정 요청 보낸 유저(로그인 유저)가 일정 작성자인 유저일 경우에만 수정 가능하도록 한다.
     * @param scheduleId 수정할 일정 ID
     * @param requestDto 수정할 제목, 내용을 담은 요청 DTO
     * @param userId 요청 보낸 유저 ID (현재 로그인한 유저 ID)
     * @return 수정된 일정 정보 반환
     * @throws ResponseStatusException
     *         - 수정할 일정이 존재하지 않을 경우: 404 상태 코드로 예외 처리
     *         - 작성자가 아닌 유저가 수정 요청할 경우: 403 상태 코드로 예외 처리
     */
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

    /**
     * 조회한 일정 ID의 일정 정보를 삭제하는 기능
     * 삭제 요청한 유저가 일정을 작성(생성)한 유저일 경우에만 삭제가 가능하도록 한다.
     * @param scheduleId 삭제할 일정 ID
     * @param userId 요청 보낸 유저 ID (현재 로그인한 유저 ID)
     * @throws ResponseStatusException
     *         - 삭제할 일정이 존재하지 않을 경우: 404 상태 코드로 예외 처리
     *         - 작성자가 아닌 유저가 삭제 요청할 경우: 403 상태 코드로 예외 처리
     */
    @Override
    public void deleteSchedule(Long scheduleId, Long userId) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        if (!findSchedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the author of the schedule can delete it.");
        }

        scheduleRepository.delete(findSchedule);
    }


}
