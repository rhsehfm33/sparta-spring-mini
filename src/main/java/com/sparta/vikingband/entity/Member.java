package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.SignupRequestDto;
import com.sparta.vikingband.enums.MemberRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "Member")
@NoArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberName;

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role = MemberRoleEnum.USER;

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    List<StudyBoardComment> studyBoardCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    List<StudyBoard> studyBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    List<StudyRegister> studyRegisterList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    List<StudyWish> studyWishList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    List<Study> studyList = new ArrayList<>();

    public Member(SignupRequestDto signupRequestDto) {
        this.memberName = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.email = signupRequestDto.getEmail();
    }
}
