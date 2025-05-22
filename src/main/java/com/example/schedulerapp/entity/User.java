package com.example.schedulerapp.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // 유저와 관련된 일정들을 한번에 조회 목적 > User -> Schedule 방향 관계 설정
    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules; // 하나의 유저가 여러 개의 일정과 연관됨

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String name) {
        this.name = name;
    }
}
