package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
