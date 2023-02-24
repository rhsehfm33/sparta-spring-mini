package com.sparta.vikingband.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Entity(name = "study_board")
@NoArgsConstructor
public class Study_Board extends Timestamped {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Size(max=255)
        @Column(nullable = false)
        private String content;


        public Study_Board(Study_BoardRequestDto dto, Long member_id, Long study_id){
            //this.content=dto.getContent;
            //this.member_id=dto.getMember.getId()
            //this.study_id=dto.getStudy.getId()
        }
}
