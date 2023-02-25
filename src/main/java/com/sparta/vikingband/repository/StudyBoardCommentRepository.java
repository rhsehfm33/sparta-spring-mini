package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyBoard;
import com.sparta.vikingband.entity.StudyBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyBoardCommentRepository extends JpaRepository<StudyBoardComment, Long> {


    Optional<StudyBoardComment> findByIdAndStudyBoard_Id(Long id, Long studyBoard_Id);

    Optional<StudyBoardComment> findByIdAndStudyId(Long id, Long studyId);

    Optional<StudyBoardComment> findByIdAndMemberId(Long id, Long memberId);

    List<StudyBoardComment> findAllByMember(Member member);

    List<StudyBoardComment> findAllByStudy(Study study);

    List<StudyBoardComment> findAllByStudyBoard(StudyBoard studyBoard);
}