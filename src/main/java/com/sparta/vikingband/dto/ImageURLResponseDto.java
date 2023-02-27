package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ImageURLResponseDto {
   private String imageUrl;
   private String memberName;
   private Long studyId;


   ImageURLResponseDto(Study study){
      this.imageUrl = study.getImageUrl();
      this.memberName = study.getMember().getMemberName();
      this.studyId = study.getId();
   }

   public static ImageURLResponseDto of (Study study){return new ImageURLResponseDto(study);}
}
