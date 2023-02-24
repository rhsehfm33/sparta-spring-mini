package com.sparta.vikingband.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
   public class Study_BoardRequestDto {
       @Size(max=255)
       private String content;

}
