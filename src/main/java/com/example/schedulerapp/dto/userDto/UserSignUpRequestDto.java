package com.example.schedulerapp.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignUpRequestDto {

    @NotBlank (message = "name 은 필수값 입니다.")
    private final String name;

    @Email
    @NotBlank (message = "email 은 필수값 입니다.")
    private final String email;

    @NotBlank (message = "password 는 필수값 입니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{4,}$", message = "비밀번호는 최소 4글자 이상이어야 하며 대문자/숫자/소문자/특수문자가 포함되어 있어야 합니다 ")

    private final String password;

    public UserSignUpRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
