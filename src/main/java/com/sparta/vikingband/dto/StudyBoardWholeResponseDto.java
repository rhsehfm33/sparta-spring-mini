package com.sparta.vikingband.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StudyBoardWholeResponseDto {
    private List<StudyBoardResponseDto> studyBoardList = new ArrayList<>();

    public void addStudyBoard(StudyBoardResponseDto dto){
        studyBoardList.add(dto);
    }
}
