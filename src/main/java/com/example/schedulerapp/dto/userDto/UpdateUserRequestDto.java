package com.example.schedulerapp.dto.userDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = false)
public class UpdateUserRequestDto {

    @Column(nullable = false)
    private final String name;

    public UpdateUserRequestDto(String name) {
        this.name = name;
    }
}
