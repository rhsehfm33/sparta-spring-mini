package com.sparta.vikingband.controller;

import com.sparta.vikingband.dto.ApiResponse;
import com.sparta.vikingband.dto.StudyBoardRequestDto;
import com.sparta.vikingband.dto.StudyBoardResponseDto;
import com.sparta.vikingband.dto.StudyBoardWholeResponseDto;
import com.sparta.vikingband.security.UserDetailsImpl;
import com.sparta.vikingband.service.StudyBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyBoardController {
    private final StudyBoardService studyBoardService;


    @PostMapping("/study_boards/{studyid}")
    public ApiResponse<StudyBoardResponseDto> createStudyBoard
            (@PathVariable Long studyid,
             @RequestBody StudyBoardRequestDto requestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return ApiResponse.successOf(HttpStatus.OK, studyBoardService.createStudyBoard(studyid,requestDto,userDetails));
    }
    @GetMapping("/study_boards")
    public ApiResponse<StudyBoardWholeResponseDto> getStudyBoard() {
        return ApiResponse.successOf(HttpStatus.OK,studyBoardService.getStudyBoards());
    }

    @GetMapping("/study_boards/{boardid}")
    public ApiResponse<StudyBoardResponseDto> getIdStudyBoard
            (@PathVariable Long boardid)
    {
        return ApiResponse.successOf(HttpStatus.OK,studyBoardService.getIdStudyBoard(boardid));
    }

    @PutMapping("/study_boards/{boardid}")
    public ApiResponse<StudyBoardResponseDto> updateStudyBoard
            (@PathVariable Long boardid,
             @RequestBody StudyBoardRequestDto requestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return ApiResponse.successOf(HttpStatus.OK,studyBoardService.updateStudyBoard(boardid,requestDto,userDetails));
    }

    @DeleteMapping("/study_boards/{boardid}")
    public ApiResponse<StudyBoardResponseDto> deleteStudyBoard
            (@PathVariable Long boardid,
             @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        studyBoardService.deleteStudyBoard(boardid,userDetails);
        return ApiResponse.successOf(HttpStatus.OK,null);
    }

}

