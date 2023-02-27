package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    List<StudyBoardResponseDto> studyBoardResponseDtos;

    public StudyWholeResponseDto(Study study) {
        this.author = AuthorResponseDto.of(study.getMember());
        this.likes = study.getStudyWishSet().size();
        this.title = study.getTitle();
        this.subject = study.getSubject();
        this.content = study.getContent();
        this.maxMember = study.getMaxMember();
        this.createdAt = study.getCreatedAt();
        this.modifiedAt = study.getModifiedAt();
        this.studyBoardResponseDtos = study.getStudyBoardSet().stream()
            .map(StudyBoardResponseDto::of)
            .collect(Collectors.toList());
    }
    public static StudyWholeResponseDto of(Study study) {
        return new StudyWholeResponseDto(study);
    }
}
