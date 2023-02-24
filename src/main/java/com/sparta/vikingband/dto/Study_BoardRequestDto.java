package com.sparta.vikingband.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
   public class Study_BoardRequestDto {
       private Long member_Id;
       private Long study_Id;
       @Size(max=255)
       private String content;

}
