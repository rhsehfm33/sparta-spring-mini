package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.StudyBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyBoardResponseDto {
    private Long id;
    private Long memberId;
    private String membername;
    private String title;
    private String content;

    StudyBoardResponseDto(StudyBoard studyBoard){
        this.id = studyBoard.getId();
        this.memberId =studyBoard.getMember().getId();
        this.membername=studyBoard.getMember().getMemberName();
        this.title=studyBoard.getStudy().getTitle();
        this.content=studyBoard.getContent();
    }
    public static StudyBoardResponseDto of (StudyBoard studyBoard){
        return new StudyBoardResponseDto(studyBoard);
    }
}


