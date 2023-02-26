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

    private Long studyRegistId;

    StudyRegistResponseDto(StudyRegist studyRegist) {
        this.studyRegistId = studyRegist.getId();
    }

    public static StudyRegistResponseDto of(StudyRegist studyRegist) {
        return new StudyRegistResponseDto(studyRegist);
    }
}
