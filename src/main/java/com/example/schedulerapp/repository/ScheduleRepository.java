package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 요청받은 id 값에 해당하는 일정(schedule) 조회 > 조회 일정이 없을 경우 예외 처리
     * @param id 조회할 일정 ID
     * @return 요청받은 id 값으로 조회된 일정 데이터
     * @throws ResponseStatusException 조회 시 일정 데이터가 없을 경우 > 404 상태 코드 반환으로 예외 처리
     */
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

}
