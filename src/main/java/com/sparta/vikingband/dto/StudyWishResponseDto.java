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

    private Long studyWishId;


    StudyWishResponseDto(StudyWish studyWish) {
        this.studyWishId = studyWish.getId();
    }

    public static StudyWishResponseDto of(StudyWish studyWish) {
        return new StudyWishResponseDto(studyWish);
    }

}
