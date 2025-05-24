package com.example.schedulerapp.dto.userDto;

import com.example.schedulerapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserTimeIncludeResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public UserTimeIncludeResponseDto(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserTimeIncludeResponseDto toDto (User user) {
        return new UserTimeIncludeResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
