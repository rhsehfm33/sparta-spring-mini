package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.StudyWishRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Entity(name = "StudyWish")
@NoArgsConstructor
public class StudyWish extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    public StudyWish(Member member, Study study) {
        this.member = member;
        this.study = study;
    }
}
