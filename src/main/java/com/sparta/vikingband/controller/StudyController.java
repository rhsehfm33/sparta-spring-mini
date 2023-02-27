package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.*;
import com.sparta.vikingband.enums.SortType;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyService;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ApiResponse<StudyResponseDto> createStudy (
            @RequestBody @Valid StudyRequestDto studyRequestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.CREATED, studyService.studyCreate(studyRequestDto, userDetailsImpl));
    }
    @PostMapping("/file")
    public ApiResponse<ImageURLWholeResponseDto> uploadFile(
            @RequestPart List<MultipartFile> multipartFile,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.CREATED, studyService.uploadFile(multipartFile,userDetailsImpl));
    }


    @GetMapping("/{studyId}")
    public ApiResponse<StudyWholeResponseDto> getStudy(@PathVariable Long studyId) {
        return ApiResponse.successOf(HttpStatus.OK, studyService.getStudy(studyId));
    }

    @GetMapping
    public ApiResponse<List<StudyResponseDto>> getStudies() {
        return ApiResponse.successOf(HttpStatus.OK, studyService.getStudies());
    }

    @GetMapping("/hottest")
    public ApiResponse<List<StudyResponseDto>> getStudiesHottest() {
        return ApiResponse.successOf(HttpStatus.OK, studyService.getStudiesByQueryCondition());
    }

    @PutMapping("/{studyId}")
    public ApiResponse<StudyResponseDto> updateStudy(
            @PathVariable Long studyId,
            @RequestBody @Valid StudyRequestDto studyRequestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.OK, studyService.updateStudy(studyId, studyRequestDto, userDetailsImpl));
    }

    @DeleteMapping("/{studyId}")
    public ApiResponse<StudyResponseDto> deleteStudy(
            @PathVariable Long studyId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        studyService.deleteStudy(studyId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }

    @DeleteMapping("/file")
    public ApiResponse<String> deleteFile(
            @RequestParam String fileName,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        studyService.deleteFile(fileName,userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK,null);
    }
}
