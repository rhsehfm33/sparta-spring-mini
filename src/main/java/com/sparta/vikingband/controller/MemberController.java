package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.*;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

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
    public ApiResponse<MemberOuterResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, @Parameter(hidden = true) HttpServletResponse response) {
        return ApiResponse.successOf(HttpStatus.OK, memberService.login(loginRequestDto, response));
    }

    @GetMapping("/details/{memberId}")
    public ApiResponse<MemberWholeResponseDto> getMemberDetail(
            @PathVariable Long memberId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) throws AccessDeniedException {
        return ApiResponse.successOf(HttpStatus.OK, memberService.getMemberDetail(memberId, userDetailsImpl));
    }
}
