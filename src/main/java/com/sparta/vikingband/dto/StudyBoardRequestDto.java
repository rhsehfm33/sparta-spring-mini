package com.sparta.vikingband.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class StudyBoardRequestDto {
    @Size(max = 255)
    private String content;

    @Size(max = 255)
    private String title;

}
