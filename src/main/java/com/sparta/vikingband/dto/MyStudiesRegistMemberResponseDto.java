package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyRegist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MyStudiesRegistMemberResponseDto {
    private Long studyId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<MemberOuterResponseDto> appliedMembers = new ArrayList<>();

    public MyStudiesRegistMemberResponseDto(Study study) {
        this.studyId = study.getId();
        this.title = study.getTitle();
        this.content = study.getContent();
        this.createdAt = study.getCreatedAt();
        this.modifiedAt = study.getModifiedAt();
        if (study.getStudyRegistSet().size() != 0) {
            for (StudyRegist studyRegist : study.getStudyRegistSet()) {
                if (!studyRegist.isAccepted()) {
                    this.appliedMembers.add(MemberOuterResponseDto.of(studyRegist.getMember()));
                }
            }
        }
    }

    public static MyStudiesRegistMemberResponseDto of(Study study) {
        return new MyStudiesRegistMemberResponseDto(study);
    }

}
