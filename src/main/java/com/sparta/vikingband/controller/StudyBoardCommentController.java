package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.*;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyBoardCommentService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class StudyBoardCommentController {

    private final StudyBoardCommentService studyBoardCommentService;

    @PostMapping("/{studyBoardId}")
    public ApiResponse<StudyBoardCommentResponseDto> createComment(
            @PathVariable Long studyBoardId,
            @RequestBody StudyBoardCommentRequestDto requestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.CREATED, studyBoardCommentService.createStudyBoardComment(studyBoardId, requestDto, userDetailsImpl));
    }

    @GetMapping("/{studyBoardId}")
    public ApiResponse<List<StudyBoardCommentResponseDto>> getIdStudyBoardComment(@PathVariable Long studyBoardId) {
        return ApiResponse.successOf(HttpStatus.OK, studyBoardCommentService.getStudyBoardComments(studyBoardId));
    }

    @PutMapping("/{studyBoardCommentId}")
    public ApiResponse<StudyBoardCommentResponseDto> updateComment(
            @PathVariable Long studyBoardCommentId,
            @RequestBody StudyBoardCommentRequestDto requestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return ApiResponse.successOf(HttpStatus.OK, studyBoardCommentService.updateStudyBoardComment(studyBoardCommentId, requestDto, userDetailsImpl));
    }

    @DeleteMapping("/{studyBoardCommentId}")
    public ApiResponse<StudyResponseDto> deleteComment(
            @PathVariable Long studyBoardCommentId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        studyBoardCommentService.deleteStudyBoardComment(studyBoardCommentId, userDetailsImpl);
        return ApiResponse.successOf(HttpStatus.OK, null);
    }

}

