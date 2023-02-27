package com.sparta.vikingband.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StudyWholeResponseDto {

    private List<StudyResponseDto> studyResponseDtoList = new ArrayList<>();

    public void addStudy(StudyResponseDto dto) {
        studyResponseDtoList.add(dto);
    }
}
