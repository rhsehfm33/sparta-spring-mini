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
    private Long memberId;
    private String memberName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<MyStudiesRegistMemberResponseDto> myStudiesWithRegistMembers;
    private List<StudyResponseDto> myStudyRegists;
    private List<StudyResponseDto> myStudyWishes;
    private List<StudyResponseDto> myCreatedStudies;
    private List<StudyBoardResponseDto> myCreatedStudyBoards;
    private List<StudyBoardCommentResponseDto> myCreatedStudyBoardComments;


    public MemberWholeResponseDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
        this.myStudiesWithRegistMembers = member.getStudySet().stream()
                .map(study -> MyStudiesRegistMemberResponseDto.of(study))
                .filter(myStudiesRegistMemberResponseDto -> myStudiesRegistMemberResponseDto.getAppliedMembers().size()> 0)
                .collect(Collectors.toList());
        this.myStudyRegists = member.getStudyRegistSet().stream()
                .map(studyRegist -> StudyResponseDto.of(studyRegist.getStudy()))
                .collect(Collectors.toList());
        this.myStudyWishes = member.getStudyWishSet().stream()
                .map(studyWish -> StudyResponseDto.of(studyWish.getStudy()))
                .collect(Collectors.toList());
        this.myCreatedStudies = member.getStudySet().stream()
                .map(study -> StudyResponseDto.of(study))
                .collect(Collectors.toList());
        this.myCreatedStudyBoards = member.getStudyBoardSet().stream()
                .map(studyBoard -> StudyBoardResponseDto.of(studyBoard))
                .collect(Collectors.toList());
        this.myCreatedStudyBoardComments = member.getStudyBoardCommentSet().stream()
                .map(studyBoardComment -> StudyBoardCommentResponseDto.of(studyBoardComment))
                .collect(Collectors.toList());
    }

    public static MemberWholeResponseDto of(Member user) {
        return new MemberWholeResponseDto(user);
    }
}
