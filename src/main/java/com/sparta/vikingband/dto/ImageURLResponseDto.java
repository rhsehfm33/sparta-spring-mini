package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ImageURLResponseDto {
   private String imageUrl;

   ImageURLResponseDto(String imageUrl){
      this.imageUrl = imageUrl;
   }

   public static ImageURLResponseDto of (String imageUrl) {
      return new ImageURLResponseDto(imageUrl);
   }
}
