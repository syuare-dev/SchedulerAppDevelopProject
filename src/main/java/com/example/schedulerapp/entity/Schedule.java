package com.example.schedulerapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String username;

    @NotNull
    @Length(min=1, max=16)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @Setter
    @ManyToOne // 여러 일정이 하나의 유저와 연결
    @JoinColumn(name = "user_id") // 실제 DB 에서 FK를 user_id로 설정
    private User user;

    @OneToMany(mappedBy = "schedule")
    private List<CommentEntity> comments;



    public Schedule() {
    }

    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateSchedule(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

}
