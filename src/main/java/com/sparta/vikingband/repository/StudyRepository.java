package com.sparta.vikingband.repository;

import com.sparta.vikingband.dto.StudyResponseDto;
import com.sparta.vikingband.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    // TODO: best practice 더 찾아보기. 현재는 처리가 분할되어 있음
//    @Query("select s from Study s" +
//            " left join s.studyWishSet w" +
//            " where lower(s.title) like lower(concat('%', :keyword, '%'))" +
//            " group by s.id" +
//            " order by count(w) desc")
//    List<StudyResponseDto> findAllByKeywordOrderByStudyWishSetCountDesc(@Param("keyword") String keyword);

//    @Query("SELECT s FROM Study s" +
//            " WHERE LOWER(s.title)" +
//            " LIKE LOWER(CONCAT('%', :keyword, '%'))" +
//            " ORDER BY s.createdAt DESC")
//    List<StudyResponseDto> findByTitleContainingOrderByCreatedAtDesc(String keyword);

    @Query("select s from Study s" +
            " left join s.studyWishSet w" +
            " group by s.id" +
            " order by count(w) desc")
    List<StudyResponseDto> findAllByHottest();

    List<Study> findAllByOrderByCreatedAtDesc();

    Optional<Study> findById(Long studyId);

    Optional<Study> findByMemberId(Long memberId);

    Optional<Study> findByIdAndMemberId(Long studyId, Long memberId);

    Page<Study> findByOrderByCreatedAtDesc(Pageable pageable);

    List<Study> findByOrderByCreatedAtDesc();
}
