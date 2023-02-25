package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.StudyBoardComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class StudyBoardCommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public StudyBoardCommentResponseDto (StudyBoardComment studyBoardComment) {
        this.id = studyBoardComment.getId();
        this.content=studyBoardComment.getContent();
        this.createdAt = studyBoardComment.getCreatedAt();
        this.modifiedAt = studyBoardComment.getModifiedAt();
    }
}
