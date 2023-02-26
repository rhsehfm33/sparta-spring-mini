package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.StudyBoardCommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
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