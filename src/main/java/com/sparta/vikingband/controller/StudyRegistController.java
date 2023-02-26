package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.StudyRegistRequestDto;
import com.sparta.vikingband.dto.StudyRegistResponseDto;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyRegistService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study_register")
public class StudyRegistController {

    private final StudyRegistService studyRegistService;

//    @GetMapping("/apply/{memberId}")
//    public ApiResponse<List<StudyRegistResponseDto>> getRegists(@PathVariable Long memberId) {
//        return ApiResponse.successOf(HttpStatus.OK, studyRegistService.getRegists(memberId));
//    }


    @PostMapping("/apply/{studyId}")
    public ApiResponse<StudyRegistResponseDto> makeRegist(
            @PathVariable Long studyId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ApiResponse.successOf(HttpStatus.ACCEPTED, studyRegistService.makeRegist(studyId, userDetails));
    }

    @DeleteMapping("/cancel/{studyId}")
    public ApiResponse<StudyRegistResponseDto> deleteRegist(
        @PathVariable Long studyRegistId,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        studyRegistService.deleteRegist(studyRegistId, userDetails);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }

    @PutMapping("/approve/{studyRegistId}")
    public ApiResponse<StudyRegistResponseDto> approveRegist(
        @PathVariable Long studyRegistId,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        studyRegistService.approveRegist(studyRegistId, userDetails);
        return ApiResponse.successOf(HttpStatus.ACCEPTED, null);
    }

    @DeleteMapping("/deny/{studyRegistId}")
    public ApiResponse<StudyRegistResponseDto> denyRegist(
        @PathVariable Long studyRegistId,
        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws AccessDeniedException {
        studyRegistService.deleteRegist(studyRegistId, userDetails);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }

}
