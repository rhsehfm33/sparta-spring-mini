package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.SignupRequestDto;
import com.sparta.vikingband.enums.MemberRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity(name = "Member")
@NoArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberName;

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role = MemberRoleEnum.USER;

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    Set<Study> studySet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    // TODO: @OrderBy("createdAt DESC") 넣기
    Set<StudyWish> studyWishSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    // TODO: @OrderBy("createdAt DESC") 넣기
    Set<StudyRegist> studyRegistSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    Set<StudyBoard> studyBoardSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", cascade=CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    Set<StudyBoardComment> studyBoardCommentSet = new LinkedHashSet<>();

    public Member(SignupRequestDto signupRequestDto) {
        this.memberName = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.email = signupRequestDto.getEmail();
    }
}
