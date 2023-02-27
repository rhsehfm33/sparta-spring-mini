package com.sparta.vikingband.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StudyWholeResponseDto {
    AuthorResponseDto author;
    long studyId;
    long likes;
    String title;
    String subject;
    String content;
    int maxMember;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    List<StudyBoardResponseDto> studyResponseDtoList = new ArrayList<>();

    public void addStudy(StudyResponseDto dto) {
        studyResponseDtoList.add(dto);
    }
}
