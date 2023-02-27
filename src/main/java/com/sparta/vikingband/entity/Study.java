package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.StudyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity(name = "Study")
@NoArgsConstructor
public class Study extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false, length = 30)
    private String subject;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private int maxMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    Set<StudyBoardComment> studyBoardCommentSet = new HashSet<>();

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    Set<StudyBoard> studyBoardSet = new HashSet<>();

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    Set<StudyRegist> studyRegistSet = new HashSet<>();

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    Set<StudyWish> studyWishSet = new HashSet<>();

    public Study(StudyRequestDto studyRequestDto, Member member) {
        this.title = studyRequestDto.getTitle();
        this.content = studyRequestDto.getContent();
        this.subject = studyRequestDto.getSubject();
        this.imageUrl = imageUrl;
        this.maxMember = studyRequestDto.getMaxMember();
        this.member = member;
    }

    public void update(StudyRequestDto studyRequestDto) {
        this.title = studyRequestDto.getTitle();
        this.subject = studyRequestDto.getSubject();
        this.content = studyRequestDto.getContent();
        this.maxMember = studyRequestDto.getMaxMember();
    }
}
