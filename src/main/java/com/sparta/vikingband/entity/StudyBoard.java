package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.StudyBoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "StudyBoard")
@NoArgsConstructor
public class StudyBoard extends Timestamped {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String content;

        @ManyToOne(fetch = LAZY)
        private Study study;

        @ManyToOne(fetch = LAZY)
        private Member member;

        @OneToMany(mappedBy = "studyBoard", cascade=CascadeType.REMOVE)
        List<StudyBoardComment> studyBoardCommentList;

        public StudyBoard(StudyBoardRequestDto dto, Member member, Study study){
            this.content=dto.getContent();
            this.member=member;
            this.study=study;
        }

        public void update(StudyBoardRequestDto dto){
                this.content= dto.getContent();
        }
}
