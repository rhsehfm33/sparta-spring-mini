package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyBoardCommentRepository extends JpaRepository<StudyBoard, Long> {


    Optional<StudyBoardCommentRepository> findByIdAndStudyBoard_Id(Long id, Long studyBoard_Id);

    Optional<StudyBoardCommentRepository> findByIdAndStudyId(Long id, Long studyId);

    Optional<StudyBoardCommentRepository> findByIdAndMemberId(Long id, Long memberId);

    List<StudyBoardCommentRepository> findAllByMember(Member member);

    List<StudyBoardCommentRepository> findAllByStudy(Study study);

    List<StudyBoardCommentRepository> findAllByStudyBoard(StudyBoard studyBoard);
}