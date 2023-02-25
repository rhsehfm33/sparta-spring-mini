package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findById(Long studyId);

    //진규 추가
    Optional<Study> findByIdAndMemberId(Long studyId, Long memberId);


    Page<Study> findByOrderByCreatedAtDesc(Pageable pageable);

    List<Study> findByOrderByCreatedAtDesc();
}
