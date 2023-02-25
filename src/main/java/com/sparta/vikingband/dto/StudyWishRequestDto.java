package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StudyWishRequestDto {

    private Member member;
    private Study study;
}
