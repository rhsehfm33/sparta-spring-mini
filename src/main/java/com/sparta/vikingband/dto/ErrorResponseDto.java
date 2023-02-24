package com.sparta.vikingband.dto;

import com.sparta.vikingband.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
    ErrorType errorType;
    String errorMessage;
    public ErrorResponseDto(ErrorType errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
}
