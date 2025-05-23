package com.example.schedulerapp.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{4,}$", message = "비밀번호는 최소 4글자 이상이어야 하며 대문자/숫자/소문자/특수문자가 포함되어 있어야 합니다 ")
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
