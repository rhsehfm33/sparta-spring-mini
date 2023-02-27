package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.StudyBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StudyBoardResponseDto {
    private Long id;
    private Long memberId;
    private String memberName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    StudyBoardResponseDto(StudyBoard studyBoard){
        this.id = studyBoard.getId();
        this.memberId =studyBoard.getMember().getId();
        this.memberName=studyBoard.getMember().getMemberName();
        this.title=studyBoard.getTitle();
        this.content=studyBoard.getContent();
        this.createdAt = studyBoard.getCreatedAt();
        this.modifiedAt = studyBoard.getModifiedAt();
    }
    public static StudyBoardResponseDto of (StudyBoard studyBoard) {
        return new StudyBoardResponseDto(studyBoard);
    }
}


