package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StudyBoardResponseDto {
    private Long id;
    private Long memberId;
    private String membername; // TODO: 네이밍 컨벤션에 따라 memberName으로
    private String title; // TODO: 삭제
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    StudyBoardResponseDto(StudyBoard studyBoard){
        this.id = studyBoard.getId();
        this.memberId =studyBoard.getMember().getId();
        this.membername=studyBoard.getMember().getMemberName();
        this.title=studyBoard.getStudy().getTitle();
        this.content=studyBoard.getContent();
        this.createdAt = studyBoard.getCreatedAt();
        this.modifiedAt = studyBoard.getModifiedAt();
    }
    public static StudyBoardResponseDto of (StudyBoard studyBoard) {
        return new StudyBoardResponseDto(studyBoard);
    }
}


