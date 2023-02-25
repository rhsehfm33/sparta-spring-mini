package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
    //생성 시 사용, 그 전에 Userdetail.getmembername을 받아서 repository
    Optional<StudyBoard> findByIdAndStudyId(Long id, Long studyId);
    //Optional<StudyBoard> findByIdAndMembername(Long id, String membername);
    //수정 시 사용
    Optional<StudyBoard> findByIdAndMemberId(Long id, Long memberId);
    //해당Member의 스터디 보드 모두 조회
    List<StudyBoard> findAllByMember(Member member);

    //해당Study 모임의 스터디 보드 모두 조회
    List<StudyBoard> findAllByStudy(Study study);

    //해당멤버의 페이지 모두 조회
    Page<StudyBoard> findAllByMemberId(Long memberId, Pageable pageable);
    Page<StudyBoard> findAll(Pageable pageable);
}
