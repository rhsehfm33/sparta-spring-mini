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
     * 모든 찜하기 내용 불러오기
     * @return List{studyWish}
     */
    List<StudyWish> findAll();

    /**
     * 모든 찜하기 내용 불러오기
     * @oaram pageable
     * @return Page{studyWish}
     */
    Page<StudyWish> findAll(Pageable pageable);

    /**
     * 회원이 찜한 스터디 목록 출력
     * @param memberId
     * @return
     */
    List<StudyWish> findAllByMemberId(Long memberId);

//    Page<StudyWish> findAllByMemberId(Pageable pageable); // TODO: complie에러 잡기


}
