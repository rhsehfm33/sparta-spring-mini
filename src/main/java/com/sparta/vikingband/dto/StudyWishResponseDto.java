package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyWish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudyWishResponseDto {

    private Long id;
    private Member member;
    private Study study;

    StudyWishResponseDto(StudyWish studyWish) {
        this.id = studyWish.getId();
        this.member = studyWish.getMember();
        this.study = studyWish.getStudy();
    }

    public static StudyWishResponseDto of(StudyWish studyWish) {
        return new StudyWishResponseDto(studyWish);
    }

}
