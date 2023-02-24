package com.sparta.vikingband.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.sparta.vikingband.enums.ErrorMessage.WRONG_JWT_TOKEN;
import static com.sparta.vikingband.enums.ErrorType.JWT_EXCEPTION;

//인증되지 않았을 경우(비로그인) AuthenticationEntryPoint 부분에서 AuthenticationException 발생시키면서
// 비로그인 상태에서 인증실패 시, AuthenticationEntryPoint 로 핸들링되어 이곳에서 처리
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ApiResponse dto = ApiResponse.failOf(HttpStatus.UNAUTHORIZED,new ErrorResponseDto(JWT_EXCEPTION,WRONG_JWT_TOKEN.getMessage()));

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, dto);
            os.flush();
        }
    }
}
