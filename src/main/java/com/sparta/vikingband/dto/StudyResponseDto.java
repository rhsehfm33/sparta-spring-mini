package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Study;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyResponseDto {
    AuthorResponseDto author;
    long studyId;
    long likes;
    String title;
    String subject;
    String content;
    int maxMember;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    StudyResponseDto(Study study) {
        this.author = AuthorResponseDto.of(study.getMember());
        this.studyId = study.getId();
        this.likes = study.getStudyWishSet().size();
        this.title = study.getTitle();
        this.content = study.getContent();
        this.subject = study.getSubject();
        this.maxMember = study.getMaxMember();
        this.createdAt = study.getCreatedAt() == null ? LocalDateTime.now() : study.getCreatedAt();
        this.modifiedAt = LocalDateTime.now();
    }

    public static StudyResponseDto of(Study newStudy) {
        return new StudyResponseDto(newStudy);
    }
}
