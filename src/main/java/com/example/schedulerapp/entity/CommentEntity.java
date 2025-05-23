package com.example.schedulerapp.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "longtext")
    private String comment;

    @Setter
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentEntity() {
    }

    public CommentEntity(String comment) {
        this.comment = comment;
    }

    public void updateComment (String comment) {
        this.comment = comment;
    }
}
