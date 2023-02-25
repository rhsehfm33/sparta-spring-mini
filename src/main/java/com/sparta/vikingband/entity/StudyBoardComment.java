package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.StudyBoardCommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "StudyBoardComment")
@NoArgsConstructor
public class StudyBoardComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private StudyBoard studyBoard;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Study study;


    public StudyBoardComment(
            StudyBoardCommentRequestDto requestDto,
            Member member,
            Study study,
            StudyBoard studyBoard
    ) {
        this.content = requestDto.getContent();
        this.member = member;
        this.study = study;
        this.studyBoard = studyBoard;
    }

    public void update(StudyBoardCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

}