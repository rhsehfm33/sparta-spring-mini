package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    boolean isWished;
    boolean isRegistered;
    int maxMember;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    List<StudyBoardResponseDto> studyBoards;

    public StudyWholeResponseDto(Study study, boolean isWished, boolean isRegistered) {
        this.author = AuthorResponseDto.of(study.getMember());
        this.studyId = study.getId();
        this.likes = study.getStudyWishSet().size();
        this.title = study.getTitle();
        this.subject = study.getSubject();
        this.content = study.getContent();
        this.isWished = isWished;
        this.isRegistered = isRegistered;
        this.maxMember = study.getMaxMember();
        this.createdAt = study.getCreatedAt();
        this.modifiedAt = study.getModifiedAt();
        this.studyBoards = study.getStudyBoardSet().stream()
            .map(StudyBoardResponseDto::of)
            .collect(Collectors.toList());
    }
    public static StudyWholeResponseDto of(Study study, boolean isWished, boolean isRegistered) {
        return new StudyWholeResponseDto(study, isWished, isRegistered);
    }
}
