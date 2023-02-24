package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.StudyWish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyWishRepository extends JpaRepository<StudyWish, Long> {

    /**
     * 찜하기 테이블의 아이디로 찜한 내용 찾기
     * @param id must not be {@literal null}.
     * @return {Optional};
     */
    Optional<StudyWish> findById(Long id);

    /**
     * 회원이 찜한 스터디 목록 출력
     * @param memberId
     * @return
     */
    List<StudyWish> findAllById(Long memberId);

    Page<StudyWish> findAllById(Pageable pageable);


}
