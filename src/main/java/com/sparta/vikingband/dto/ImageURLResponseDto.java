package com.sparta.vikingband.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ImageURLResponseDto {
   private String imageUrl;

   ImageURLResponseDto(String url){
      this.imageUrl = url;
   }

   public static ImageURLResponseDto of (String url){return new ImageURLResponseDto(url);}
}
