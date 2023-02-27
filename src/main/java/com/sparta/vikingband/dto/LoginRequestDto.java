package com.sparta.vikingband.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class LoginRequestDto {
    @Size(min=4, max=10)
    @Pattern(regexp="^[a-z0-9]+$")
    private String username;

    @Size(min=8, max=15)
    @Pattern(regexp="^[A-Za-z0-9]+$")
    private String password;
}