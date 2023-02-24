package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.Study_BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Entity(name = "StudyBoard")
@NoArgsConstructor
public class StudyBoard extends Timestamped {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String content;

        @ManyToOne
        private Study study;

        @ManyToOne
        private Member member;

        @OneToMany(mappedBy = "studyBoard")
        List<StudyBoardComment> studyBoardCommentList;

        public StudyBoard(Study_BoardRequestDto dto){
            this.content=dto.getContent();
            //this.member=member
            //this.study=study
        }
}
