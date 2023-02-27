package com.sparta.vikingband.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity(name = "StudyRegister")
@NoArgsConstructor
public class StudyRegist extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @Column
    private boolean isAccepted = false;

    public StudyRegist(Member member, Study study) {
        this.member = member;
        this.study = study;
    }

    public void approve() {
        this.isAccepted = true;
    }

    public void deny() {
        this.isAccepted = false;
    }
}
