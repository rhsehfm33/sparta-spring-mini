package com.sparta.vikingband.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "StudyRegister")
@NoArgsConstructor
public class StudyRegist extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Study study;

    @Column
    private boolean isAccepted = false;

    public StudyRegist(Member member, Study study) {
        this.member = member;
        this.study = study;
    }

    public void toggleAccept() {
        isAccepted = !isAccepted;
    }


}
