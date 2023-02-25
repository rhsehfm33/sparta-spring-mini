package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.StudyRequestDto;
import com.sparta.vikingband.dto.StudyResponseDto;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies")
public class StudyController {
    private final StudyService studyService;

    @PostMapping
    public ApiResponse<StudyResponseDto> createStudy(
            @RequestBody @Valid StudyRequestDto studyRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.CREATED, studyService.studyCreate(studyRequestDto, userDetailsImpl));
    }

    @GetMapping("/{studyId}")
    public ApiResponse<StudyResponseDto> getStudy(@PathVariable Long studyId) {
        return ApiResponse.successOf(HttpStatus.OK, studyService.getStudy(studyId));
    }

    @GetMapping
    public ApiResponse<List<StudyResponseDto>> getStudies() {
        return ApiResponse.successOf(HttpStatus.OK, studyService.getStudies());
    }

    @PutMapping("/{studyId}")
    public ApiResponse<StudyResponseDto> updateStudy(
            @PathVariable Long studyId,
            @RequestBody @Valid StudyRequestDto studyRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.OK, studyService.updateStudy(studyId, studyRequestDto, userDetailsImpl));
    }

    @DeleteMapping("/{studyId}")
    public ApiResponse<StudyResponseDto> deleteStudy(
            @PathVariable Long studyId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        studyService.deleteStudy(studyId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }
}
