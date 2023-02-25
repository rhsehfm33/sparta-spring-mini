package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyRegist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudyRegistResponseDto {

    private Long id;
    private Member member;
    private Study study;

    StudyRegistResponseDto(StudyRegist studyRegist) {
        this.id = studyRegist.getId();
        this.member = studyRegist.getMember();
        this.study = studyRegist.getStudy();
    }

    public static StudyRegistResponseDto of(StudyRegist studyRegist) {
        return new StudyRegistResponseDto(studyRegist);
    }
}
