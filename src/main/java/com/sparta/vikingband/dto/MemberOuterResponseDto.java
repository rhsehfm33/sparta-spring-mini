package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberOuterResponseDto {
    private Long memberId;
    private String memberName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemberOuterResponseDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }

    public static MemberOuterResponseDto of(Member user) {
        return new MemberOuterResponseDto(user);
    }
}