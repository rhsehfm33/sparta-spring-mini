package com.sparta.vikingband.dto;

import com.sparta.vikingband.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MemberOuterResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemberOuterResponseDto(Member user) {
        this.id = user.getId();
        this.username = user.getMemberName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

    public static MemberOuterResponseDto of(Member user) {
        return new MemberOuterResponseDto(user);
    }
}