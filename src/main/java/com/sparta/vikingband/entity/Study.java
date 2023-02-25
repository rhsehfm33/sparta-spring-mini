package com.sparta.vikingband.entity;

import com.sparta.vikingband.dto.StudyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int minMember;

    @Column(nullable = false)
    private int maxMember;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "study", cascade=CascadeType.REMOVE)
    List<StudyBoardComment> studyBoardCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade=CascadeType.REMOVE)
    List<StudyBoard> studyBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade=CascadeType.REMOVE)
    List<StudyRegist> studyRegistList = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade=CascadeType.REMOVE)
    List<StudyWish> studyWishList = new ArrayList<>();

    public Study(StudyRequestDto studyRequestDto, Member member, String imageUrl) {
        this.title = studyRequestDto.getTitle();
        this.content = studyRequestDto.getContent();
        this.subject = studyRequestDto.getSubject();
        this.imageUrl = imageUrl;
        this.minMember = studyRequestDto.getMinMember();
        this.maxMember = studyRequestDto.getMaxMember();
        this.member = member;
    }

    public void update(StudyRequestDto studyRequestDto) {
        this.title = studyRequestDto.getTitle();
        this.subject = studyRequestDto.getSubject();
        this.content = studyRequestDto.getContent();
        this.minMember = studyRequestDto.getMinMember();
        this.maxMember = studyRequestDto.getMaxMember();
    }
}
