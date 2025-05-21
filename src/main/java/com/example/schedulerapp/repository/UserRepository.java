package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
