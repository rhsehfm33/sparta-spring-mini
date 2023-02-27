package com.sparta.vikingband.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class StudyRequestDto {
    @NotBlank
    @Size(min = 1, max = 30)
    String title;

    @Size(max = 30)
    String subject;

    @Size(max = 255)
    String content;

    @NotNull
    @Min(2)
    @Max(100)
    Integer maxMember;
}
