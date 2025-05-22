package com.example.schedulerapp.dto.userDto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    @Column(nullable = false)
    private final String name;

    public UpdateUserRequestDto(String name) {
        this.name = name;
    }
}
