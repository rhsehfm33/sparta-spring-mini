package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyWish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyWishResponseDto {

    private boolean isWished;


    StudyWishResponseDto(boolean isWished) {
        this.isWished = isWished;
    }

    public static StudyWishResponseDto of(boolean isWished) {
        return new StudyWishResponseDto(isWished);
    }

}
