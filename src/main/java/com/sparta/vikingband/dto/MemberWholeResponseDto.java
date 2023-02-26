package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberWholeResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<StudyResponseDto> myCreatedStudies;
    private List<StudyBoardResponseDto> myCreatedStudyBoards;
    private List<StudyBoardCommentResponseDto> myStudyBoardComments;
    private List<StudyRegistResponseDto> myStudyRegists;
    private List<StudyWishResponseDto> myStudyWishes;


    // TODO: N + 1 problem occurs. Need refactoring
    public MemberWholeResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getMemberName();
        this.email = member.getEmail();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
        this.myCreatedStudies = member.getStudyList().stream()
                .map(study -> StudyResponseDto.of(study))
                .collect(Collectors.toList());
        this.myStudyBoardComments = member.getStudyBoardCommentList().stream()
                .map(studyBoardComment -> StudyBoardCommentResponseDto.of(studyBoardComment))
                .collect(Collectors.toList());
        this.myStudyRegists = member.getStudyRegistList().stream()
                .map(studyRegist -> StudyRegistResponseDto.of(studyRegist))
                .collect(Collectors.toList());;
        this.myStudyWishes = member.getStudyWishList().stream()
                .map(studyWish -> StudyWishResponseDto.of(studyWish))
                .collect(Collectors.toList());
    }

    public static MemberWholeResponseDto of(Member user) {
        return new MemberWholeResponseDto(user);
    }
}
