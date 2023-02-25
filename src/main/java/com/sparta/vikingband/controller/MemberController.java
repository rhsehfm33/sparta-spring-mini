package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.LoginRequestDto;
import com.sparta.vikingband.dto.MemberOuterResponseDto;
import com.sparta.vikingband.dto.SignupRequestDto;
import com.sparta.vikingband.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberOuterResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ApiResponse.successOf(HttpStatus.CREATED, memberService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ApiResponse<MemberOuterResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, @Parameter(hidden = true) HttpServletResponse response) {
        return ApiResponse.successOf(HttpStatus.OK, memberService.login(loginRequestDto, response));
    }
}
