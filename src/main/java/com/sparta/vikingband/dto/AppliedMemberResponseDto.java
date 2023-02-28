package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppliedMemberResponseDto {
    private boolean isApproved;
    private Long memberId;
    private String memberName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AppliedMemberResponseDto(Member member, boolean isApproved) {
        this.isApproved = isApproved;
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }

    public static AppliedMemberResponseDto of(Member user, boolean isApproved) {
        return new AppliedMemberResponseDto(user, isApproved);
    }
}