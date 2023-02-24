package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.Study_BoardRequestDto;
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

        @Column(nullable = false)
        private String content;


        public Study_Board(Study_BoardRequestDto dto){
            this.content=dto.getContent();
            //this.member=member
            //this.study=study
        }
}
