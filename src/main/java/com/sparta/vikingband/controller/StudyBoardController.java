package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.StudyBoardRequestDto;
import com.sparta.vikingband.dto.StudyBoardResponseDto;
import com.sparta.vikingband.dto.StudyBoardWholeResponseDto;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyBoardService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// 현재 사용하게 됐음
@RestController
@RequestMapping("/api/study_boards")
@RequiredArgsConstructor
public class StudyBoardController {
    private final StudyBoardService studyBoardService;

    @PostMapping("/{studyid}")
    public ApiResponse<StudyBoardResponseDto> createStudyBoard
            (@PathVariable Long studyid,
             @RequestBody @Valid StudyBoardRequestDto requestDto,
             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return ApiResponse.successOf(HttpStatus.OK, studyBoardService.createStudyBoard(studyid,requestDto,userDetails));
    }
    @GetMapping
    public ApiResponse<StudyBoardWholeResponseDto> getStudyBoard()
    {
        return ApiResponse.successOf(HttpStatus.OK,studyBoardService.getStudyBoards());
    }

    @GetMapping("/{boardid}")
    public ApiResponse<StudyBoardResponseDto> getIdStudyBoard(@PathVariable Long boardid)
    {
        return ApiResponse.successOf(HttpStatus.OK,studyBoardService.getIdStudyBoard(boardid));
    }

    @PutMapping("/{boardid}")
    public ApiResponse<StudyBoardResponseDto> updateStudyBoard
            (@PathVariable Long boardid,
             @RequestBody @Valid StudyBoardRequestDto requestDto,
             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return ApiResponse.successOf(HttpStatus.OK,studyBoardService.updateStudyBoard(boardid,requestDto,userDetails));
    }

    @DeleteMapping("/{boardid}")
    public ApiResponse<StudyBoardResponseDto> deleteStudyBoard(
            @PathVariable Long boardid,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails
    )
    {
        studyBoardService.deleteStudyBoard(boardid,userDetails);
        return ApiResponse.successOf(HttpStatus.OK,null);
    }

}

