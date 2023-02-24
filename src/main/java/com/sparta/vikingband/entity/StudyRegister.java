package com.sparta.vikingband.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "StudyRegister")
@NoArgsConstructor
public class StudyRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Study study;

    @Column
    private boolean isAccepted = false;
}
