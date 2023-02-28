package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

    // TODO: 베스트 프랙티스가 뭐였을까.. API 쪼개기 같다...
    @EntityGraph(attributePaths = {
            "studySet.member",
            "studySet.studyWishSet",
            "studySet.studyRegistSet.member",
            "studySet.studyWishSet.member",
            "studyWishSet.study.studyWishSet",
            "studyWishSet.study.member",
            "studyRegistSet.study.member",
            "studyRegistSet.study.studyWishSet",
            "studyBoardSet.member",
            "studyBoardCommentSet.member"
    }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findMemberDetailByMemberName(String memberName);

    List<Member> findAll();

    Page<Member> findAll(Pageable pageable);
}
