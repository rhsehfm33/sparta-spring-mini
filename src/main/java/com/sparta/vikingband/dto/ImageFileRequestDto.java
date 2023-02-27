package com.sparta.vikingband.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ImageFileRequestDto {
   private String OriginalFilename;
   private String fileName;
   private Integer fileSize;
}
