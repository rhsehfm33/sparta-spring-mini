package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {
            "studyBoardCommentList",
            "studyBoardList",
            "studyRegistList",
            "studyWishList",
            "studyList"
    }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByMemberName(String memberName);

    List<Member> findAll();

    Page<Member> findAll(Pageable pageable);
}
