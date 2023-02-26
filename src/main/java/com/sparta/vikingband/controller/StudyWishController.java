package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.StudyWishRequestDto;
import com.sparta.vikingband.dto.StudyWishResponseDto;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyWishService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudyWishController {

    private StudyWishService studyWishService;

    @GetMapping("/wish/{memberId}")
    public ApiResponse<List<StudyWishResponseDto>> getWishes(@PathVariable Long memberId) {
        return ApiResponse.successOf(HttpStatus.OK, studyWishService.getWishes(memberId));
    }

    @PostMapping("/wish")
    public ApiResponse<StudyWishResponseDto> makeWish(
        @RequestBody StudyWishRequestDto studyWishRequestDto,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ApiResponse.successOf(HttpStatus.ACCEPTED, studyWishService.makeWish(studyWishRequestDto, userDetails));
    }

    @DeleteMapping("/wish/{wishId}")
    public ApiResponse<StudyWishResponseDto> deleteWish(
        @PathVariable Long wishId,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        studyWishService.deleteWish(wishId, userDetails);
        return ApiResponse.successOf(HttpStatus.ACCEPTED, null);
    }
}
