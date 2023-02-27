package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.StudyBoardComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class StudyBoardCommentResponseDto {

    private Long id;
    private Long memberId;
    private String memberName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public StudyBoardCommentResponseDto (StudyBoardComment studyBoardComment) {
        this.id = studyBoardComment.getId();
        this.memberId = studyBoardComment.getMember().getId();
        this.memberName = studyBoardComment.getMember().getMemberName();
        this.content = studyBoardComment.getContent();
        this.createdAt = studyBoardComment.getCreatedAt() == null ? LocalDateTime.now() : studyBoardComment.getCreatedAt();
        this.modifiedAt = LocalDateTime.now();
    }

    public static StudyBoardCommentResponseDto of(StudyBoardComment studyBoardComment) {
        return new StudyBoardCommentResponseDto(studyBoardComment);
    }
}
