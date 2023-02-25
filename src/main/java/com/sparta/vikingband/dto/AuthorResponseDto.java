package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import lombok.Getter;

@Getter
public class AuthorResponseDto {
    private long memberId;
    private String memberName;
    private String email;

    AuthorResponseDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
    }

    public static AuthorResponseDto of(Member member) {
        return new AuthorResponseDto(member);
    }
}
